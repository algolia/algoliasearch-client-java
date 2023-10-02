// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.recommend;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.Objects;

/** BaseRecommendationsQuery */
public class BaseRecommendationsQuery {

  @JsonProperty("model")
  private RecommendationModels model;

  @JsonProperty("objectID")
  private String objectID;

  @JsonProperty("queryParameters")
  private SearchParamsObject queryParameters;

  @JsonProperty("fallbackParameters")
  private SearchParamsObject fallbackParameters;

  public BaseRecommendationsQuery setModel(RecommendationModels model) {
    this.model = model;
    return this;
  }

  /** Get model */
  @javax.annotation.Nonnull
  public RecommendationModels getModel() {
    return model;
  }

  public BaseRecommendationsQuery setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  /** Unique object identifier. */
  @javax.annotation.Nonnull
  public String getObjectID() {
    return objectID;
  }

  public BaseRecommendationsQuery setQueryParameters(SearchParamsObject queryParameters) {
    this.queryParameters = queryParameters;
    return this;
  }

  /** Get queryParameters */
  @javax.annotation.Nullable
  public SearchParamsObject getQueryParameters() {
    return queryParameters;
  }

  public BaseRecommendationsQuery setFallbackParameters(SearchParamsObject fallbackParameters) {
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
    BaseRecommendationsQuery baseRecommendationsQuery = (BaseRecommendationsQuery) o;
    return (
      Objects.equals(this.model, baseRecommendationsQuery.model) &&
      Objects.equals(this.objectID, baseRecommendationsQuery.objectID) &&
      Objects.equals(this.queryParameters, baseRecommendationsQuery.queryParameters) &&
      Objects.equals(this.fallbackParameters, baseRecommendationsQuery.fallbackParameters)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(model, objectID, queryParameters, fallbackParameters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BaseRecommendationsQuery {\n");
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