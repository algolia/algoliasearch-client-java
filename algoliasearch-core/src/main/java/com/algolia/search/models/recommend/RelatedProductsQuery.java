package com.algolia.search.models.recommend;

import com.algolia.search.models.indexing.Query;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Objects;
import javax.annotation.Nonnull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelatedProductsQuery extends RecommendationsOptions {

  public RelatedProductsQuery(@Nonnull String indexName, @Nonnull String objectID) {
    Objects.requireNonNull(indexName, "an index is required");
    Objects.requireNonNull(objectID, "an objectID is required");
    this.indexName = indexName;
    this.objectID = objectID;
    this.model = "related-products";
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

  public RelatedProductsQuery setThreshold(@Nonnull Integer threshold) {
    this.threshold = threshold;
    return this;
  }

  public Integer getMaxRecommendations() {
    return maxRecommendations;
  }

  public RelatedProductsQuery setMaxRecommendations(Integer maxRecommendations) {
    this.maxRecommendations = maxRecommendations;
    return this;
  }

  public Query getQueryParameters() {
    return queryParameters;
  }

  public RelatedProductsQuery setQueryParameters(@Nonnull Query queryParameters) {
    this.queryParameters = queryParameters;
    return this;
  }

  public Query getFallbackParameters() {
    return fallbackParameters;
  }

  public RelatedProductsQuery setFallbackParameters(@Nonnull Query fallbackParameters) {
    this.fallbackParameters = fallbackParameters;
    return this;
  }
}
