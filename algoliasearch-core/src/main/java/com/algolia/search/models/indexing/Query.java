package com.algolia.search.models.indexing;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Algolia Search Query
 *
 * @see <a href="https://www.algolia.com/doc/api-reference/api-parameters/query/">Algolia.com</a>
 */
public class Query extends SearchParameters<Query> {

  public Query() {}

  public Query(String query) {
    this.query = query;
  }

  @Override
  @JsonIgnore
  public Query getThis() {
    return this;
  }
}
