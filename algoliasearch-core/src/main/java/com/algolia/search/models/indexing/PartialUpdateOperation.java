package com.algolia.search.models.indexing;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartialUpdateOperation<T> {

  @JsonProperty("_operation")
  private String operation;

  @JsonProperty("value")
  private T value;

  public PartialUpdateOperation(String operation, T value) {
    this.operation = operation;
    this.value = value;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public static PartialUpdateOperation<Integer> increment(Integer value) {
    return new PartialUpdateOperation<>(PartialUpdateOperationType.INCREMENT, value);
  }

  public static PartialUpdateOperation<Integer> incrementFrom(Integer value) {
    return new PartialUpdateOperation<>(PartialUpdateOperationType.INCREMENT_FROM, value);
  }

  public static PartialUpdateOperation<Integer> incrementSet(Integer value) {
    return new PartialUpdateOperation<>(PartialUpdateOperationType.INCREMENT_SET, value);
  }

  public static PartialUpdateOperation<Integer> decrement(Integer value) {
    return new PartialUpdateOperation<>(PartialUpdateOperationType.DECREMENT, value);
  }

  public static <T> PartialUpdateOperation<T> add(T value) {
    return new PartialUpdateOperation<>(PartialUpdateOperationType.ADD, value);
  }

  public static <T> PartialUpdateOperation<T> addUnique(T value) {
    return new PartialUpdateOperation<>(PartialUpdateOperationType.ADD_UNIQUE, value);
  }

  public static <T> PartialUpdateOperation<T> remove(T value) {
    return new PartialUpdateOperation<>(PartialUpdateOperationType.REMOVE, value);
  }
}
