package com.algolia.search.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Algolia Exception if there was an unexpected response code (!= 2XX) */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgoliaHttpException extends AlgoliaException {

  /** HTTP response code of the Algolia API */
  private int httpResponseCode;

  public AlgoliaHttpException(int httpResponseCode, String message) {
    super(message);
    this.httpResponseCode = httpResponseCode;
  }

  @SuppressWarnings("unused")
  public int getHttpResponseCode() {
    return httpResponseCode;
  }
}
