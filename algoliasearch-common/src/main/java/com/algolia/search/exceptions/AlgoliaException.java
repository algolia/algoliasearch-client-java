package com.algolia.search.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Generic Algolia Exception */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgoliaException extends Exception {

  public AlgoliaException(String message) {
    super(message);
  }

  public AlgoliaException(String message, Exception e) {
    super(message, e);
  }
}
