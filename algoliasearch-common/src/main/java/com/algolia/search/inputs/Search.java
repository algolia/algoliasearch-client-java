package com.algolia.search.inputs;

import com.algolia.search.objects.Query;

public class Search {

  private final String params;

  public Search(Query params) {
    this.params = params.getQueryString();
  }

  @SuppressWarnings("unused")
  public String getParams() {
    return params;
  }

}
