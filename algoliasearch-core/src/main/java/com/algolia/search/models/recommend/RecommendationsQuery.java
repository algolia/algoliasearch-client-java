package com.algolia.search.models.recommend;

import com.algolia.search.models.indexing.Query;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Objects;
import javax.annotation.Nonnull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecommendationsQuery extends RecommendationsOptions {

  /**
   * Recommendation query options.
   *
   * @param indexName name of the index to target.
   * @param model the recommendation model to use, on of: "related-products", or "bought-together"
   * @param objectID the objectID to get recommendations for
   */
  public RecommendationsQuery(
      @Nonnull String indexName, @Nonnull String model, @Nonnull String objectID) {
    Objects.requireNonNull(indexName, "an index is required");
    Objects.requireNonNull(model, "a model is required");
    Objects.requireNonNull(objectID, "an objectID is required");
    this.indexName = indexName;
    this.model = model;
    this.objectID = objectID;
    this.threshold = 0;
  }

  public String getIndexName() {
    return indexName;
  }

  public String getModel() {
    return model;
  }

  public String getObjectID() {
    return objectID;
  }

  public RecommendationsOptions setObjectID(@Nonnull String objectID) {
    this.objectID = objectID;
    return this;
  }

  public Integer getThreshold() {
    return threshold;
  }

  public RecommendationsQuery setThreshold(@Nonnull Integer threshold) {
    this.threshold = threshold;
    return this;
  }

  public Integer getMaxRecommendations() {
    return maxRecommendations;
  }

  public RecommendationsQuery setMaxRecommendations(Integer maxRecommendations) {
    this.maxRecommendations = maxRecommendations;
    return this;
  }

  public Query getQueryParameters() {
    return queryParameters;
  }

  public RecommendationsQuery setQueryParameters(@Nonnull Query queryParameters) {
    this.queryParameters = queryParameters;
    return this;
  }

  public Query getFallbackParameters() {
    return fallbackParameters;
  }

  public RecommendationsQuery setFallbackParameters(@Nonnull Query fallbackParameters) {
    this.fallbackParameters = fallbackParameters;
    return this;
  }
}
