package com.algolia.search.serialization;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.algolia.search.Defaults;
import com.algolia.search.models.rules.ConsequenceParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestFiltersJsonDeserializer {

  @Test
  void testListAndString() throws JsonProcessingException {
    String json =
        "{\"optionalFilters\":[[\"product_type:Water<score=3>\",\"product_type:SparklingWaters<score=2>\",\"category_hierarchy_tier3:Grocery>Beverages>Juices<score=1>\"],\"category_hierarchy_tier3:-Fitness&Nutrition>Bars&Drinks>Drinks<score=100>\"]}";
    ConsequenceParams consequenceParams =
        Defaults.getObjectMapper().readValue(json, ConsequenceParams.class);
    List<List<String>> optionalFilters = consequenceParams.getOptionalFilters();
    optionalFilters.forEach(filter -> assertThat(filter).isInstanceOf(List.class));
    assertThat(optionalFilters.size()).isEqualTo(2);
    assertThat(optionalFilters.get(0).size()).isEqualTo(3);
    assertThat(optionalFilters.get(1).size()).isEqualTo(1);
  }

  @Test
  void testStringOnly() throws JsonProcessingException {
    String json =
        "{\"optionalFilters\":[\"product_type:Water<score=3>\",\"product_type:SparklingWaters<score=2>\",\"category_hierarchy_tier3:Grocery>Beverages>Juices<score=1>\",\"category_hierarchy_tier3:-Fitness&Nutrition>Bars&Drinks>Drinks<score=100>\"]}";
    ConsequenceParams consequenceParams =
        Defaults.getObjectMapper().readValue(json, ConsequenceParams.class);
    List<List<String>> optionalFilters = consequenceParams.getOptionalFilters();
    optionalFilters.forEach(filter -> assertThat(filter).isInstanceOf(List.class));
    assertThat(optionalFilters.size()).isEqualTo(4);
  }

  @Test
  void testListOnly() throws JsonProcessingException {
    String json =
        "{\"optionalFilters\":[[\"product_type:Water<score=3>\",\"product_type:SparklingWaters<score=2>\"],[\"category_hierarchy_tier3:Grocery>Beverages>Juices<score=1>\",\"category_hierarchy_tier3:-Fitness&Nutrition>Bars&Drinks>Drinks<score=100>\"]]}";
    ConsequenceParams consequenceParams =
        Defaults.getObjectMapper().readValue(json, ConsequenceParams.class);
    List<List<String>> optionalFilters = consequenceParams.getOptionalFilters();
    optionalFilters.forEach(filter -> assertThat(filter).isInstanceOf(List.class));
    assertThat(optionalFilters.size()).isEqualTo(2);
    assertThat(optionalFilters.get(0).size()).isEqualTo(2);
    assertThat(optionalFilters.get(1).size()).isEqualTo(2);
  }
}
