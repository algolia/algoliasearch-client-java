package com.algolia.search.inputs.batch;

import com.algolia.search.AbstractIndex;
import com.algolia.search.inputs.BatchOperation;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchUpdateObjectOperation<T> implements BatchOperation {

  private final T body;
  private final String indexName;

  /**
   * @param indexName
   * @param body
   */
  public BatchUpdateObjectOperation(String indexName, T body) {
    this.body = body;
    this.indexName = indexName;
  }

  /** @param body */
  public BatchUpdateObjectOperation(T body) {
    this((String) null, body);
  }

  /**
   * @param index
   * @param body
   */
  public BatchUpdateObjectOperation(AbstractIndex<T> index, T body) {
    this(index.getName(), body);
  }

  @SuppressWarnings("unused")
  public T getBody() {
    return body;
  }

  @Override
  public String getAction() {
    return "updateObject";
  }

  @Override
  public String getIndexName() {
    return indexName;
  }
}
