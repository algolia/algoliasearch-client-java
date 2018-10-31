package com.algolia.search.objects;

public class Query extends QueryBase<Query> {
  public Query() {}

  public Query(String query) {
    this.query = query;
  }
}
