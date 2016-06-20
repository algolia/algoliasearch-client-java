package com.algolia.search.inputs;

import java.util.List;

public class Batch {

  private final List<BatchOperation> requests;

  public Batch(List<BatchOperation> requests) {
    this.requests = requests;
  }

  @SuppressWarnings("unused")
  public List<BatchOperation> getRequests() {
    return requests;
  }
}
