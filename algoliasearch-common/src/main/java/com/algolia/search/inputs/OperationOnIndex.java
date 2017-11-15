package com.algolia.search.inputs;

import com.algolia.search.objects.CopyScope;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationOnIndex {

  private final String operation;
  private final String destination;
  private final List<CopyScope> scopes;

  public OperationOnIndex(String operation, String destination) {
    this(operation, destination, null);
  }

  public OperationOnIndex(String operation, String destination, List<CopyScope> scopes) {
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

  public List<CopyScope> getScopes() {
    return scopes;
  }
}
