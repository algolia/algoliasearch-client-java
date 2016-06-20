package com.algolia.search.inputs.batch;

import com.algolia.search.Index;
import com.algolia.search.inputs.BatchOperation;

public class BatchAddObjectOperation<T> implements BatchOperation {

  private final T body;
  private final String indexName;

  /**
   *
   * @param indexName
   * @param body
   */
  public BatchAddObjectOperation(String indexName, T body) {
    this.body = body;
    this.indexName = indexName;
  }

  /**
   *
   * @param index
   * @param body
   */
  public BatchAddObjectOperation(Index<T> index, T body) {
    this(index.getName(), body);
  }

  /**
   *
   * @param body
   */
  public BatchAddObjectOperation(T body) {
    this((String) null, body);
  }

  @Override
  public String getAction() {
    return "addObject";
  }

  @SuppressWarnings("unused")
  public T getBody() {
    return body;
  }

  @Override
  public String getIndexName() {
    return indexName;
  }
}
