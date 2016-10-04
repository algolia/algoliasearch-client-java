package com.algolia.search.exceptions;

/**
 * Algolia Exception if an index does not exists
 */
public class AlgoliaIndexNotFoundException extends AlgoliaException {


  public AlgoliaIndexNotFoundException(String message) {
    super(message);
  }

}
