package com.algolia.search.models.recommendation;

import java.io.Serializable;

@SuppressWarnings("unused")
public class SetStrategyResponse implements Serializable {

  public SetStrategyResponse() {}

  public Integer getStatus() {
    return status;
  }

  public SetStrategyResponse setStatus(Integer status) {
    this.status = status;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public SetStrategyResponse setMessage(String message) {
    this.message = message;
    return this;
  }

  private Integer status;
  private String message;
}
