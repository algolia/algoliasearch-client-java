package com.algolia.search.models.indexing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrowseIndexQuery extends SearchParameters<BrowseIndexQuery> {

  public BrowseIndexQuery() {}

  public BrowseIndexQuery(String query) {
    this.query = query;
  }

  public String getCursor() {
    return cursor;
  }

  public BrowseIndexQuery setCursor(String cursor) {
    this.cursor = cursor;
    return this;
  }

  private String cursor;

  @Override
  @JsonIgnore
  public BrowseIndexQuery getThis() {
    return this;
  }
}
