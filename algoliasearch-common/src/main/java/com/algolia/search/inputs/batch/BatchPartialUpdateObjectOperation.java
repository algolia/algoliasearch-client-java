package com.algolia.search.inputs.batch;

import com.algolia.search.Index;
import com.algolia.search.inputs.BatchOperation;

public class BatchPartialUpdateObjectOperation<T> implements BatchOperation {

  private final T body;
  private final String indexName;

  /**
   *
   * @param indexName
   * @param body
   */
  public BatchPartialUpdateObjectOperation(String indexName, T body) {
    this.body = body;
    this.indexName = indexName;
  }

  /**
   *
   * @param body
   */
  public BatchPartialUpdateObjectOperation(T body) {
    this((String) null, body);
  }

  /**
   *
   * @param index
   * @param body
   */
  public BatchPartialUpdateObjectOperation(Index<T> index, T body) {
    this(index.getName(), body);
  }

  @Override
  public String getAction() {
    return "partialUpdateObject";
  }

  public T getBody() {
    return body;
  }

  @Override
  public String getIndexName() {
    return indexName;
  }
}
