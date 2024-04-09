// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.abtesting;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Variant */
public class Variant {

  @JsonProperty("addToCartCount")
  private Integer addToCartCount;

  @JsonProperty("addToCartRate")
  private Double addToCartRate;

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

  @JsonProperty("currencies")
  private Map<String, Currency> currencies;

  @JsonProperty("description")
  private String description;

  @JsonProperty("estimatedSampleSize")
  private Integer estimatedSampleSize;

  @JsonProperty("filterEffects")
  private FilterEffects filterEffects;

  @JsonProperty("index")
  private String index;

  @JsonProperty("noResultCount")
  private Integer noResultCount;

  @JsonProperty("purchaseCount")
  private Integer purchaseCount;

  @JsonProperty("purchaseRate")
  private Double purchaseRate;

  @JsonProperty("searchCount")
  private Integer searchCount;

  @JsonProperty("trackedSearchCount")
  private Integer trackedSearchCount;

  @JsonProperty("trafficPercentage")
  private Integer trafficPercentage;

  @JsonProperty("userCount")
  private Integer userCount;

  @JsonProperty("trackedUserCount")
  private Integer trackedUserCount;

  public Variant setAddToCartCount(Integer addToCartCount) {
    this.addToCartCount = addToCartCount;
    return this;
  }

  /** Number of add-to-cart events for this variant. */
  @javax.annotation.Nonnull
  public Integer getAddToCartCount() {
    return addToCartCount;
  }

  public Variant setAddToCartRate(Double addToCartRate) {
    this.addToCartRate = addToCartRate;
    return this;
  }

  /**
   * [Add-to-cart
   * rate](https://www.algolia.com/doc/guides/search-analytics/concepts/metrics/#add-to-cart-rate)
   * for this variant.
   */
  @javax.annotation.Nullable
  public Double getAddToCartRate() {
    return addToCartRate;
  }

  public Variant setAverageClickPosition(Integer averageClickPosition) {
    this.averageClickPosition = averageClickPosition;
    return this;
  }

  /**
   * [Average click
   * position](https://www.algolia.com/doc/guides/search-analytics/concepts/metrics/#click-position)
   * for this variant.
   */
  @javax.annotation.Nullable
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
   * [Click-through
   * rate](https://www.algolia.com/doc/guides/search-analytics/concepts/metrics/#click-through-rate)
   * for this variant.
   */
  @javax.annotation.Nullable
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
   * [Conversion
   * rate](https://www.algolia.com/doc/guides/search-analytics/concepts/metrics/#conversion-rate)
   * for this variant.
   */
  @javax.annotation.Nullable
  public Double getConversionRate() {
    return conversionRate;
  }

  public Variant setCurrencies(Map<String, Currency> currencies) {
    this.currencies = currencies;
    return this;
  }

  public Variant putCurrencies(String key, Currency currenciesItem) {
    if (this.currencies == null) {
      this.currencies = new HashMap<>();
    }
    this.currencies.put(key, currenciesItem);
    return this;
  }

  /** A/B test currencies. */
  @javax.annotation.Nullable
  public Map<String, Currency> getCurrencies() {
    return currencies;
  }

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
   * test configuration must include a `mininmumDetectableEffect` setting for this number to be
   * included in the response.
   */
  @javax.annotation.Nullable
  public Integer getEstimatedSampleSize() {
    return estimatedSampleSize;
  }

  public Variant setFilterEffects(FilterEffects filterEffects) {
    this.filterEffects = filterEffects;
    return this;
  }

  /** Get filterEffects */
  @javax.annotation.Nullable
  public FilterEffects getFilterEffects() {
    return filterEffects;
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
   * for this variant.
   */
  @javax.annotation.Nullable
  public Integer getNoResultCount() {
    return noResultCount;
  }

  public Variant setPurchaseCount(Integer purchaseCount) {
    this.purchaseCount = purchaseCount;
    return this;
  }

  /** Number of purchase events for this variant. */
  @javax.annotation.Nonnull
  public Integer getPurchaseCount() {
    return purchaseCount;
  }

  public Variant setPurchaseRate(Double purchaseRate) {
    this.purchaseRate = purchaseRate;
    return this;
  }

  /**
   * [Purchase
   * rate](https://www.algolia.com/doc/guides/search-analytics/concepts/metrics/#purchase-rate) for
   * this variant.
   */
  @javax.annotation.Nullable
  public Double getPurchaseRate() {
    return purchaseRate;
  }

  public Variant setSearchCount(Integer searchCount) {
    this.searchCount = searchCount;
    return this;
  }

  /** Number of searches for this variant. */
  @javax.annotation.Nullable
  public Integer getSearchCount() {
    return searchCount;
  }

  public Variant setTrackedSearchCount(Integer trackedSearchCount) {
    this.trackedSearchCount = trackedSearchCount;
    return this;
  }

  /**
   * Number of tracked searches. Tracked searches are search requests where the `clickAnalytics`
   * parameter is true.
   */
  @javax.annotation.Nullable
  public Integer getTrackedSearchCount() {
    return trackedSearchCount;
  }

  public Variant setTrafficPercentage(Integer trafficPercentage) {
    this.trafficPercentage = trafficPercentage;
    return this;
  }

  /** Percentage of search requests each variant receives. minimum: 0 maximum: 100 */
  @javax.annotation.Nonnull
  public Integer getTrafficPercentage() {
    return trafficPercentage;
  }

  public Variant setUserCount(Integer userCount) {
    this.userCount = userCount;
    return this;
  }

  /** Number of users that made searches to this variant. */
  @javax.annotation.Nullable
  public Integer getUserCount() {
    return userCount;
  }

  public Variant setTrackedUserCount(Integer trackedUserCount) {
    this.trackedUserCount = trackedUserCount;
    return this;
  }

  /** Number of users that made tracked searches to this variant. */
  @javax.annotation.Nullable
  public Integer getTrackedUserCount() {
    return trackedUserCount;
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
      Objects.equals(this.addToCartCount, variant.addToCartCount) &&
      Objects.equals(this.addToCartRate, variant.addToCartRate) &&
      Objects.equals(this.averageClickPosition, variant.averageClickPosition) &&
      Objects.equals(this.clickCount, variant.clickCount) &&
      Objects.equals(this.clickThroughRate, variant.clickThroughRate) &&
      Objects.equals(this.conversionCount, variant.conversionCount) &&
      Objects.equals(this.conversionRate, variant.conversionRate) &&
      Objects.equals(this.currencies, variant.currencies) &&
      Objects.equals(this.description, variant.description) &&
      Objects.equals(this.estimatedSampleSize, variant.estimatedSampleSize) &&
      Objects.equals(this.filterEffects, variant.filterEffects) &&
      Objects.equals(this.index, variant.index) &&
      Objects.equals(this.noResultCount, variant.noResultCount) &&
      Objects.equals(this.purchaseCount, variant.purchaseCount) &&
      Objects.equals(this.purchaseRate, variant.purchaseRate) &&
      Objects.equals(this.searchCount, variant.searchCount) &&
      Objects.equals(this.trackedSearchCount, variant.trackedSearchCount) &&
      Objects.equals(this.trafficPercentage, variant.trafficPercentage) &&
      Objects.equals(this.userCount, variant.userCount) &&
      Objects.equals(this.trackedUserCount, variant.trackedUserCount)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      addToCartCount,
      addToCartRate,
      averageClickPosition,
      clickCount,
      clickThroughRate,
      conversionCount,
      conversionRate,
      currencies,
      description,
      estimatedSampleSize,
      filterEffects,
      index,
      noResultCount,
      purchaseCount,
      purchaseRate,
      searchCount,
      trackedSearchCount,
      trafficPercentage,
      userCount,
      trackedUserCount
    );
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Variant {\n");
    sb.append("    addToCartCount: ").append(toIndentedString(addToCartCount)).append("\n");
    sb.append("    addToCartRate: ").append(toIndentedString(addToCartRate)).append("\n");
    sb.append("    averageClickPosition: ").append(toIndentedString(averageClickPosition)).append("\n");
    sb.append("    clickCount: ").append(toIndentedString(clickCount)).append("\n");
    sb.append("    clickThroughRate: ").append(toIndentedString(clickThroughRate)).append("\n");
    sb.append("    conversionCount: ").append(toIndentedString(conversionCount)).append("\n");
    sb.append("    conversionRate: ").append(toIndentedString(conversionRate)).append("\n");
    sb.append("    currencies: ").append(toIndentedString(currencies)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    estimatedSampleSize: ").append(toIndentedString(estimatedSampleSize)).append("\n");
    sb.append("    filterEffects: ").append(toIndentedString(filterEffects)).append("\n");
    sb.append("    index: ").append(toIndentedString(index)).append("\n");
    sb.append("    noResultCount: ").append(toIndentedString(noResultCount)).append("\n");
    sb.append("    purchaseCount: ").append(toIndentedString(purchaseCount)).append("\n");
    sb.append("    purchaseRate: ").append(toIndentedString(purchaseRate)).append("\n");
    sb.append("    searchCount: ").append(toIndentedString(searchCount)).append("\n");
    sb.append("    trackedSearchCount: ").append(toIndentedString(trackedSearchCount)).append("\n");
    sb.append("    trafficPercentage: ").append(toIndentedString(trafficPercentage)).append("\n");
    sb.append("    userCount: ").append(toIndentedString(userCount)).append("\n");
    sb.append("    trackedUserCount: ").append(toIndentedString(trackedUserCount)).append("\n");
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
