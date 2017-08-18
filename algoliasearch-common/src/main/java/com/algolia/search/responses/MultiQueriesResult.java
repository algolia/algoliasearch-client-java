package com.algolia.search.responses;

import java.util.List;

public class MultiQueriesResult {

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
    return "MultiQueriesResult{" +
      "results=" + results +
      '}';
  }
}
