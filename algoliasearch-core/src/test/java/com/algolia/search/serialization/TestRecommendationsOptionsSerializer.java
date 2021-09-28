package com.algolia.search.serialization;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.algolia.search.Defaults;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.recommend.FrequentlyBoughtTogetherQuery;
import com.algolia.search.models.recommend.RecommendationsQuery;
import com.algolia.search.models.recommend.RelatedProductsQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class TestRecommendationsOptionsSerializer {

  @Test
  void testRecommendationsQuery() throws JsonProcessingException {
    RecommendationsQuery query =
        new RecommendationsQuery("products", "bought-together", "B018APC4LE")
            .setThreshold(10)
            .setMaxRecommendations(10)
            .setQueryParameters(
                new Query().setAttributesToRetrieve(Collections.singletonList("*")));
    String json = Defaults.getObjectMapper().writeValueAsString(query);
    assertThat(json)
        .isEqualTo(
            "{\"indexName\":\"products\",\"model\":\"bought-together\",\"objectID\":\"B018APC4LE\",\"threshold\":10,\"maxRecommendations\":10,\"queryParameters\":{\"attributesToRetrieve\":[\"*\"]}}");
  }

  @Test
  void testRecommendationsQueryDefaultThreshold() throws JsonProcessingException {
    RecommendationsQuery query =
        new RecommendationsQuery("products", "bought-together", "B018APC4LE")
            .setMaxRecommendations(10)
            .setQueryParameters(
                new Query().setAttributesToRetrieve(Collections.singletonList("*")));
    String json = Defaults.getObjectMapper().writeValueAsString(query);
    assertThat(json)
        .isEqualTo(
            "{\"indexName\":\"products\",\"model\":\"bought-together\",\"objectID\":\"B018APC4LE\",\"threshold\":0,\"maxRecommendations\":10,\"queryParameters\":{\"attributesToRetrieve\":[\"*\"]}}");
  }

  @Test
  void testRelatedProductsQuery() throws JsonProcessingException {
    RelatedProductsQuery query =
        new RelatedProductsQuery("products", "B018APC4LE")
            .setThreshold(10)
            .setMaxRecommendations(10)
            .setQueryParameters(
                new Query().setAttributesToRetrieve(Collections.singletonList("*")));
    String json = Defaults.getObjectMapper().writeValueAsString(query);
    assertThat(json)
        .isEqualTo(
            "{\"indexName\":\"products\",\"model\":\"related-products\",\"objectID\":\"B018APC4LE\",\"threshold\":10,\"maxRecommendations\":10,\"queryParameters\":{\"attributesToRetrieve\":[\"*\"]}}");
  }

  @Test
  void testFrequentlyBoughtTogether() throws JsonProcessingException {
    FrequentlyBoughtTogetherQuery query =
        new FrequentlyBoughtTogetherQuery("products", "B018APC4LE")
            .setThreshold(10)
            .setMaxRecommendations(10)
            .setQueryParameters(
                new Query().setAttributesToRetrieve(Collections.singletonList("*")));
    String json = Defaults.getObjectMapper().writeValueAsString(query);
    assertThat(json)
        .isEqualTo(
            "{\"indexName\":\"products\",\"model\":\"bought-together\",\"objectID\":\"B018APC4LE\",\"threshold\":10,\"maxRecommendations\":10,\"queryParameters\":{\"attributesToRetrieve\":[\"*\"]}}");
  }
}
