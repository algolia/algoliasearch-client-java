package com.algolia.search.models;

import java.io.Serializable;
import java.util.List;

public class BatchResponse extends IndexingResponse implements Serializable {

  public List<String> getObjectIDs() {
    return objectIDs;
  }

  public BatchResponse setObjectIDs(List<String> objectIDs) {
    this.objectIDs = objectIDs;
    return this;
  }

  private List<String> objectIDs;
}
