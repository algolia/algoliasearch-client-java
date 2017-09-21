package com.algolia.search.inputs.batch;

import com.algolia.search.AbstractIndex;
import com.algolia.search.inputs.BatchOperation;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchDeleteIndexOperation implements BatchOperation {

  private final String indexName;

  /** */
  public BatchDeleteIndexOperation() {
    this((String) null);
  }

  /** @param indexName */
  public BatchDeleteIndexOperation(String indexName) {
    this.indexName = indexName;
  }

  /** @param index */
  public BatchDeleteIndexOperation(AbstractIndex<?> index) {
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
