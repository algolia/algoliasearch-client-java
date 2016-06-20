package com.algolia.search.exceptions;

public class AlgoliaException extends Exception {

  public AlgoliaException(String message) {
    super(message);
  }

  public AlgoliaException(String message, Exception e) {
    super(message, e);
  }

}
