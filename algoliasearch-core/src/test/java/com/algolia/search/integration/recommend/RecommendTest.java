package com.algolia.search.integration.recommend;

import com.algolia.search.RecommendClient;
import com.algolia.search.integration.models.RecommendObject;
import com.algolia.search.models.indexing.DefaultRecommendHit;
import com.algolia.search.models.indexing.RecommendationsResult;
import com.algolia.search.models.recommend.FrequentlyBoughtTogetherQuery;
import com.algolia.search.models.recommend.RecommendationsQuery;
import com.algolia.search.models.recommend.RelatedProductsQuery;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class RecommendTest {

  private final RecommendClient recommendClient;

  public RecommendTest(RecommendClient recommendClient) {
    this.recommendClient = recommendClient;
  }

  @Test
  void testGetRecommends() {
    RecommendationsQuery queryRelatedProducts =
        new RecommendationsQuery("MyIndexName", "related-products", "MyObjectID");
    RecommendationsQuery queryBoughtTogether =
        new RecommendationsQuery("MyIndexName", "bought-together", "MyObjectID");

    List<RecommendationsResult<DefaultRecommendHit>> recommendationsDefault =
        recommendClient.getRecommendations(
            Arrays.asList(queryRelatedProducts, queryBoughtTogether));
    assertThat(recommendationsDefault.size()).isEqualTo(2);

    List<RecommendationsResult<RecommendObject>> recommendations =
        recommendClient.getRecommendations(
            Arrays.asList(queryRelatedProducts, queryBoughtTogether), RecommendObject.class);
    assertThat(recommendations.size()).isEqualTo(2);
  }

  @Test
  void testRelatedProducts() {
    RelatedProductsQuery queryRelatedProducts =
        new RelatedProductsQuery("MyIndexName", "MyObjectID");

    List<RecommendationsResult<DefaultRecommendHit>> recommendationsDefault =
        recommendClient.getRelatedProducts(Collections.singletonList(queryRelatedProducts));
    assertThat(recommendationsDefault.size()).isEqualTo(2);

    List<RecommendationsResult<RecommendObject>> recommendations =
        recommendClient.getRelatedProducts(
            Collections.singletonList(queryRelatedProducts), RecommendObject.class);
    assertThat(recommendations.size()).isEqualTo(2);
  }

  @Test
  void testFrequentlyBoughtTogether() {
    FrequentlyBoughtTogetherQuery queryBoughtTogether =
        new FrequentlyBoughtTogetherQuery("MyIndexName", "MyObjectID");

    List<RecommendationsResult<DefaultRecommendHit>> recommendationsDefault =
        recommendClient.getFrequentlyBoughtTogether(Collections.singletonList(queryBoughtTogether));
    assertThat(recommendationsDefault.size()).isEqualTo(2);

    List<RecommendationsResult<RecommendObject>> recommendations =
        recommendClient.getFrequentlyBoughtTogether(
            Collections.singletonList(queryBoughtTogether), RecommendObject.class);
    assertThat(recommendations.size()).isEqualTo(2);
  }
}
