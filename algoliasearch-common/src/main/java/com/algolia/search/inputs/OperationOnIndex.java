package com.algolia.search.inputs;

public class OperationOnIndex {

  private final String operation;
  private final String destination;

  public OperationOnIndex(String operation, String destination) {
    this.operation = operation;
    this.destination = destination;
  }

  public String getOperation() {
    return operation;
  }

  public String getDestination() {
    return destination;
  }

}
