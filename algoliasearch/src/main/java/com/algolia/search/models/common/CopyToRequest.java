package com.algolia.search.models.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CopyToRequest implements Serializable {

  public String getOperation() {
    return operation;
  }

  public CopyToRequest setOperation(String operation) {
    this.operation = operation;
    return this;
  }

  public List<String> getScope() {
    return scope;
  }

  public CopyToRequest setScope(List<String> scope) {
    this.scope = scope;
    return this;
  }

  public String getDestination() {
    return destination;
  }

  public CopyToRequest setDestination(String destination) {
    this.destination = destination;
    return this;
  }

  private String operation;
  private List<String> scope;
  private String destination;
}
