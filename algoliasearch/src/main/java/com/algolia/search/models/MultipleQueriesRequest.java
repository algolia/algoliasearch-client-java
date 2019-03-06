package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultipleQueriesRequest implements Serializable {

  public MultipleQueriesRequest() {}

  public MultipleQueriesRequest(String strategy, List<MultipleQueries> requests) {
    this.strategy = strategy;
    this.requests = requests;
  }

  public MultipleQueriesRequest setRequests(List<MultipleQueries> requests) {
    this.requests = requests;
    return this;
  }

  public MultipleQueriesRequest setStrategy(String strategy) {
    this.strategy = strategy;
    return this;
  }

  public String getStrategy() {
    return strategy;
  }

  public List<MultipleQueries> getRequests() {
    return requests;
  }

  private String strategy;
  private List<MultipleQueries> requests;
}
