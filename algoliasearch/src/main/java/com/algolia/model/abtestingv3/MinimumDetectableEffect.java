// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.abtestingv3;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.Objects;

/** Configuration for the smallest difference between test variants you want to detect. */
public class MinimumDetectableEffect {

  @JsonProperty("size")
  private Double size;

  @JsonProperty("metric")
  private EffectMetric metric;

  public MinimumDetectableEffect setSize(Double size) {
    this.size = size;
    return this;
  }

  /**
   * Smallest difference in an observable metric between variants. For example, to detect a 10%
   * difference between variants, set this value to 0.1. minimum: 0 maximum: 1
   */
  @javax.annotation.Nonnull
  public Double getSize() {
    return size;
  }

  public MinimumDetectableEffect setMetric(EffectMetric metric) {
    this.metric = metric;
    return this;
  }

  /** Get metric */
  @javax.annotation.Nonnull
  public EffectMetric getMetric() {
    return metric;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MinimumDetectableEffect minimumDetectableEffect = (MinimumDetectableEffect) o;
    return Objects.equals(this.size, minimumDetectableEffect.size) && Objects.equals(this.metric, minimumDetectableEffect.metric);
  }

  @Override
  public int hashCode() {
    return Objects.hash(size, metric);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MinimumDetectableEffect {\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    metric: ").append(toIndentedString(metric)).append("\n");
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
