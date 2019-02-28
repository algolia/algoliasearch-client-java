package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultipleQueriesRequest implements Serializable {

  public String getStrategy() {
    return strategy;
  }

  public MultipleQueriesRequest setStrategy(String strategy) {
    this.strategy = strategy;
    return this;
  }

  public List<MultipleQueries> getRequests() {
    return requests;
  }

  public MultipleQueriesRequest setRequests(List<MultipleQueries> requests) {
    this.requests = requests;
    return this;
  }

  private String strategy;
  private List<MultipleQueries> requests;
}
