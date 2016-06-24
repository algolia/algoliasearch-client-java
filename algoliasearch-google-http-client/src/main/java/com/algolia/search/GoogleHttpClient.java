package com.algolia.search;

import com.algolia.search.http.AlgoliaHttpClient;
import com.algolia.search.http.AlgoliaHttpRequest;
import com.algolia.search.http.AlgoliaHttpResponse;
import com.algolia.search.http.HttpMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.*;
import com.google.common.net.MediaType;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Internal HttpClient
 */
class GoogleHttpClient extends AlgoliaHttpClient {

  private final HttpRequestFactory requestFactory;
  private final ObjectMapper objectMapper;
  private final List<String> queryHosts;
  private final List<String> buildHosts;
  private final JacksonParser jacksonParser;

  GoogleHttpClient(HttpRequestFactory requestFactory, ObjectMapper objectMapper, List<String> queryHosts, List<String> buildHosts) {
    this.requestFactory = requestFactory;
    this.objectMapper = objectMapper;
    this.queryHosts = queryHosts;
    this.buildHosts = buildHosts;
    this.jacksonParser = new JacksonParser(objectMapper);
  }

  private HttpRequest buildRequest(HttpMethod method, String host, List<String> path, Map<String, String> parameters, HttpContent content) throws IOException {
    GenericUrl url = new GenericUrl();
    url.setScheme("https");
    url.setHost(host);

    List<String> computedPath = new ArrayList<>();
    computedPath.add(""); //Add the first `/` see java doc of setPathParts
    computedPath.addAll(path);
    url.setPathParts(computedPath);

    if (parameters != null && !parameters.isEmpty()) {
      for (Map.Entry<String, String> entry : parameters.entrySet()) {
        url.set(entry.getKey(), entry.getValue());
      }
    }

    return requestFactory
      .buildRequest(method.name, url, content)
      .setParser(jacksonParser)
      .setLoggingEnabled(true)
      .setCurlLoggingEnabled(true) //nice logging it necessary
      .setThrowExceptionOnExecuteError(false) //4XX won't throw exceptions
      .setNumberOfRetries(0); //let us handle retries
  }

  @Override
  protected AlgoliaHttpResponse request(@Nonnull AlgoliaHttpRequest request) throws IOException {
    final HttpResponse httpResponse = buildRequest(
      request.getMethod(),
      request.getHost(),
      request.getPath(),
      request.getParameters(),
      request.getContent() == null ? null : new StringHttpContent(request.getContent())
    ).execute();

    return new AlgoliaHttpResponse() {

      @Override
      public int getStatusCode() {
        return httpResponse.getStatusCode();
      }

      @Override
      public <T> T parseAs(Class<T> klass) throws IOException {
        return httpResponse.parseAs(klass);
      }

      @Override
      public Object parseAs(Type type) throws IOException {
        return httpResponse.parseAs(type);
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

  private final class StringHttpContent extends AbstractHttpContent {

    private final String content;

    StringHttpContent(String content) {
      super(MediaType.JSON_UTF_8.type());
      this.content = content;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
      out.write(content.getBytes());
      out.flush();
    }
  }
}
