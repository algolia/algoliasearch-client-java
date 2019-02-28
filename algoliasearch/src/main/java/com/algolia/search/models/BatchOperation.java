package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchOperation<T> implements Serializable {

  public BatchOperation(String indexName, String action, T body) {
    this.action = action;
    this.indexName = indexName;
    this.body = body;
  }

  public String getAction() {
    return action;
  }

  public BatchOperation<T> setAction(String action) {
    this.action = action;
    return this;
  }

  public String getIndexName() {
    return indexName;
  }

  public BatchOperation<T> setIndexName(String indexName) {
    this.indexName = indexName;
    return this;
  }

  public T getBody() {
    return body;
  }

  public BatchOperation<T> setBody(T body) {
    this.body = body;
    return this;
  }

  private String action;
  private String indexName;
  private T body;
}
