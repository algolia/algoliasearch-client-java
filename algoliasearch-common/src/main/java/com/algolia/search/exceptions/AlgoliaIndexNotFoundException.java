package com.algolia.search.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Algolia Exception if an index does not exists */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgoliaIndexNotFoundException extends AlgoliaException {

  public AlgoliaIndexNotFoundException(String message) {
    super(message);
  }
}
