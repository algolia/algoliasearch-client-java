package com.algolia.search.models;

import java.io.InputStream;

public class AlgoliaHttpResponse {

  public AlgoliaHttpResponse(int httpStatusCode, InputStream body) {
    this.httpStatusCode = httpStatusCode;
    this.body = body;
  }

  public AlgoliaHttpResponse(int httpStatusCode, String error) {
    this.httpStatusCode = httpStatusCode;
    this.error = error;
  }

  public AlgoliaHttpResponse(boolean isTimedOut) {
    this.isTimedOut = isTimedOut;
  }

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

  public String getError() {
    return error;
  }

  public AlgoliaHttpResponse setError(String error) {
    this.error = error;
    return this;
  }

  public boolean isTimedOut() {
    return isTimedOut;
  }

  public AlgoliaHttpResponse setTimedOut(boolean timedOut) {
    isTimedOut = timedOut;
    return this;
  }

  private int httpStatusCode;
  private InputStream body;
  private String error;
  private boolean isTimedOut;
}
