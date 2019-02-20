package com.algolia.search.transport;

import com.algolia.search.Defaults;
import com.algolia.search.clients.AlgoliaConfig;
import com.algolia.search.exceptions.*;
import com.algolia.search.http.IHttpRequester;
import com.algolia.search.models.*;
import com.algolia.search.objects.RequestOptions;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

  public <TResult, TData> TResult executeRequest(
      @Nonnull HttpMethod method,
      @Nonnull String path,
      @Nonnull CallType callType,
      TData data,
      Class<TResult> returnClass,
      RequestOptions requestOptions)
      throws AlgoliaException {
    try {
      return executeRequestAsync(method, path, callType, data, returnClass, requestOptions).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new AlgoliaException(e.getMessage());
    }
  }

  public <TResult, TData> CompletableFuture<TResult> executeRequestAsync(
      @Nonnull HttpMethod method,
      @Nonnull String path,
      @Nonnull CallType callType,
      TData data,
      Class<TResult> returnClass,
      RequestOptions requestOptions) {

    Iterator<StatefulHost> hosts = retryStrategy.getTryableHosts(callType).iterator();
    AlgoliaHttpRequest request = buildRequest(method, path, callType, data);

    return executeWithRetry(hosts, request, returnClass);
  }

  private <TResult> CompletableFuture<TResult> executeWithRetry(
      Iterator<StatefulHost> hosts, AlgoliaHttpRequest request, Class<TResult> returnClass) {

    // If no more hosts to request the retry has failed
    if (!hosts.hasNext()) {
      throw new AlgoliaRetryException("Unreachable hosts");
    }

    // Building the request URL
    StatefulHost currentHost = hosts.next();
    request.setUri(buildURI(currentHost.getUrl(), request.getMethodPath()));

    // Performing the recursive http request in case of failure
    return httpRequester
        .performRequestAsync(request)
        .thenApply(
            resp -> {
              switch (retryStrategy.decide(
                  currentHost, resp.getHttpStatusCode(), resp.isTimedOut())) {
                case SUCCESS:
                  try (InputStream dataStream = resp.getBody()) {
                    return Defaults.DEFAULT_OBJECT_MAPPER.readValue(dataStream, returnClass);
                  } catch (IOException e) {
                    throw new AlgoliaRuntimeException(e);
                  }
                case RETRY:
                  executeWithRetry(hosts, request, returnClass);
                case FAILURE:
                  throw new AlgoliaApiException(resp.getError(), resp.getHttpStatusCode());
                default:
                  throw new AlgoliaRetryException("Couldn't process the retry strategy decision");
              }
            });
  }

  private <TData> AlgoliaHttpRequest buildRequest(
      @Nonnull HttpMethod method,
      @Nonnull String methodPath,
      @Nonnull CallType callType,
      TData data) {

    AlgoliaHttpRequest request =
        new AlgoliaHttpRequest(
            method, methodPath, config.getDefaultHeaders(), getTimeOut(callType));

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

  private URL buildURI(String host, String uri) {
    try {
      return new URL("https://" + host + uri);
    } catch (MalformedURLException e) {
      throw new AlgoliaRuntimeException("Error while building the URL", e);
    }
  }

  private int getTimeOut(CallType callType) {
    switch (callType) {
      case READ:
        return config.getReadTimeOut() == null ? 5 : config.getReadTimeOut();
      case WRITE:
        return config.getWriteTimeOut() == null ? 30 : config.getWriteTimeOut();
      default:
        return 30;
    }
  }
}
