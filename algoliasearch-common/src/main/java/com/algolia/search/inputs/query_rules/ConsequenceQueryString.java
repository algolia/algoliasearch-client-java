package com.algolia.search.inputs.query_rules;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsequenceQueryString extends ConsequenceQuery {

  private final String query;

  public ConsequenceQueryString(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }
}
