package com.algolia.search.inputs;

import com.algolia.search.objects.Query;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Search {

  private final String params;

  public Search(Query params) {
    this.params = params.toParam();
  }

  @SuppressWarnings("unused")
  public String getParams() {
    return params;
  }

}
