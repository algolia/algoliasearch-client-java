package com.algolia.search.exceptions;

public class AlgoliaApiException extends AlgoliaRuntimeException {

  public int getHttpErrorCode() {
    return httpErrorCode;
  }

  private int httpErrorCode;

  public AlgoliaApiException(String message, Throwable cause, int httpErrorCode) {
    super(message, cause);
    this.httpErrorCode = httpErrorCode;
  }

  public AlgoliaApiException(String message, int httpErrorCode) {
    super(message);
    this.httpErrorCode = httpErrorCode;
  }

  public AlgoliaApiException(Throwable cause, int httpErrorCode) {
    super(cause);
    this.httpErrorCode = httpErrorCode;
  }
}
