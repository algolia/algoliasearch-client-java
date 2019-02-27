package com.algolia.search.models;

import com.algolia.search.inputs.MultipleGetObjectsRequests;
import java.io.Serializable;
import java.util.List;

public class MultipleGetObjectsRequest implements Serializable {

  public MultipleGetObjectsRequest(List<MultipleGetObjectsRequests> requests) {
    this.requests = requests;
  }

  public List<MultipleGetObjectsRequests> getRequests() {
    return requests;
  }

  public MultipleGetObjectsRequest setRequests(List<MultipleGetObjectsRequests> requests) {
    this.requests = requests;
    return this;
  }

  private List<MultipleGetObjectsRequests> requests;
}
