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

  @Test
  public void queryWithCustomParameters() {
    Query query = new Query().addCustomParameter("myKey", "myValue");
    assertThat(query.toParam()).isEqualTo("myKey=myValue");
  }

  @Test
  public void queryWithHTMLEntities() {
    Query query = new Query("&?@:=");
    assertThat(query.toParam()).isEqualTo("query=%26?@:%3D");
  }

  @Test
  public void queryWithUTF8() {
    Query query = new Query("é®„");
    assertThat(query.toParam()).isEqualTo("query=%C3%A9%C2%AE%E2%80%9E");
  }
}