package com.algolia.search.exceptions;

public class AlgoliaApiException extends RuntimeException {

  private static final long serialVersionUID = -989193527186415117L;

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
