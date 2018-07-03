package com.algolia.search.inputs.analytics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.annotation.Nonnull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Variant implements Serializable {

  // Properties available at construction time
  private String index;
  private int trafficPercentage;
  private String description;

  // Properties set by the AB Testing API
  private int averageClickPosition;
  private int clickCount;
  private float clickThroughRate;
  private int conversionCount;
  private float conversionRate;
  private int noResultCount;
  private int searchCount;
  private int userCount;

  public Variant() {}

  public Variant(@Nonnull String index, @Nonnull int trafficPercentage, String description) {
    this.index = index;
    this.trafficPercentage = trafficPercentage;
    this.description = (description != null) ? description : "";
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

  @JsonIgnore
  public int getAverageClickPosition() {
    return averageClickPosition;
  }

  @JsonProperty
  public void setAverageClickPosition(int averageClickPosition) {
    this.averageClickPosition = averageClickPosition;
  }

  @JsonIgnore
  public int getClickCount() {
    return clickCount;
  }

  @JsonProperty
  public void setClickCount(int clickCount) {
    this.clickCount = clickCount;
  }

  @JsonIgnore
  public float getClickThroughRate() {
    return clickThroughRate;
  }

  @JsonProperty
  public void setClickThroughRate(float clickThroughRate) {
    this.clickThroughRate = clickThroughRate;
  }

  @JsonIgnore
  public int getConversionCount() {
    return conversionCount;
  }

  @JsonProperty
  public void setConversionCount(int conversionCount) {
    this.conversionCount = conversionCount;
  }

  @JsonIgnore
  public float getConversionRate() {
    return conversionRate;
  }

  @JsonProperty
  public void setConversionRate(float conversionRate) {
    this.conversionRate = conversionRate;
  }

  @JsonIgnore
  public int getNoResultCount() {
    return noResultCount;
  }

  @JsonProperty
  public void setNoResultCount(int noResultCount) {
    this.noResultCount = noResultCount;
  }

  @JsonIgnore
  public int getSearchCount() {
    return searchCount;
  }

  @JsonProperty
  public void setSearchCount(int searchCount) {
    this.searchCount = searchCount;
  }

  @JsonIgnore
  public int getUserCount() {
    return userCount;
  }

  @JsonProperty
  public void setUserCount(int userCount) {
    this.userCount = userCount;
  }
}
