package com.algolia.search.inputs.batch;

import com.algolia.search.Index;
import com.algolia.search.inputs.BatchOperation;

public class BatchDeleteIndexOperation implements BatchOperation {

  private final String indexName;

  /**
   *
   */
  public BatchDeleteIndexOperation() {
    this((String) null);
  }

  /**
   *
   * @param indexName
   */
  public BatchDeleteIndexOperation(String indexName) {
    this.indexName = indexName;
  }

  /**
   *
   * @param index
   */
  public BatchDeleteIndexOperation(Index<?> index) {
    this(index.getName());
  }

  @Override
  public String getIndexName() {
    return indexName;
  }

  @Override
  public String getAction() {
    return "delete";
  }
}
