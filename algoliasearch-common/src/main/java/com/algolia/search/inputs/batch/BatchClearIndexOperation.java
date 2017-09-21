package com.algolia.search.inputs.batch;

import com.algolia.search.AbstractIndex;
import com.algolia.search.inputs.BatchOperation;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchClearIndexOperation implements BatchOperation {

  private final String indexName;

  /** */
  public BatchClearIndexOperation() {
    this((String) null);
  }

  /** @param indexName */
  public BatchClearIndexOperation(String indexName) {
    this.indexName = indexName;
  }

  /** @param index */
  public BatchClearIndexOperation(AbstractIndex<?> index) {
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
