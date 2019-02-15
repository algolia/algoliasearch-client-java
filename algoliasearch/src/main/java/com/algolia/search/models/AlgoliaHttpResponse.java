package com.algolia.search.models;

import java.io.InputStream;

public class AlgoliaHttpResponse {

  public AlgoliaHttpResponse(int httpStatusCode, InputStream body) {
    this.httpStatusCode = httpStatusCode;
    this.body = body;
  }

  public AlgoliaHttpResponse() {}

  public int getHttpStatusCode() {
    return httpStatusCode;
  }

  public AlgoliaHttpResponse setHttpStatusCode(int httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
    return this;
  }

  public InputStream getBody() {
    return body;
  }

  public AlgoliaHttpResponse setBody(InputStream body) {
    this.body = body;
    return this;
  }

  private int httpStatusCode;
  private InputStream body;
}
