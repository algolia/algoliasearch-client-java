package com.algolia.search.models;

import java.util.List;

public class ListIndicesResponse {
  public List<IndicesResponse> getItems() {
    return items;
  }

  public ListIndicesResponse setItems(List<IndicesResponse> items) {
    this.items = items;
    return this;
  }

  private List<IndicesResponse> items;
}
