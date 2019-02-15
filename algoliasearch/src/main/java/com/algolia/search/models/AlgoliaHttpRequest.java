package com.algolia.search.models;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;

public class AlgoliaHttpRequest {

  public AlgoliaHttpRequest(
      HttpMethod method, URL uri, HashMap<String, Collection<String>> headers, InputStream body) {
    this.method = method;
    this.uri = uri;
    this.headers = headers;
    this.body = body;
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

  private HttpMethod method;
  private URL uri;
  private HashMap<String, Collection<String>> headers;
  private InputStream body;
}
