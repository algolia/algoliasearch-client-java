package com.algolia.search.inputs;

public interface BatchOperation {

  @SuppressWarnings("unused")
  String getAction();

  String getIndexName();
}
