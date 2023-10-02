// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.abtesting;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.Objects;

/** Variant */
public class Variant {

  @JsonProperty("averageClickPosition")
  private Integer averageClickPosition;

  @JsonProperty("clickCount")
  private Integer clickCount;

  @JsonProperty("clickThroughRate")
  private Double clickThroughRate;

  @JsonProperty("conversionCount")
  private Integer conversionCount;

  @JsonProperty("conversionRate")
  private Double conversionRate;

  @JsonProperty("description")
  private String description;

  @JsonProperty("index")
  private String index;

  @JsonProperty("noResultCount")
  private Integer noResultCount;

  @JsonProperty("outlierTrackedSearchesCount")
  private Integer outlierTrackedSearchesCount;

  @JsonProperty("outlierUsersCount")
  private Integer outlierUsersCount;

  @JsonProperty("searchCount")
  private Integer searchCount;

  @JsonProperty("trackedSearchCount")
  private Integer trackedSearchCount;

  @JsonProperty("trafficPercentage")
  private Integer trafficPercentage;

  @JsonProperty("userCount")
  private Integer userCount;

  public Variant setAverageClickPosition(Integer averageClickPosition) {
    this.averageClickPosition = averageClickPosition;
    return this;
  }

  /**
   * Variant's [average click
   * position](https://www.algolia.com/doc/guides/search-analytics/concepts/metrics/#click-position).
   */
  @javax.annotation.Nonnull
  public Integer getAverageClickPosition() {
    return averageClickPosition;
  }

  public Variant setClickCount(Integer clickCount) {
    this.clickCount = clickCount;
    return this;
  }

  /** Number of click events for this variant. */
  @javax.annotation.Nonnull
  public Integer getClickCount() {
    return clickCount;
  }

  public Variant setClickThroughRate(Double clickThroughRate) {
    this.clickThroughRate = clickThroughRate;
    return this;
  }

  /**
   * Variant's [click-through
   * rate](https://www.algolia.com/doc/guides/search-analytics/concepts/metrics/#click-through-rate).
   */
  @javax.annotation.Nonnull
  public Double getClickThroughRate() {
    return clickThroughRate;
  }

  public Variant setConversionCount(Integer conversionCount) {
    this.conversionCount = conversionCount;
    return this;
  }

  /** Number of click events for this variant. */
  @javax.annotation.Nonnull
  public Integer getConversionCount() {
    return conversionCount;
  }

  public Variant setConversionRate(Double conversionRate) {
    this.conversionRate = conversionRate;
    return this;
  }

  /**
   * Variant's [conversion
   * rate](https://www.algolia.com/doc/guides/search-analytics/concepts/metrics/#conversion-rate).
   */
  @javax.annotation.Nonnull
  public Double getConversionRate() {
    return conversionRate;
  }

  public Variant setDescription(String description) {
    this.description = description;
    return this;
  }

  /** A/B test description. */
  @javax.annotation.Nonnull
  public String getDescription() {
    return description;
  }

  public Variant setIndex(String index) {
    this.index = index;
    return this;
  }

  /** A/B test index. */
  @javax.annotation.Nonnull
  public String getIndex() {
    return index;
  }

  public Variant setNoResultCount(Integer noResultCount) {
    this.noResultCount = noResultCount;
    return this;
  }

  /**
   * Number of [searches without
   * results](https://www.algolia.com/doc/guides/search-analytics/concepts/metrics/#searches-without-results)
   * for that variant.
   */
  @javax.annotation.Nonnull
  public Integer getNoResultCount() {
    return noResultCount;
  }

  public Variant setOutlierTrackedSearchesCount(Integer outlierTrackedSearchesCount) {
    this.outlierTrackedSearchesCount = outlierTrackedSearchesCount;
    return this;
  }

  /**
   * Number of tracked searches attributed to [outlier
   * traffic](https://www.algolia.com/doc/guides/ab-testing/how-to-read-your-a-b-test-results/#is-the-split-off)
   * that were removed from the A/B test. A _tracked_ search is a search request where the
   * `clickAnalytics` parameter is `true`.
   */
  @javax.annotation.Nonnull
  public Integer getOutlierTrackedSearchesCount() {
    return outlierTrackedSearchesCount;
  }

  public Variant setOutlierUsersCount(Integer outlierUsersCount) {
    this.outlierUsersCount = outlierUsersCount;
    return this;
  }

  /**
   * Number of users attributed to [outlier
   * traffic](https://www.algolia.com/doc/guides/ab-testing/how-to-read-your-a-b-test-results/#is-the-split-off)
   * that were removed from the A/B test.
   */
  @javax.annotation.Nonnull
  public Integer getOutlierUsersCount() {
    return outlierUsersCount;
  }

  public Variant setSearchCount(Integer searchCount) {
    this.searchCount = searchCount;
    return this;
  }

  /** Number of searches carried out during the A/B test. */
  @javax.annotation.Nonnull
  public Integer getSearchCount() {
    return searchCount;
  }

  public Variant setTrackedSearchCount(Integer trackedSearchCount) {
    this.trackedSearchCount = trackedSearchCount;
    return this;
  }

  /**
   * Number of tracked searches. This is the number of search requests where the `clickAnalytics`
   * parameter is `true`.
   */
  @javax.annotation.Nonnull
  public Integer getTrackedSearchCount() {
    return trackedSearchCount;
  }

  public Variant setTrafficPercentage(Integer trafficPercentage) {
    this.trafficPercentage = trafficPercentage;
    return this;
  }

  /** A/B test traffic percentage. */
  @javax.annotation.Nonnull
  public Integer getTrafficPercentage() {
    return trafficPercentage;
  }

  public Variant setUserCount(Integer userCount) {
    this.userCount = userCount;
    return this;
  }

  /** Number of users during the A/B test. */
  @javax.annotation.Nonnull
  public Integer getUserCount() {
    return userCount;
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
      Objects.equals(this.averageClickPosition, variant.averageClickPosition) &&
      Objects.equals(this.clickCount, variant.clickCount) &&
      Objects.equals(this.clickThroughRate, variant.clickThroughRate) &&
      Objects.equals(this.conversionCount, variant.conversionCount) &&
      Objects.equals(this.conversionRate, variant.conversionRate) &&
      Objects.equals(this.description, variant.description) &&
      Objects.equals(this.index, variant.index) &&
      Objects.equals(this.noResultCount, variant.noResultCount) &&
      Objects.equals(this.outlierTrackedSearchesCount, variant.outlierTrackedSearchesCount) &&
      Objects.equals(this.outlierUsersCount, variant.outlierUsersCount) &&
      Objects.equals(this.searchCount, variant.searchCount) &&
      Objects.equals(this.trackedSearchCount, variant.trackedSearchCount) &&
      Objects.equals(this.trafficPercentage, variant.trafficPercentage) &&
      Objects.equals(this.userCount, variant.userCount)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      averageClickPosition,
      clickCount,
      clickThroughRate,
      conversionCount,
      conversionRate,
      description,
      index,
      noResultCount,
      outlierTrackedSearchesCount,
      outlierUsersCount,
      searchCount,
      trackedSearchCount,
      trafficPercentage,
      userCount
    );
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Variant {\n");
    sb.append("    averageClickPosition: ").append(toIndentedString(averageClickPosition)).append("\n");
    sb.append("    clickCount: ").append(toIndentedString(clickCount)).append("\n");
    sb.append("    clickThroughRate: ").append(toIndentedString(clickThroughRate)).append("\n");
    sb.append("    conversionCount: ").append(toIndentedString(conversionCount)).append("\n");
    sb.append("    conversionRate: ").append(toIndentedString(conversionRate)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    index: ").append(toIndentedString(index)).append("\n");
    sb.append("    noResultCount: ").append(toIndentedString(noResultCount)).append("\n");
    sb.append("    outlierTrackedSearchesCount: ").append(toIndentedString(outlierTrackedSearchesCount)).append("\n");
    sb.append("    outlierUsersCount: ").append(toIndentedString(outlierUsersCount)).append("\n");
    sb.append("    searchCount: ").append(toIndentedString(searchCount)).append("\n");
    sb.append("    trackedSearchCount: ").append(toIndentedString(trackedSearchCount)).append("\n");
    sb.append("    trafficPercentage: ").append(toIndentedString(trafficPercentage)).append("\n");
    sb.append("    userCount: ").append(toIndentedString(userCount)).append("\n");
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