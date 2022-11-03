// This file is generated, manual changes will be lost - read more on
// https://github.com/algolia/api-clients-automation.

package com.algolia.model.analytics;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** GetConversationRateResponse */
public class GetConversationRateResponse {

  @JsonProperty("rate")
  private Double rate;

  @JsonProperty("trackedSearchCount")
  private Integer trackedSearchCount;

  @JsonProperty("conversionCount")
  private Integer conversionCount;

  @JsonProperty("dates")
  private List<ConversionRateEvent> dates = new ArrayList<>();

  public GetConversationRateResponse setRate(Double rate) {
    this.rate = rate;
    return this;
  }

  /**
   * The click-through rate.
   *
   * @return rate
   */
  @javax.annotation.Nonnull
  public Double getRate() {
    return rate;
  }

  public GetConversationRateResponse setTrackedSearchCount(Integer trackedSearchCount) {
    this.trackedSearchCount = trackedSearchCount;
    return this;
  }

  /**
   * The number of tracked search click.
   *
   * @return trackedSearchCount
   */
  @javax.annotation.Nonnull
  public Integer getTrackedSearchCount() {
    return trackedSearchCount;
  }

  public GetConversationRateResponse setConversionCount(Integer conversionCount) {
    this.conversionCount = conversionCount;
    return this;
  }

  /**
   * The number of converted clicks.
   *
   * @return conversionCount
   */
  @javax.annotation.Nonnull
  public Integer getConversionCount() {
    return conversionCount;
  }

  public GetConversationRateResponse setDates(List<ConversionRateEvent> dates) {
    this.dates = dates;
    return this;
  }

  public GetConversationRateResponse addDates(ConversionRateEvent datesItem) {
    this.dates.add(datesItem);
    return this;
  }

  /**
   * A list of conversion events with their date.
   *
   * @return dates
   */
  @javax.annotation.Nonnull
  public List<ConversionRateEvent> getDates() {
    return dates;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetConversationRateResponse getConversationRateResponse = (GetConversationRateResponse) o;
    return (
      Objects.equals(this.rate, getConversationRateResponse.rate) &&
      Objects.equals(this.trackedSearchCount, getConversationRateResponse.trackedSearchCount) &&
      Objects.equals(this.conversionCount, getConversationRateResponse.conversionCount) &&
      Objects.equals(this.dates, getConversationRateResponse.dates)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(rate, trackedSearchCount, conversionCount, dates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetConversationRateResponse {\n");
    sb.append("    rate: ").append(toIndentedString(rate)).append("\n");
    sb.append("    trackedSearchCount: ").append(toIndentedString(trackedSearchCount)).append("\n");
    sb.append("    conversionCount: ").append(toIndentedString(conversionCount)).append("\n");
    sb.append("    dates: ").append(toIndentedString(dates)).append("\n");
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