package com.algolia.search.models.indexing;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class Explain {

  public QueryMatch getQueryMatch() {
    return queryMatch;
  }

  public Explain setQueryMatch(QueryMatch queryMatch) {
    this.queryMatch = queryMatch;
    return this;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public Explain setParams(Map<String, Object> params) {
    this.params = params;
    return this;
  }

  @JsonProperty("match")
  private QueryMatch queryMatch;

  private Map<String, Object> params;
}
