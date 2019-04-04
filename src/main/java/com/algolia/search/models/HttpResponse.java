package com.algolia.search.models;

import java.io.InputStream;

public class HttpResponse {

  public HttpResponse(int httpStatusCode, InputStream body) {
    this.httpStatusCode = httpStatusCode;
    this.body = body;
  }

  public HttpResponse(int httpStatusCode, String error) {
    this.httpStatusCode = httpStatusCode;
    this.error = error;
  }

  public HttpResponse(boolean isTimedOut) {
    this.isTimedOut = isTimedOut;
  }

  public int getHttpStatusCode() {
    return httpStatusCode;
  }

  public HttpResponse setHttpStatusCode(int httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
    return this;
  }

  public InputStream getBody() {
    return body;
  }

  public HttpResponse setBody(InputStream body) {
    this.body = body;
    return this;
  }

  public String getError() {
    return error;
  }

  public HttpResponse setError(String error) {
    this.error = error;
    return this;
  }

  public boolean isTimedOut() {
    return isTimedOut;
  }

  public HttpResponse setTimedOut(boolean timedOut) {
    isTimedOut = timedOut;
    return this;
  }

  private int httpStatusCode;
  private InputStream body;
  private String error;
  private boolean isTimedOut;
}
