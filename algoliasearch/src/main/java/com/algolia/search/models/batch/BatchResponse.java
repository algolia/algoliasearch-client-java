package com.algolia.search.models.batch;

import com.algolia.search.models.common.IndexingResponse;
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
