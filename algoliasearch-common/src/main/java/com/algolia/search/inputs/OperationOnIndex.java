package com.algolia.search.inputs;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationOnIndex implements Serializable {

  private final String operation;
  private final String destination;
  private final List<String> scopes;

  public OperationOnIndex(String operation, String destination) {
    this(operation, destination, null);
  }

  public OperationOnIndex(String operation, String destination, List<String> scopes) {
    this.operation = operation;
    this.destination = destination;
    this.scopes = scopes;
  }

  public String getOperation() {
    return operation;
  }

  public String getDestination() {
    return destination;
  }

  public List<String> getScopes() {
    return scopes;
  }
}
