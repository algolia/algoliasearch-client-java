package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class BatchRequest implements Serializable {

  private List<BatchOperation> requests;

  public BatchRequest(List<BatchOperation> requests) {
    this.requests = requests;
  }

  public List<BatchOperation> getRequests() {
    return requests;
  }
}
