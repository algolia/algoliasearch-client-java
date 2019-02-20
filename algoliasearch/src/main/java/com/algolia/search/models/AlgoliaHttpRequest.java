package com.algolia.search.models;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;

public class AlgoliaHttpRequest {

  public AlgoliaHttpRequest(
      HttpMethod method,
      String methodPath,
      HashMap<String, Collection<String>> headers,
      int timeout) {
    this.method = method;
    this.methodPath = methodPath;
    this.headers = headers;
    this.timeout = timeout;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public AlgoliaHttpRequest setMethod(HttpMethod method) {
    this.method = method;
    return this;
  }

  public URL getUri() {
    return uri;
  }

  public AlgoliaHttpRequest setUri(URL uri) {
    this.uri = uri;
    return this;
  }

  public String getMethodPath() {
    return methodPath;
  }

  public AlgoliaHttpRequest setMethodPath(String methodPath) {
    this.methodPath = methodPath;
    return this;
  }

  public HashMap<String, Collection<String>> getHeaders() {
    return headers;
  }

  public AlgoliaHttpRequest setHeaders(HashMap<String, Collection<String>> headers) {
    this.headers = headers;
    return this;
  }

  public InputStream getBody() {
    return body;
  }

  public AlgoliaHttpRequest setBody(InputStream body) {
    this.body = body;
    return this;
  }

  public int getTimeout() {
    return timeout;
  }

  public AlgoliaHttpRequest setTimeout(int timeout) {
    this.timeout = timeout;
    return this;
  }

  private HttpMethod method;
  private URL uri;
  private String methodPath;
  private HashMap<String, Collection<String>> headers;
  private InputStream body;
  private int timeout;
}
