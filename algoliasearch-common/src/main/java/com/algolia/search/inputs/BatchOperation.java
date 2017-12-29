package com.algolia.search.inputs;

import java.io.Serializable;

public interface BatchOperation extends Serializable {

  @SuppressWarnings("unused")
  String getAction();

  String getIndexName();
}
