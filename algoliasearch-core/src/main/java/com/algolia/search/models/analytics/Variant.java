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

  // Average click position for the variant.
  private Float averageClickPosition;

  // Distinct click count for the variant.
  private Integer clickCount;

  // Click through rate for the variant.
  private Float clickThroughRate;

  // Click through rate for the variant.
  private Integer conversionCount;

  // Distinct conversion count for the variant.
  private Float conversionRate;

  // No result count
  private Integer noResultCount;

  // Search count
  private Integer searchCount;

  // User Count
  private Integer userCount;

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

  public Float getAverageClickPosition() {
    return averageClickPosition;
  }

  public void setAverageClickPosition(Float averageClickPosition) {
    this.averageClickPosition = averageClickPosition;
  }

  public Integer getClickCount() {
    return clickCount;
  }

  public void setClickCount(Integer clickCount) {
    this.clickCount = clickCount;
  }

  public Float getClickThroughRate() {
    return clickThroughRate;
  }

  public void setClickThroughRate(Float clickThroughRate) {
    this.clickThroughRate = clickThroughRate;
  }

  public Integer getConversionCount() {
    return conversionCount;
  }

  public void setConversionCount(Integer conversionCount) {
    this.conversionCount = conversionCount;
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

  public Integer getSearchCount() {
    return searchCount;
  }

  public void setSearchCount(Integer searchCount) {
    this.searchCount = searchCount;
  }

  public Integer getUserCount() {
    return userCount;
  }

  public void setUserCount(Integer userCount) {
    this.userCount = userCount;
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
