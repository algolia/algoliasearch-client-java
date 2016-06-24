package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.apache.ApacheHttpTransport;
import org.apache.http.conn.ssl.SSLSocketFactory;

import javax.annotation.Nonnull;
import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public final class ApacheAPIClientBuilder extends APIClientBuilder {

  public ApacheAPIClientBuilder(@Nonnull String applicationId, @Nonnull String apiKey) {
    super(applicationId, apiKey);
  }

  @Override
  protected APIClient build(String applicationId, String apiKey, ObjectMapper objectMapper, List<String> buildHosts, List<String> queryHosts, Map<String, String> headers, int connectTimeout, int readTimeout) {
    HttpHeaders httpHeaders = new HttpHeaders();
    for (Map.Entry<String, String> e : headers.entrySet()) {
      httpHeaders = httpHeaders.set(e.getKey(), e.getValue());
    }

    SSLSocketFactory sslSocketFactory;
    try {
      sslSocketFactory = new SSLSocketFactory(SSLContext.getDefault(), new TimeoutableHostNameResolver(connectTimeout));
    } catch (NoSuchAlgorithmException e) {
      sslSocketFactory = SSLSocketFactory.getSocketFactory();
    }

    ApacheHttpTransport httpTransport = new ApacheHttpTransport.Builder().setSocketFactory(sslSocketFactory).build();
    HttpRequestFactory requestFactory = httpTransport.createRequestFactory(buildHttpRequestInitializer(connectTimeout, readTimeout, httpHeaders));

    return new APIClient(new GoogleHttpClient(requestFactory, objectMapper, queryHosts, buildHosts));
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
