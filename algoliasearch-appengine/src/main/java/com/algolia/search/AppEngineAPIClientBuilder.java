package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestInitializer;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public final class AppEngineAPIClientBuilder extends APIClientBuilder {

  public AppEngineAPIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    super(applicationId, apiKey);
  }

  @Override
  protected APIClient build(String applicationId, String apiKey, ObjectMapper objectMapper, List<String> buildHosts, List<String> queryHosts, Map<String, String> headers, int connectTimeout, int readTimeout) {
    HttpHeaders httpHeaders = new HttpHeaders();
    for (Map.Entry<String, String> e : headers.entrySet()) {
      httpHeaders = httpHeaders.set(e.getKey(), e.getValue());
    }

    return new APIClient(new GoogleHttpClient(
      new UrlFetchTransport().createRequestFactory(buildHttpRequestInitializer(connectTimeout, readTimeout, httpHeaders)),
      objectMapper,
      queryHosts,
      buildHosts
    ));
  }

  private HttpRequestInitializer buildHttpRequestInitializer(int connectTimeout, int readTimeout, HttpHeaders headers) {
    return request -> request
      .setConnectTimeout(connectTimeout)
      .setReadTimeout(readTimeout)
      .setHeaders(headers);
  }


//  @Override
//  protected AsyncAPIClient buildAsync(Executor executor, String applicationId, String apiKey, List<String> buildHosts, List<String> queryHosts, HttpRequestInitializer httpRequestInitializer) {
//    return null;
//  }

}
