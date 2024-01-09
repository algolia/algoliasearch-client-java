// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.recommend;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.Objects;

/** RecommendationsQuery */
@JsonDeserialize(as = RecommendationsQuery.class)
public class RecommendationsQuery implements RecommendationsRequest {

  @JsonProperty("indexName")
  private String indexName;

  @JsonProperty("threshold")
  private Integer threshold;

  @JsonProperty("maxRecommendations")
  private Integer maxRecommendations;

  @JsonProperty("model")
  private RecommendationModels model;

  @JsonProperty("objectID")
  private String objectID;

  @JsonProperty("queryParameters")
  private SearchParamsObject queryParameters;

  @JsonProperty("fallbackParameters")
  private SearchParamsObject fallbackParameters;

  public RecommendationsQuery setIndexName(String indexName) {
    this.indexName = indexName;
    return this;
  }

  /** Algolia index name. */
  @javax.annotation.Nonnull
  public String getIndexName() {
    return indexName;
  }

  public RecommendationsQuery setThreshold(Integer threshold) {
    this.threshold = threshold;
    return this;
  }

  /**
   * Recommendations with a confidence score lower than `threshold` won't appear in results. >
   * **Note**: Each recommendation has a confidence score of 0 to 100. The closer the score is to
   * 100, the more relevant the recommendations are. minimum: 0 maximum: 100
   */
  @javax.annotation.Nullable
  public Integer getThreshold() {
    return threshold;
  }

  public RecommendationsQuery setMaxRecommendations(Integer maxRecommendations) {
    this.maxRecommendations = maxRecommendations;
    return this;
  }

  /** Maximum number of recommendations to retrieve. If 0, all recommendations will be returned. */
  @javax.annotation.Nullable
  public Integer getMaxRecommendations() {
    return maxRecommendations;
  }

  public RecommendationsQuery setModel(RecommendationModels model) {
    this.model = model;
    return this;
  }

  /** Get model */
  @javax.annotation.Nonnull
  public RecommendationModels getModel() {
    return model;
  }

  public RecommendationsQuery setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  /** Unique object identifier. */
  @javax.annotation.Nonnull
  public String getObjectID() {
    return objectID;
  }

  public RecommendationsQuery setQueryParameters(SearchParamsObject queryParameters) {
    this.queryParameters = queryParameters;
    return this;
  }

  /** Get queryParameters */
  @javax.annotation.Nullable
  public SearchParamsObject getQueryParameters() {
    return queryParameters;
  }

  public RecommendationsQuery setFallbackParameters(SearchParamsObject fallbackParameters) {
    this.fallbackParameters = fallbackParameters;
    return this;
  }

  /** Get fallbackParameters */
  @javax.annotation.Nullable
  public SearchParamsObject getFallbackParameters() {
    return fallbackParameters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecommendationsQuery recommendationsQuery = (RecommendationsQuery) o;
    return (
      Objects.equals(this.indexName, recommendationsQuery.indexName) &&
      Objects.equals(this.threshold, recommendationsQuery.threshold) &&
      Objects.equals(this.maxRecommendations, recommendationsQuery.maxRecommendations) &&
      Objects.equals(this.model, recommendationsQuery.model) &&
      Objects.equals(this.objectID, recommendationsQuery.objectID) &&
      Objects.equals(this.queryParameters, recommendationsQuery.queryParameters) &&
      Objects.equals(this.fallbackParameters, recommendationsQuery.fallbackParameters)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(indexName, threshold, maxRecommendations, model, objectID, queryParameters, fallbackParameters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecommendationsQuery {\n");
    sb.append("    indexName: ").append(toIndentedString(indexName)).append("\n");
    sb.append("    threshold: ").append(toIndentedString(threshold)).append("\n");
    sb.append("    maxRecommendations: ").append(toIndentedString(maxRecommendations)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    objectID: ").append(toIndentedString(objectID)).append("\n");
    sb.append("    queryParameters: ").append(toIndentedString(queryParameters)).append("\n");
    sb.append("    fallbackParameters: ").append(toIndentedString(fallbackParameters)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}