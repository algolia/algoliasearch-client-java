package com.algolia.search.models.indexing;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrowseIndexResponse<T> extends SearchResult<T> {
  public String getCursor() {
    return cursor;
  }

  public BrowseIndexResponse<T> setCursor(String cursor) {
    this.cursor = cursor;
    return this;
  }

  private String cursor;
}
