package com.algolia.search.exceptions;

public class AlgoliaRuntimeException extends RuntimeException {

  public AlgoliaRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public AlgoliaRuntimeException(String message) {
    super(message);
  }

  public AlgoliaRuntimeException(Throwable cause) {
    super(cause);
  }
}
