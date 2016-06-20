package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.http.HttpRequestInitializer;

import javax.annotation.Nonnull;
import java.util.List;

public final class AppEngineAPIClientBuilder extends APIClientBuilder {

  public AppEngineAPIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    super(applicationId, apiKey);
  }

  @Override
  protected APIClient build(String applicationId, String apiKey, ObjectMapper objectMapper, List<String> buildHosts, List<String> queryHosts, int connectTimeout, HttpRequestInitializer httpRequestInitializer) {
    return new APIClient(new AlgoliaHttpClient(
      new UrlFetchTransport().createRequestFactory(httpRequestInitializer),
      objectMapper,
      queryHosts,
      buildHosts
    ));
  }

//  @Override
//  protected AsyncAPIClient buildAsync(Executor executor, String applicationId, String apiKey, List<String> buildHosts, List<String> queryHosts, HttpRequestInitializer httpRequestInitializer) {
//    return null;
//  }

}
