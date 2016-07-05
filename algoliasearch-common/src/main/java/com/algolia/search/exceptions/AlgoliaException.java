package com.algolia.search.exceptions;

/**
 * Generic Algolia Exception
 */
public class AlgoliaException extends Exception {

  public AlgoliaException(String message) {
    super(message);
  }

  public AlgoliaException(String message, Exception e) {
    super(message, e);
  }

}
