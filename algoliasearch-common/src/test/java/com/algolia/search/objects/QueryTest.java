package com.algolia.search.objects;

import org.junit.Test;

import java.util.Collections;

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
    assertThat(query.toParam()).isEqualTo("query=%26%3F%40%3A%3D");
  }

  @Test
  public void queryWithUTF8() {
    Query query = new Query("é®„");
    assertThat(query.toParam()).isEqualTo("query=%C3%A9%C2%AE%E2%80%9E");
  }

  @Test
  public void queryWithMultipleParams() {
    Query query = new Query("é®„").setTagFilters(Collections.singletonList("(attribute)"));
    assertThat(query.toParam()).isEqualTo("tagFilters=%28attribute%29&query=%C3%A9%C2%AE%E2%80%9E");
  }

  @Test
  public void queryWithDistinct() {
    Query query = new Query("").setDistinct(Distinct.of(0));
    assertThat(query.toParam()).isEqualTo("distinct=0&query=");
  }

  @Test
  public void queryWithAroundRadius() {
    Query query = new Query("").setAroundRadiusAll();
    assertThat(query.toParam()).isEqualTo("aroundRadius=all&query=");

    query = new Query("").setAroundRadius(1);
    assertThat(query.toParam()).isEqualTo("aroundRadius=1&query=");
  }

  @Test
  public void queryWithRemoveStopWords() {
    Query query = new Query("").setRemoveStopWords(RemoveStopWords.of(true));
    assertThat(query.toParam()).isEqualTo("removeStopWords=true&query=");
  }

  @Test
  public void queryWithIgnorePlurals() {
    Query query = new Query("").setIgnorePlurals(IgnorePlurals.of(true));
    assertThat(query.toParam()).isEqualTo("query=&ignorePlurals=true");
  }
}