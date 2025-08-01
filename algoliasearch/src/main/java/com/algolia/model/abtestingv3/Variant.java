// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.abtestingv3;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Variant */
public class Variant {

  @JsonProperty("description")
  private String description;

  @JsonProperty("estimatedSampleSize")
  private Integer estimatedSampleSize;

  @JsonProperty("index")
  private String index;

  @JsonProperty("trafficPercentage")
  private Integer trafficPercentage;

  @JsonProperty("metrics")
  private List<MetricResult> metrics = new ArrayList<>();

  @JsonProperty("metadata")
  private VariantMetadata metadata;

  @JsonProperty("customSearchParameters")
  private Object customSearchParameters;

  public Variant setDescription(String description) {
    this.description = description;
    return this;
  }

  /** Description for this variant. */
  @javax.annotation.Nonnull
  public String getDescription() {
    return description;
  }

  public Variant setEstimatedSampleSize(Integer estimatedSampleSize) {
    this.estimatedSampleSize = estimatedSampleSize;
    return this;
  }

  /**
   * Estimated number of searches required to achieve the desired statistical significance. The A/B
   * test configuration must include a `minimumDetectableEffect` setting for this number to be
   * included in the response.
   */
  @javax.annotation.Nullable
  public Integer getEstimatedSampleSize() {
    return estimatedSampleSize;
  }

  public Variant setIndex(String index) {
    this.index = index;
    return this;
  }

  /** Index name of the A/B test variant (case-sensitive). */
  @javax.annotation.Nonnull
  public String getIndex() {
    return index;
  }

  public Variant setTrafficPercentage(Integer trafficPercentage) {
    this.trafficPercentage = trafficPercentage;
    return this;
  }

  /** Percentage of search requests each variant receives. minimum: 1 maximum: 99 */
  @javax.annotation.Nonnull
  public Integer getTrafficPercentage() {
    return trafficPercentage;
  }

  public Variant setMetrics(List<MetricResult> metrics) {
    this.metrics = metrics;
    return this;
  }

  public Variant addMetrics(MetricResult metricsItem) {
    this.metrics.add(metricsItem);
    return this;
  }

  /** All ABTest metrics that were defined during test creation. */
  @javax.annotation.Nonnull
  public List<MetricResult> getMetrics() {
    return metrics;
  }

  public Variant setMetadata(VariantMetadata metadata) {
    this.metadata = metadata;
    return this;
  }

  /** Get metadata */
  @javax.annotation.Nullable
  public VariantMetadata getMetadata() {
    return metadata;
  }

  public Variant setCustomSearchParameters(Object customSearchParameters) {
    this.customSearchParameters = customSearchParameters;
    return this;
  }

  /**
   * Search parameters applied to this variant when the same index is used for multiple variants.
   * Only present if custom search parameters were provided during test creation.
   */
  @javax.annotation.Nullable
  public Object getCustomSearchParameters() {
    return customSearchParameters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Variant variant = (Variant) o;
    return (
      Objects.equals(this.description, variant.description) &&
      Objects.equals(this.estimatedSampleSize, variant.estimatedSampleSize) &&
      Objects.equals(this.index, variant.index) &&
      Objects.equals(this.trafficPercentage, variant.trafficPercentage) &&
      Objects.equals(this.metrics, variant.metrics) &&
      Objects.equals(this.metadata, variant.metadata) &&
      Objects.equals(this.customSearchParameters, variant.customSearchParameters)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, estimatedSampleSize, index, trafficPercentage, metrics, metadata, customSearchParameters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Variant {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    estimatedSampleSize: ").append(toIndentedString(estimatedSampleSize)).append("\n");
    sb.append("    index: ").append(toIndentedString(index)).append("\n");
    sb.append("    trafficPercentage: ").append(toIndentedString(trafficPercentage)).append("\n");
    sb.append("    metrics: ").append(toIndentedString(metrics)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    customSearchParameters: ").append(toIndentedString(customSearchParameters)).append("\n");
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
