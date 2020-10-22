package com.algolia.search.models.analytics;

import com.algolia.search.models.indexing.Query;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.annotation.Nonnull;

/**
 * Variant
 *
 * <p>* @see <a
 * href="https://www.algolia.com/doc/api-reference/api-methods/add-ab-test">Algolia.com</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Variant implements Serializable {

  // Properties available at construction time
  private String index;

  // Percentage of the traffic that should be going to the variant. The sum of the percentage should
  // be equal to 100.
  private int trafficPercentage;

  // Description of the variant. This is useful when seing the results in the dashboard or via the
  // API.
  private String description;

  // Distinct conversion count for the variant.
  private Float conversionRate;

  // No result count
  private Integer noResultCount;

  // Average click position for the variant.
  private Float averageClickPosition;

  // Search count
  private Long searchCount;

  // Tracked search count
  private Long trackedSearchCount;

  // User Count
  private Long userCount;

  // Click through rate for the variant.
  private Float clickThroughRate;

  // Properties set by the AB Testing API
  private Query customSearchParameters;

  public Variant() {}

  public Variant(@Nonnull String index, int trafficPercentage, String description) {
    this.index = index;
    this.trafficPercentage = trafficPercentage;
    this.description = (description != null) ? description : "";
  }

  public Variant(
      String index, int trafficPercentage, String description, Query customSearchParameters) {
    this.index = index;
    this.trafficPercentage = trafficPercentage;
    this.description = description;
    this.customSearchParameters = customSearchParameters;
  }

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public int getTrafficPercentage() {
    return trafficPercentage;
  }

  public void setTrafficPercentage(int trafficPercentage) {
    this.trafficPercentage = trafficPercentage;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Float getConversionRate() {
    return conversionRate;
  }

  public void setConversionRate(Float conversionRate) {
    this.conversionRate = conversionRate;
  }

  public Integer getNoResultCount() {
    return noResultCount;
  }

  public void setNoResultCount(Integer noResultCount) {
    this.noResultCount = noResultCount;
  }

  public Float getAverageClickPosition() {
    return averageClickPosition;
  }

  public void setAverageClickPosition(Float averageClickPosition) {
    this.averageClickPosition = averageClickPosition;
  }

  public Long getSearchCount() {
    return searchCount;
  }

  public void setSearchCount(Long searchCount) {
    this.searchCount = searchCount;
  }

  public Long getTrackedSearchCount() {
    return trackedSearchCount;
  }

  public void setTrackedSearchCount(Long trackedSearchCount) {
    this.trackedSearchCount = trackedSearchCount;
  }

  public Long getUserCount() {
    return userCount;
  }

  public void setUserCount(Long userCount) {
    this.userCount = userCount;
  }

  public Float getClickThroughRate() {
    return clickThroughRate;
  }

  public void setClickThroughRate(Float clickThroughRate) {
    this.clickThroughRate = clickThroughRate;
  }

  @JsonProperty
  public Query getCustomSearchParameters() {
    return customSearchParameters;
  }

  @JsonProperty
  public void setCustomSearchParameters(Query customSearchParameters) {
    this.customSearchParameters = customSearchParameters;
  }
}
