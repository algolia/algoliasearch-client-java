package com.algolia.search.inputs.insights;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
