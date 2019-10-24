package com.algolia.search.models.indexing;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Explain {

  public QueryMatch getQueryMatch() {
    return queryMatch;
  }

  public Explain setQueryMatch(QueryMatch queryMatch) {
    this.queryMatch = queryMatch;
    return this;
  }

  @JsonProperty("match")
  private QueryMatch queryMatch;
}
