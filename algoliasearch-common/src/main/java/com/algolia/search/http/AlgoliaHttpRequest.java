package com.algolia.search.http;

import java.util.List;
import java.util.Map;

public class AlgoliaHttpRequest {

  private final String host;
  private final String content;
  private final AlgoliaRequest<?> request;

  public AlgoliaHttpRequest(String host, String content, AlgoliaRequest<?> request) {
    this.host = host;
    this.content = content;
    this.request = request;
  }

  public HttpMethod getMethod() {
    return request.getMethod();
  }

  public String getHost() {
    return host;
  }

  public List<String> getPath() {
    return request.getPath();
  }

  public Map<String, String> getParameters() {
    return request.getParameters();
  }

  public Map<String, String> getHeaders() {
    return request.getHeaders();
  }

  public String getContent() {
    return content;
  }

  public boolean hasContent() {
    return content != null && !content.isEmpty();
  }
}
