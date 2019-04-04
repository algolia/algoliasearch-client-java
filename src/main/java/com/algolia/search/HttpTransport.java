package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.models.*;
import com.algolia.search.models.common.CallType;
import com.algolia.search.util.CompletableFutureUtils;
import com.algolia.search.util.QueryStringUtils;
import com.fasterxml.jackson.databind.JavaType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

/**
 * The transport layer is responsible of the serialization/deserialization and the retry strategy.
 */
class HttpTransport {

  private final IHttpRequester httpRequester;
  private final RetryStrategy retryStrategy;
  private final AlgoliaConfigBase config;

  HttpTransport(@Nonnull AlgoliaConfigBase config, @Nonnull IHttpRequester httpRequester) {
    this.config = config;
    this.httpRequester = httpRequester;
    this.retryStrategy = new RetryStrategy(config);
  }

  void close() throws IOException {
    httpRequester.close();
  }

  /**
   * Executes the request to Algolia asynchronously with the retry strategy.
   *
   * @param method The http method used for the request (Get,Post,etc.)
   * @param path The path of the API endpoint
   * @param callType The Algolia call type of the request : read or write
   * @param returnClass The type that will be returned
   * @param requestOptions Requests options to add to the request (if so)
   * @param <TResult> The type of the result
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an error
   * @throws AlgoliaRuntimeException When an error occurred during the serialization.
   */
  <TResult> CompletableFuture<TResult> executeRequestAsync(
      @Nonnull HttpMethod method,
      @Nonnull String path,
      @Nonnull CallType callType,
      Class<TResult> returnClass,
      RequestOptions requestOptions) {

    return executeRequestAsync(method, path, callType, null, returnClass, null, requestOptions);
  }

  /**
   * Executes the request to Algolia asynchronously with the retry strategy.
   *
   * @param method The http method used for the request (Get,Post,etc.)
   * @param path The path of the API endpoint
   * @param callType The Algolia call type of the request : read or write
   * @param data The data to send if any
   * @param returnClazz The type that will be returned
   * @param requestOptions Requests options to add to the request (if so)
   * @param <TResult> The type of the result
   * @param <TData> The type of the data to send (if so)
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an error
   * @throws AlgoliaRuntimeException When an error occurred during the serialization.
   */
  <TResult, TData> CompletableFuture<TResult> executeRequestAsync(
      @Nonnull HttpMethod method,
      @Nonnull String path,
      @Nonnull CallType callType,
      TData data,
      Class<TResult> returnClazz,
      RequestOptions requestOptions) {

    return executeRequestAsync(method, path, callType, data, returnClazz, null, requestOptions);
  }

  /**
   * Executes the request to Algolia asynchronously with the retry strategy.
   *
   * @param method The http method used for the request (Get,Post,etc.)
   * @param path The path of the API endpoint
   * @param callType The Algolia call type of the request : read or write
   * @param data The data to send if any
   * @param returnClazz The type that will be returned
   * @param requestOptions Requests options to add to the request (if so)
   * @param <TResult> The type of the result
   * @param <TInnerResult> The type of the nested class
   * @param <TData> The type of the data to send (if so)
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an error
   * @throws AlgoliaRuntimeException When an error occurred during the serialization.
   */
  <TResult, TInnerResult, TData> CompletableFuture<TResult> executeRequestAsync(
      @Nonnull HttpMethod method,
      @Nonnull String path,
      @Nonnull CallType callType,
      TData data,
      Class<TResult> returnClazz,
      Class<TInnerResult> innerClazz,
      RequestOptions requestOptions) {

    Iterator<StatefulHost> hosts = retryStrategy.getTryableHosts(callType).iterator();

    AlgoliaHttpRequest request = buildRequest(method, path, callType, requestOptions, data);

    JavaType type =
        innerClazz == null
            ? Defaults.getObjectMapper().getTypeFactory().constructType(returnClazz)
            : Defaults.getObjectMapper()
                .getTypeFactory()
                .constructParametricType(returnClazz, innerClazz);

    return executeWithRetry(hosts, request, type);
  }

  /**
   * Executes asynchronously and recursively (in case of retry) a request to the Algolia API
   *
   * <p>If success the result is returned to the user If retry it performs another call to the API
   * If failure it throws an exception
   *
   * @param hosts An iterator of the hosts to request
   * @param request The request to send to the API
   * @param type The type used for deserialization
   * @param <TResult> The type of the result
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an error
   * @throws AlgoliaRuntimeException When an error occurred during the serialization.
   */
  private <TResult> CompletableFuture<TResult> executeWithRetry(
      @Nonnull Iterator<StatefulHost> hosts,
      @Nonnull AlgoliaHttpRequest request,
      @Nonnull JavaType type) {

    // If no more hosts to request the retry has failed
    if (!hosts.hasNext()) {
      return CompletableFutureUtils.failedFuture(
          new AlgoliaRetryException("All hosts are unreachable"));
    }

    // Building the request URL
    StatefulHost currentHost = hosts.next();
    request.setUri(buildURI(currentHost.getUrl(), request.getMethodPath()));

    // Computing timeout with the retry count
    request.incrementTimeout(currentHost.getRetryCount());

    // Performing the recursive http request in case of failure
    return httpRequester
        .performRequestAsync(request)
        .thenComposeAsync(
            resp -> {
              switch (retryStrategy.decide(
                  currentHost, resp.getHttpStatusCode(), resp.isTimedOut())) {
                case SUCCESS:
                  try (InputStream dataStream = resp.getBody()) {
                    return CompletableFuture.completedFuture(
                        Defaults.getObjectMapper().readValue(dataStream, type));
                  } catch (IOException e) {
                    return CompletableFutureUtils.failedFuture(new AlgoliaRuntimeException(e));
                  }
                case RETRY:
                  return executeWithRetry(hosts, request, type);
                case FAILURE:
                  return CompletableFutureUtils.failedFuture(
                      new AlgoliaApiException(resp.getError(), resp.getHttpStatusCode()));
                default:
                  return CompletableFutureUtils.failedFuture(
                      new AlgoliaRetryException(
                          "Error while processing the retry strategy decision."));
              }
            },
            config.getExecutor());
  }

  /**
   * Builds the AlgoliaHttpRequest object Builds the headers Builds the queryParameters Serialize
   * the data if so
   *
   * @param method The HTTP method (GET,POST,PUT,DELETE)
   * @param methodPath The API method path
   * @param callType The API call type can be READ or WRITE
   * @param requestOptions Requests options to add to the request (if so)
   * @param data Data to send to the API (if so)
   * @param <TData> The type of the data (if so)
   */
  private <TData> AlgoliaHttpRequest buildRequest(
      @Nonnull HttpMethod method,
      @Nonnull String methodPath,
      @Nonnull CallType callType,
      RequestOptions requestOptions,
      TData data) {

    Map<String, String> headersToSend =
        requestOptions != null ? buildHeaders(requestOptions.getExtraHeaders()) : buildHeaders();

    String fullPath =
        requestOptions != null
            ? buildFullPath(methodPath, requestOptions.getExtraQueryParams())
            : buildFullPath(methodPath);

    int timeout =
        requestOptions != null && requestOptions.getTimeout() != null
            ? requestOptions.getTimeout()
            : getTimeOut(callType);

    AlgoliaHttpRequest request = new AlgoliaHttpRequest(method, fullPath, headersToSend, timeout);

    if (data != null) {
      try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

        Defaults.getObjectMapper().writeValue(out, data);

        ByteArrayInputStream content = new ByteArrayInputStream(out.toByteArray());

        request.setBody(content);

      } catch (IOException e) {
        throw new AlgoliaRuntimeException("Error while serializing the request", e);
      }
    }

    return request;
  }

  /**
   * Builds the full URI for the request i.e host + fullPath
   *
   * @param host The host to request can change regarding the retry strategy
   * @param fullPath The method full path (method path + query parameters)
   */
  private URL buildURI(String host, String fullPath) {
    try {
      return new URL("https://" + host + fullPath);
    } catch (MalformedURLException e) {
      throw new AlgoliaRuntimeException("Error while building the URL", e);
    }
  }

  /**
   * Builds the method full path
   *
   * @param methodPath The path to the API method
   */
  private String buildFullPath(String methodPath) {
    return buildFullPath(methodPath, null);
  }

  /**
   * Builds the method full path, i.e path + query parameters if so
   *
   * @param methodPath The path to the API method
   * @param optionalQueryParameters Query parameters to add to the path (if so)
   */
  private String buildFullPath(String methodPath, Map<String, String> optionalQueryParameters) {

    if (optionalQueryParameters == null) {
      return methodPath;
    }

    String queryParameters = QueryStringUtils.buildQueryString(optionalQueryParameters);

    return methodPath + queryParameters;
  }

  /**
   * Builds the headers for the request If no optional headers are passed the method takes the
   * default headers from the configuration Otherwise we merge the optional headers with the default
   * headers. Please note that optionals headers will overwrite default headers
   */
  private Map<String, String> buildHeaders() {
    return buildHeaders(null);
  }

  /**
   * Builds the headers for the request If no optional headers are passed the method takes the
   * default headers from the configuration Otherwise we merge the optional headers with the default
   * headers. Please note that optionals headers will overwrite default headers
   *
   * @param optionalHeaders The optional headers to add the request (if so)
   */
  private Map<String, String> buildHeaders(Map<String, String> optionalHeaders) {

    if (optionalHeaders == null) {
      return config.getDefaultHeaders();
    }

    config.getDefaultHeaders().forEach(optionalHeaders::putIfAbsent);

    return optionalHeaders;
  }

  /**
   * Computes the request timeout with the given calltype This value can be overwritten by the
   * configuration
   *
   * @param callType The Algolia callType could be Read or write
   */
  private int getTimeOut(CallType callType) {
    switch (callType) {
      case READ:
        return config.getReadTimeOut() == null ? Defaults.READ_TIMEOUT_MS : config.getReadTimeOut();
      case WRITE:
        return config.getWriteTimeOut() == null
            ? Defaults.WRITE_TIMEOUT_MS
            : config.getWriteTimeOut();
      default:
        return Defaults.WRITE_TIMEOUT_MS;
    }
  }
}
