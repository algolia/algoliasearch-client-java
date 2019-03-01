package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class BatchRequest<T> implements Serializable {

  private List<BatchOperation> requests;

  public BatchRequest(List<BatchOperation> requests) {
    this.requests = requests;
  }

  public BatchRequest(String actionType, Iterable<T> data) {
    Objects.requireNonNull(actionType, "Action type is required.");
    Objects.requireNonNull(data, "Data are required.");

    this.requests = new ArrayList<>();

    for (T item : data) {
      this.requests.add(new BatchOperation<>(actionType, item));
    }
  }

  public List<BatchOperation> getRequests() {
    return requests;
  }
}
