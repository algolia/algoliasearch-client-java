package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultipleQueries implements Serializable {

  public MultipleQueries(String indexName, Query params) {
    this.indexName = indexName;
    this.params = params;
  }

  public String getIndexName() {
    return indexName;
  }

  public MultipleQueries setIndexName(String indexName) {
    this.indexName = indexName;
    return this;
  }

  private String indexName;

  public Query getParams() {
    return params;
  }

  public MultipleQueries setParams(Query params) {
    this.params = params;
    return this;
  }

  private Query params;
}
