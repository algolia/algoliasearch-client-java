package com.algolia.search.inputs;

import java.util.List;

public class BatchOperations {

  private final List<BatchOperation> requests;

  public BatchOperations(List<BatchOperation> requests) {
    this.requests = requests;
  }

  @SuppressWarnings("unused")
  public List<BatchOperation> getRequests() {
    return requests;
  }
}
