package com.algolia.search.models.common;

import com.algolia.search.models.search.Query;
import com.algolia.search.serializer.QuerySerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultipleQueries implements Serializable {

  public MultipleQueries() {}

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

  @JsonSerialize(using = QuerySerializer.class)
  private Query params;
}
