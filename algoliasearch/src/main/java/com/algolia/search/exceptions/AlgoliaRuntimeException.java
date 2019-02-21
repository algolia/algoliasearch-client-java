package com.algolia.search.exceptions;

public class AlgoliaRuntimeException extends RuntimeException {

  private static final long serialVersionUID = -5603709465760256932L;

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
