package com.algolia.search;

import com.algolia.search.http.AlgoliaHttpClient;
import com.algolia.search.http.AlgoliaHttpRequest;
import com.algolia.search.http.AlgoliaHttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApacheHttpClient extends AlgoliaHttpClient {

  private static final Charset UTF8 = Charset.forName("UTF-8");
  private final CloseableHttpClient internal;
  private final ObjectMapper objectMapper;
  private final List<String> queryHosts;
  private final List<String> buildHosts;

  public ApacheHttpClient(APIClientConfiguration configuration) {
    List<Header> httpHeaders = configuration.getHeaders().entrySet().stream().map(e -> new BasicHeader(e.getKey(), e.getValue())).collect(Collectors.toList());
    RequestConfig requestConfig = RequestConfig
      .custom()
      .setConnectTimeout(configuration.getConnectTimeout())
      .setSocketTimeout(configuration.getReadTimeout())
      .build();

    this.internal = HttpClients
      .custom()
      .disableAutomaticRetries()
      .setDefaultHeaders(httpHeaders)
      .setDnsResolver(new TimeoutableHostNameResolver(configuration.getConnectTimeout()))
      .setDefaultRequestConfig(requestConfig).build();

    this.objectMapper = configuration.getObjectMapper();
    this.queryHosts = configuration.getQueryHosts();
    this.buildHosts = configuration.getBuildHosts();
  }

  @Override
  protected AlgoliaHttpResponse request(@Nonnull AlgoliaHttpRequest request) throws IOException {
    RequestBuilder builder = RequestBuilder
      .create(request.getMethod().name)
      .setUri("https://" + request.getHost() + "/" + String.join("/", request.getPath()));

    for (Map.Entry<String, String> entry : request.getParameters().entrySet()) {
      builder = builder.addParameter(entry.getKey(), entry.getValue());
    }

    if (request.getContent() != null) {
      builder = builder.setEntity(new StringEntity(request.getContent(), ContentType.APPLICATION_JSON));
    }
    final HttpResponse response = internal.execute(builder.build());

    return new AlgoliaHttpResponse() {
      @Override
      public int getStatusCode() {
        return response.getStatusLine().getStatusCode();
      }

      @Override
      public Reader getBody() throws IOException {
        return new InputStreamReader(response.getEntity().getContent(), UTF8);
      }
    };
  }

  @Override
  protected ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  @Override
  public List<String> getQueryHosts() {
    return queryHosts;
  }

  @Override
  public List<String> getBuildHosts() {
    return buildHosts;
  }
}
