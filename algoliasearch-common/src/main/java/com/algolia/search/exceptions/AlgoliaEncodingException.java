package com.algolia.search.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgoliaEncodingException extends AlgoliaException {
  public AlgoliaEncodingException(String message) {
    super(message);
  }

  public AlgoliaEncodingException(String message, Exception e) {
    super(message, e);
  }
}
