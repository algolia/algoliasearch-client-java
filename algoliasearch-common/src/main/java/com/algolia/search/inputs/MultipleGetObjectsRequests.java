package com.algolia.search.inputs;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.annotation.Nonnull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultipleGetObjectsRequests implements Serializable {

  private final String indexName;
  private final String objectID;

  public MultipleGetObjectsRequests(@Nonnull String indexName, @Nonnull String objectID) {
    this.indexName = indexName;
    this.objectID = objectID;
  }

  public String getIndexName() {
    return indexName;
  }

  public String getObjectID() {
    return objectID;
  }
}
