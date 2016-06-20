package com.algolia.search.inputs.batch;

import com.algolia.search.Index;
import com.algolia.search.inputs.BatchOperation;

public class BatchClearIndexOperation implements BatchOperation {

  private final String indexName;

  /**
   *
   */
  public BatchClearIndexOperation() {
    this((String) null);
  }

  /**
   *
   * @param indexName
   */
  public BatchClearIndexOperation(String indexName) {
    this.indexName = indexName;
  }

  /**
   *
   * @param index
   */
  public BatchClearIndexOperation(Index<?> index) {
    this(index.getName());
  }

  @Override
  public String getIndexName() {
    return indexName;
  }

  @Override
  public String getAction() {
    return "clear";
  }
}
