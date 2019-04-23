package com.algolia.search.models.insights;

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

  @Override
  public String toString() {
    return "InsightsResult{" + "status=" + status + ", message='" + message + '\'' + '}';
  }

  private int status;
  private String message;
}
