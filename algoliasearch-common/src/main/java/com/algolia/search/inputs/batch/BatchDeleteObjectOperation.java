package com.algolia.search.inputs.batch;

import com.algolia.search.AbstractIndex;
import com.algolia.search.inputs.BatchOperation;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchDeleteObjectOperation implements BatchOperation {

  private final String objectID;
  private final String indexName;

  /**
   * @param indexName
   * @param objectID
   */
  public BatchDeleteObjectOperation(String indexName, String objectID) {
    this.objectID = objectID;
    this.indexName = indexName;
  }

  /** @param objectID */
  public BatchDeleteObjectOperation(String objectID) {
    this((String) null, objectID);
  }

  /**
   * @param index
   * @param objectID
   */
  public BatchDeleteObjectOperation(AbstractIndex<?> index, String objectID) {
    this(index.getName(), objectID);
  }

  @SuppressWarnings("unused")
  public String getObjectID() {
    return objectID;
  }

  @Override
  public String getIndexName() {
    return indexName;
  }

  @Override
  public String getAction() {
    return "deleteObject";
  }
}
