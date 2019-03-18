package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.annotation.Nonnull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings({"unused", "WeakerAccess"})
public class BatchOperation<T> implements Serializable {

  public static <T> BatchOperation<T> createAddObject(@Nonnull T body) {
    return new BatchOperation<>(ActionEnum.AddObject, body);
  }

  public static <T> BatchOperation<T> createUpdateObject(@Nonnull T body) {
    return new BatchOperation<>(ActionEnum.UpdateObject, body);
  }

  public static <T> BatchOperation<T> createPartialUpdateObject(@Nonnull T body) {
    return new BatchOperation<>(ActionEnum.PartialUpdateObject, body);
  }

  public static <T> BatchOperation<T> createPartialUpdateObjectNoCreate(@Nonnull T body) {
    return new BatchOperation<>(ActionEnum.PartialUpdateObjectNoCreate, body);
  }

  public static <T> BatchOperation<T> createDeleteObject(@Nonnull T body) {
    return new BatchOperation<>(ActionEnum.DeleteObject, body);
  }

  public static <T> BatchOperation<T> createDelete(@Nonnull T body) {
    return new BatchOperation<>(ActionEnum.Delete, body);
  }

  public BatchOperation(String indexName, String action, T body) {
    this.action = action;
    this.indexName = indexName;
    this.body = body;
  }

  public BatchOperation(String indexName, String action) {
    this.action = action;
    this.indexName = indexName;
  }

  public BatchOperation(String action, T body) {
    this.action = action;
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
