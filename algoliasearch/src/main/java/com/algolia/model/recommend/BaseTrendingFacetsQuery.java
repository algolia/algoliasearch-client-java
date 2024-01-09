// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.recommend;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.Objects;

/** BaseTrendingFacetsQuery */
public class BaseTrendingFacetsQuery {

  @JsonProperty("facetName")
  private String facetName;

  @JsonProperty("model")
  private TrendingFacetsModel model;

  public BaseTrendingFacetsQuery setFacetName(String facetName) {
    this.facetName = facetName;
    return this;
  }

  /** Facet name for trending models. */
  @javax.annotation.Nonnull
  public String getFacetName() {
    return facetName;
  }

  public BaseTrendingFacetsQuery setModel(TrendingFacetsModel model) {
    this.model = model;
    return this;
  }

  /** Get model */
  @javax.annotation.Nullable
  public TrendingFacetsModel getModel() {
    return model;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseTrendingFacetsQuery baseTrendingFacetsQuery = (BaseTrendingFacetsQuery) o;
    return Objects.equals(this.facetName, baseTrendingFacetsQuery.facetName) && Objects.equals(this.model, baseTrendingFacetsQuery.model);
  }

  @Override
  public int hashCode() {
    return Objects.hash(facetName, model);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BaseTrendingFacetsQuery {\n");
    sb.append("    facetName: ").append(toIndentedString(facetName)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
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