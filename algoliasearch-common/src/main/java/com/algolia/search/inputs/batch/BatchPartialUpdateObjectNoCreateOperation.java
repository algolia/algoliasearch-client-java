package com.algolia.search.inputs.batch;

import com.algolia.search.Index;
import com.algolia.search.inputs.BatchOperation;

public class BatchPartialUpdateObjectNoCreateOperation<T> implements BatchOperation {

  private final T body;
  private final String indexName;

  /**
   *
   * @param indexName
   * @param body
   */
  public BatchPartialUpdateObjectNoCreateOperation(String indexName, T body) {
    this.body = body;
    this.indexName = indexName;
  }

  /**
   *
   * @param body
   */
  public BatchPartialUpdateObjectNoCreateOperation(T body) {
    this((String) null, body);
  }

  /**
   *
   * @param index
   * @param body
   */
  public BatchPartialUpdateObjectNoCreateOperation(Index<T> index, T body) {
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
