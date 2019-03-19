package com.algolia.search.models;

import java.io.Serializable;

public class InsightsResult implements Serializable {

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public InsightsResult setMessage(String message) {
    this.message = message;
    return this;
  }

  private int status;
  private String message;
}
