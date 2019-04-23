package com.algolia.search.models;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

  public HttpRequest(
      HttpMethod method, String methodPath, Map<String, String> headers, int timeout) {
    this.method = method;
    this.methodPath = methodPath;
    this.headers = headers;
    this.timeout = timeout;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public HttpRequest setMethod(HttpMethod method) {
    this.method = method;
    return this;
  }

  public URL getUri() {
    return uri;
  }

  public HttpRequest setUri(URL uri) {
    this.uri = uri;
    return this;
  }

  public String getMethodPath() {
    return methodPath;
  }

  public HttpRequest setMethodPath(String methodPath) {
    this.methodPath = methodPath;
    return this;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public HttpRequest setHeaders(HashMap<String, String> headers) {
    this.headers = headers;
    return this;
  }

  public InputStream getBody() {
    return body;
  }

  public HttpRequest setBody(InputStream body) {
    this.body = body;
    return this;
  }

  public int getTimeout() {
    return timeout;
  }

  public HttpRequest setTimeout(int timeout) {
    this.timeout = timeout;
    return this;
  }

  public void incrementTimeout(int retryCount) {
    this.timeout *= (retryCount + 1);
  }

  @Override
  public String toString() {
    return "HttpRequest{"
        + "method="
        + method
        + ", uri="
        + uri
        + ", methodPath='"
        + methodPath
        + '\''
        + ", headers="
        + headers
        + ", timeout="
        + timeout
        + '}';
  }

  private HttpMethod method;
  private URL uri;
  private String methodPath;
  private Map<String, String> headers;
  private InputStream body;
  private int timeout;
}
