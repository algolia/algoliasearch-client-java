// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.abtesting;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** AddABTestsRequest */
public class AddABTestsRequest {

  @JsonProperty("name")
  private String name;

  @JsonProperty("variants")
  private List<AddABTestsVariant> variants = new ArrayList<>();

  @JsonProperty("endAt")
  private String endAt;

  public AddABTestsRequest setName(String name) {
    this.name = name;
    return this;
  }

  /** A/B test name. */
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }

  public AddABTestsRequest setVariants(List<AddABTestsVariant> variants) {
    this.variants = variants;
    return this;
  }

  public AddABTestsRequest addVariants(AddABTestsVariant variantsItem) {
    this.variants.add(variantsItem);
    return this;
  }

  /** A/B test variants. */
  @javax.annotation.Nonnull
  public List<AddABTestsVariant> getVariants() {
    return variants;
  }

  public AddABTestsRequest setEndAt(String endAt) {
    this.endAt = endAt;
    return this;
  }

  /** End date and time of the A/B test, in RFC 3339 format. */
  @javax.annotation.Nonnull
  public String getEndAt() {
    return endAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddABTestsRequest addABTestsRequest = (AddABTestsRequest) o;
    return (
      Objects.equals(this.name, addABTestsRequest.name) &&
      Objects.equals(this.variants, addABTestsRequest.variants) &&
      Objects.equals(this.endAt, addABTestsRequest.endAt)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, variants, endAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddABTestsRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    variants: ").append(toIndentedString(variants)).append("\n");
    sb.append("    endAt: ").append(toIndentedString(endAt)).append("\n");
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
