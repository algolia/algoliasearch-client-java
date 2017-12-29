package com.algolia.search.inputs;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Batch implements Serializable {

  private final List<BatchOperation> requests;

  public Batch(List<BatchOperation> requests) {
    this.requests = requests;
  }

  @SuppressWarnings("unused")
  public List<BatchOperation> getRequests() {
    return requests;
  }
}
