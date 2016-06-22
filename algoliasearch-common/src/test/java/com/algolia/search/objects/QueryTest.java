package com.algolia.search.objects;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryTest {

  @Test
  public void queryStringWithQuery() {
    Query query = new Query();
    query.setQuery("search");
    assertThat(query.toParam()).isEqualTo("query=search");
  }

  @Test
  public void queryStringEmpty() {
    Query query = new Query();
    assertThat(query.toParam()).isEqualTo("");
  }
}