package com.algolia.search.models.common;

import java.io.Serializable;
import java.util.List;

public class ListIndicesResponse implements Serializable {

  public List<IndicesResponse> getIndices() {
    return items;
  }

  public ListIndicesResponse setItems(List<IndicesResponse> items) {
    this.items = items;
    return this;
  }

  private List<IndicesResponse> items;
}
