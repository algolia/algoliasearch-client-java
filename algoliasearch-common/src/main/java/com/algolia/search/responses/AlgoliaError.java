package com.algolia.search.responses;

public class AlgoliaError {

  private String message;

  public String getMessage() {
    return message == null ? "" : message;
  }

  @SuppressWarnings("unused")
  public AlgoliaError setMessage(String message) {
    this.message = message;
    return this;
  }

  @Override
  public String toString() {
    return "AlgoliaError{" +
      "message='" + message + '\'' +
      '}';
  }
}
