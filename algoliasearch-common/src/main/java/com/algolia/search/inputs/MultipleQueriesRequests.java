package com.algolia.search.inputs;

import com.algolia.search.objects.IndexQuery;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleQueriesRequests {

  private final List<QueryWithIndex> requests;

  public MultipleQueriesRequests(List<IndexQuery> requests) {
    this.requests = requests
      .stream()
      .map(MultipleQueriesRequests.QueryWithIndex::new)
      .collect(Collectors.toList());
  }

  public List<QueryWithIndex> getRequests() {
    return requests;
  }

  private static class QueryWithIndex {

    private final String indexName;
    private final String params;

    QueryWithIndex(@Nonnull IndexQuery q) {
      this.indexName = q.getIndexName();
      this.params = q.getQuery().getQueryString();
    }

    public String getIndexName() {
      return indexName;
    }

    public String getParams() {
      return params;
    }
  }
}


