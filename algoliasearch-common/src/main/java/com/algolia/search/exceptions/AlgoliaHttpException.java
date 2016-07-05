package com.algolia.search.exceptions;

/**
 * Algolia Exception if there was an unexpected response code (!= 2XX)
 */
public class AlgoliaHttpException extends AlgoliaException {

  /**
   * HTTP response code of the Algolia API
   */
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
