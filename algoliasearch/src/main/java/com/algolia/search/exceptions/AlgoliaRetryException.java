package com.algolia.search.exceptions;

public class AlgoliaRetryException extends AlgoliaRuntimeException {

  private static final long serialVersionUID = 815496271516822851L;

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
