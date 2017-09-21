package com.algolia.search.inputs.batch;

import com.algolia.search.AbstractIndex;
import com.algolia.search.inputs.BatchOperation;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchPartialUpdateObjectNoCreateOperation<T> implements BatchOperation {

  private final T body;
  private final String indexName;

  /**
   * @param indexName
   * @param body
   */
  public BatchPartialUpdateObjectNoCreateOperation(String indexName, T body) {
    this.body = body;
    this.indexName = indexName;
  }

  /** @param body */
  public BatchPartialUpdateObjectNoCreateOperation(T body) {
    this((String) null, body);
  }

  /**
   * @param index
   * @param body
   */
  public BatchPartialUpdateObjectNoCreateOperation(AbstractIndex<T> index, T body) {
    this(index.getName(), body);
  }

  @Override
  public String getAction() {
    return "partialUpdateObjectNoCreate";
  }

  public T getBody() {
    return body;
  }

  @Override
  public String getIndexName() {
    return indexName;
  }
}
