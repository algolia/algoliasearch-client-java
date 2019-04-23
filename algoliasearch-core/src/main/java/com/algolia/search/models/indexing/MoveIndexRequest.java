package com.algolia.search.models.indexing;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoveIndexRequest implements Serializable {

  public String getOperation() {
    return operation;
  }

  public MoveIndexRequest setOperation(String operation) {
    this.operation = operation;
    return this;
  }

  public String getDestination() {
    return destination;
  }

  public MoveIndexRequest setDestination(String destination) {
    this.destination = destination;
    return this;
  }

  @Override
  public String toString() {
    return "MoveIndexRequest{"
        + "operation='"
        + operation
        + '\''
        + ", destination='"
        + destination
        + '\''
        + '}';
  }

  private String operation;
  private String destination;
}
