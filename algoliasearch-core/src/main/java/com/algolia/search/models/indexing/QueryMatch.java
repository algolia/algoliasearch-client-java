package com.algolia.search.models.indexing;

import java.util.List;

public class QueryMatch {
  public List<Alternative> getAlternatives() {
    return alternatives;
  }

  public QueryMatch setAlternatives(List<Alternative> alternatives) {
    this.alternatives = alternatives;
    return this;
  }

  private List<Alternative> alternatives;
}
