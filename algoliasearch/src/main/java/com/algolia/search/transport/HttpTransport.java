package com.algolia.search.transport;

import com.algolia.search.Defaults;
import com.algolia.search.clients.AlgoliaConfig;
import com.algolia.search.http.IHttpRequester;
import com.algolia.search.models.AlgoliaHttpRequest;
import com.algolia.search.models.AlgoliaHttpResponse;
import com.algolia.search.models.CallType;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.objects.RequestOptions;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public class HttpTransport implements IHttpTransport {

  private final AlgoliaConfig config;
  private final IHttpRequester httpRequester;

  public HttpTransport(AlgoliaConfig config, IHttpRequester httpRequester) {
    this.config = config;
    this.httpRequester = httpRequester;
  }

  public <TResult, TData> CompletableFuture<TResult> executeRequestAsync(
      @Nonnull HttpMethod method,
      @Nonnull String uri,
      @Nonnull CallType callType,
      TData data,
      Class<TResult> returnClass,
      RequestOptions requestOptions) {

    AlgoliaHttpRequest request = buildRequest(method, uri, callType, data);

    CompletableFuture<AlgoliaHttpResponse> response = httpRequester.performRequestAsync(request);

    return null;
  }

  public <TResult, TData> TResult executeRequest(
      @Nonnull HttpMethod method,
      @Nonnull String uri,
      @Nonnull CallType callType,
      TData data,
      Class<TResult> returnClass,
      RequestOptions requestOptions)
      throws IOException {

    AlgoliaHttpRequest request = buildRequest(method, uri, callType, data);

    AlgoliaHttpResponse response = httpRequester.performRequest(request);

    return Defaults.DEFAULT_OBJECT_MAPPER.readValue(response.getBody(), returnClass);
  }

  private <TData> AlgoliaHttpRequest buildRequest(
      @Nonnull HttpMethod method, @Nonnull String uri, @Nonnull CallType callType, TData data) {
    URL builtUri = buildURI(uri);
    return new AlgoliaHttpRequest(method, builtUri, config.getDefaultHeaders(), null);
  }

  private URL buildURI(String uri) {
    URL ret = null;

    try {
      String url = "https://" + config.getDefaultHost().get(0).getUrl() + uri;
      ret = new URL(url);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return ret;
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
