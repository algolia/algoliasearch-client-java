package com.algolia.search.exceptions;

public class AlgoliaRetryException extends AlgoliaRuntimeException {
  public AlgoliaRetryException(String message, Throwable cause) {
    super(message, cause);
  }

  public AlgoliaRetryException(String message) {
    super(message);
  }

  public AlgoliaRetryException(Throwable cause) {
    super(cause);
  }
}
