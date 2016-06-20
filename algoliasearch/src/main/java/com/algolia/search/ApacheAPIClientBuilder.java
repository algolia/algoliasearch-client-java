package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.apache.ApacheHttpTransport;
import org.apache.http.conn.ssl.SSLSocketFactory;

import javax.annotation.Nonnull;
import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public final class ApacheAPIClientBuilder extends APIClientBuilder {

  public ApacheAPIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    super(applicationId, apiKey);
  }

  @Override
  protected APIClient build(String applicationId, String apiKey, ObjectMapper objectMapper, List<String> buildHosts, List<String> queryHosts, int connectTimeout, HttpRequestInitializer httpRequestInitializer) {
    SSLSocketFactory sslSocketFactory;
    try {
      sslSocketFactory = new SSLSocketFactory(SSLContext.getDefault(), new TimeoutableHostNameResolver(connectTimeout));
    } catch (NoSuchAlgorithmException e) {
      sslSocketFactory = SSLSocketFactory.getSocketFactory();
    }

    ApacheHttpTransport httpTransport = new ApacheHttpTransport.Builder().setSocketFactory(sslSocketFactory).build();
    HttpRequestFactory requestFactory = httpTransport.createRequestFactory(httpRequestInitializer);

    return new APIClient(new AlgoliaHttpClient(requestFactory, objectMapper, queryHosts, buildHosts));
  }

//  @Override
//  protected AsyncAPIClient buildAsync(Executor executor, String applicationId, String apiKey, List<String> buildHosts, List<String> queryHosts, HttpRequestInitializer httpRequestInitializer) {
//    return null;
//  }

}
