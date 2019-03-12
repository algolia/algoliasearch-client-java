package com.algolia.search.models;

import com.algolia.search.inputs.MultipleGetObject;
import java.io.Serializable;
import java.util.List;

public class MultipleGetObjectsRequest implements Serializable {

  public MultipleGetObjectsRequest(List<MultipleGetObject> requests) {
    this.requests = requests;
  }

  public List<MultipleGetObject> getRequests() {
    return requests;
  }

  public MultipleGetObjectsRequest setRequests(List<MultipleGetObject> requests) {
    this.requests = requests;
    return this;
  }

  private List<MultipleGetObject> requests;
}
