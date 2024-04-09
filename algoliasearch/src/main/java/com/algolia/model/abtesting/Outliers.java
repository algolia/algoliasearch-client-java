// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.abtesting;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.Objects;

/** Configuration for handling outliers. */
public class Outliers {

  @JsonProperty("exclude")
  private Boolean exclude;

  public Outliers setExclude(Boolean exclude) {
    this.exclude = exclude;
    return this;
  }

  /** Whether to exclude outliers when calculating A/B test results. */
  @javax.annotation.Nullable
  public Boolean getExclude() {
    return exclude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Outliers outliers = (Outliers) o;
    return Objects.equals(this.exclude, outliers.exclude);
  }

  @Override
  public int hashCode() {
    return Objects.hash(exclude);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Outliers {\n");
    sb.append("    exclude: ").append(toIndentedString(exclude)).append("\n");
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
