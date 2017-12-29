package com.algolia.search.responses;

import java.io.Serializable;
import java.util.List;

public class MultiQueriesResult implements Serializable {

  private List<SearchResult<?>> results;

  public MultiQueriesResult setResults(List<SearchResult<?>> results) {
    this.results = results;
    return this;
  }

  public List<SearchResult<?>> getResults() {
    return results;
  }

  @Override
  public String toString() {
    return "MultiQueriesResult{" + "results=" + results + '}';
  }
}
