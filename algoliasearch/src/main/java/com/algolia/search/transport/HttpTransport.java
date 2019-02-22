package com.algolia.search.transport;

import com.algolia.search.Defaults;
import com.algolia.search.clients.AlgoliaConfig;
import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.helpers.CompletableFutureHelper;
import com.algolia.search.helpers.QueryStringHelper;
import com.algolia.search.http.IHttpRequester;
import com.algolia.search.models.AlgoliaHttpRequest;
import com.algolia.search.models.CallType;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.objects.RequestOptions;
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

public class HttpTransport implements IHttpTransport {

  private final AlgoliaConfig config;
  private final IHttpRequester httpRequester;
  private final IRetryStrategy retryStrategy;

  public HttpTransport(AlgoliaConfig config, IHttpRequester httpRequester) {
    this.config = config;
    this.httpRequester = httpRequester;
    this.retryStrategy = new RetryStrategy(config);
  }

  /**
   * Executes the request to Algolia asynchronously with the retry strategy.
   *
   * @param method The http method used for the request (Get,Post,etc.)
   * @param path The path of the API endpoint
   * @param callType The Algolia call type of the request : read or write
   * @param data The data to send if any
   * @param returnClass The type that will be returned
   * @param requestOptions Requests options to add to the request (if so)
   * @param <TResult> The type of the result
   * @param <TData> The type of the data to send (if so)
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an error
   * @throws AlgoliaRuntimeException When an error occurred during the serialization.
   */
  public <TResult, TData> CompletableFuture<TResult> executeRequestAsync(
      @Nonnull HttpMethod method,
      @Nonnull String path,
      @Nonnull CallType callType,
      TData data,
      Class<TResult> returnClass,
      RequestOptions requestOptions) {

    Iterator<StatefulHost> hosts = retryStrategy.getTryableHosts(callType).iterator();
    AlgoliaHttpRequest request = buildRequest(method, path, callType, requestOptions, data);

    return executeWithRetry(hosts, request, returnClass);
  }

  /**
   * Executes asynchronously and recursively (in case of retry) a request to the Algolia API
   *
   * <p>If success the result is returned to the user If retry it performs another call to the API
   * If failure it throws an exception
   *
   * @param hosts An iterator of the hosts to request
   * @param request The request to send to the API
   * @param returnClass The return class (used for serialization)
   * @param <TResult> The type of the result
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an error
   * @throws AlgoliaRuntimeException When an error occurred during the serialization.
   */
  private <TResult> CompletableFuture<TResult> executeWithRetry(
      @Nonnull Iterator<StatefulHost> hosts,
      @Nonnull AlgoliaHttpRequest request,
      @Nonnull Class<TResult> returnClass) {

    // If no more hosts to request the retry has failed
    if (!hosts.hasNext()) {
      return CompletableFutureHelper.failedFuture(
          new AlgoliaRetryException("All hosts are unreachable"));
    }

    // Building the request URL
    StatefulHost currentHost = hosts.next();
    request.setUri(buildURI(currentHost.getUrl(), request.getMethodPath()));

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
                        Defaults.DEFAULT_OBJECT_MAPPER.readValue(dataStream, returnClass));
                  } catch (IOException e) {
                    throw new AlgoliaRuntimeException(e);
                  }
                case RETRY:
                  return executeWithRetry(hosts, request, returnClass);
                case FAILURE:
                  return CompletableFutureHelper.failedFuture(
                      new AlgoliaApiException(resp.getError(), resp.getHttpStatusCode()));
                default:
                  return CompletableFutureHelper.failedFuture(
                      new AlgoliaRetryException("Couldn't process the retry strategy decision"));
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
      @Nonnull RequestOptions requestOptions,
      TData data) {

    Map<String, String> headersToSend = buildHeaders(requestOptions.generateExtraHeaders());
    String fullPath = buildFullPath(methodPath, requestOptions.generateExtraQueryParams());

    AlgoliaHttpRequest request =
        new AlgoliaHttpRequest(method, fullPath, headersToSend, getTimeOut(callType));

    if (data != null) {
      try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        Defaults.DEFAULT_OBJECT_MAPPER.writeValue(out, data);
        ByteArrayInputStream content = new ByteArrayInputStream(out.toByteArray());
        request.setBody(content);
      } catch (IOException e) {
        throw new AlgoliaRuntimeException("Error while serializing the response", e);
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
   * Builds the method full path, i.e path + query parameters if so
   *
   * @param methodPath The path to the API method
   * @param optionalQueryParameters Query parameters to add to the path (if so)
   */
  private String buildFullPath(String methodPath, Map<String, String> optionalQueryParameters) {

    if (optionalQueryParameters == null) {
      return methodPath;
    }

    String queryParameters = QueryStringHelper.buildQueryString(optionalQueryParameters);
    return methodPath + queryParameters;
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
        return config.getReadTimeOut() == null ? 5 * 1000 : config.getReadTimeOut();
      case WRITE:
        return config.getWriteTimeOut() == null ? 30 * 1000 : config.getWriteTimeOut();
      default:
        return 30 * 1000;
    }
  }
}
