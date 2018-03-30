package com.algolia.search.objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;

import com.algolia.search.Defaults;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import org.assertj.core.util.Lists;
import org.junit.Test;

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
  public void queryWithAroundRadius() throws JsonProcessingException {
    Query query = new Query("").setAroundRadius(AroundRadius.of("all"));
    String serialized = objectMapper.writeValueAsString(query);
    assertThat(query.toParam()).isEqualTo("aroundRadius=all&query=");
    assertThat(serialized).isEqualTo("{\"aroundRadius\":\"all\",\"query\":\"\"}");

    query = new Query("").setAroundRadius(AroundRadius.of(1));
    serialized = objectMapper.writeValueAsString(query);
    assertThat(query.toParam()).isEqualTo("aroundRadius=1&query=");
    assertThat(serialized).isEqualTo("{\"aroundRadius\":1,\"query\":\"\"}");
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

  @Test
  public void queryWithEmptyList() {
    Query query = new Query("").setAttributesToHighlight(Lists.emptyList());
    assertThat(query.toParam()).isEqualTo("attributesToHighlight=&query=");
  }

  private ObjectMapper objectMapper =
      Defaults.DEFAULT_OBJECT_MAPPER.setVisibility(
          PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

  @Test
  public void customParameters() throws IOException {
    Query query = new Query().addCustomParameter("a", "b");
    String serialized = objectMapper.writeValueAsString(query);
    assertThat(serialized).isEqualTo("{\"a\":\"b\"}");

    Query result =
        objectMapper.readValue(
            serialized, objectMapper.getTypeFactory().constructType(Query.class));
    assertThat(result).isEqualToComparingFieldByField(query);
    assertThat(result.getCustomParameters()).containsExactly(entry("a", "b"));
  }
}
