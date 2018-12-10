package com.algolia.search.inputs.insights;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsightsResult implements Serializable {

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public InsightsResult setMessage(String message) {
    this.message = message;
    return this;
  }

  private String status;
  private String message;
}
