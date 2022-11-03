// This file is generated, manual changes will be lost - read more on
// https://github.com/algolia/api-clients-automation.

package com.algolia.model.analytics;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** GetNoClickRateResponse */
public class GetNoClickRateResponse {

  @JsonProperty("rate")
  private Double rate;

  @JsonProperty("count")
  private Integer count;

  @JsonProperty("noClickCount")
  private Integer noClickCount;

  @JsonProperty("dates")
  private List<NoClickRateEvent> dates = new ArrayList<>();

  public GetNoClickRateResponse setRate(Double rate) {
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

  public GetNoClickRateResponse setCount(Integer count) {
    this.count = count;
    return this;
  }

  /**
   * The number of click event.
   *
   * @return count
   */
  @javax.annotation.Nonnull
  public Integer getCount() {
    return count;
  }

  public GetNoClickRateResponse setNoClickCount(Integer noClickCount) {
    this.noClickCount = noClickCount;
    return this;
  }

  /**
   * The number of click event.
   *
   * @return noClickCount
   */
  @javax.annotation.Nonnull
  public Integer getNoClickCount() {
    return noClickCount;
  }

  public GetNoClickRateResponse setDates(List<NoClickRateEvent> dates) {
    this.dates = dates;
    return this;
  }

  public GetNoClickRateResponse addDates(NoClickRateEvent datesItem) {
    this.dates.add(datesItem);
    return this;
  }

  /**
   * A list of searches without clicks with their date, rate and counts.
   *
   * @return dates
   */
  @javax.annotation.Nonnull
  public List<NoClickRateEvent> getDates() {
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
    GetNoClickRateResponse getNoClickRateResponse = (GetNoClickRateResponse) o;
    return (
      Objects.equals(this.rate, getNoClickRateResponse.rate) &&
      Objects.equals(this.count, getNoClickRateResponse.count) &&
      Objects.equals(this.noClickCount, getNoClickRateResponse.noClickCount) &&
      Objects.equals(this.dates, getNoClickRateResponse.dates)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(rate, count, noClickCount, dates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetNoClickRateResponse {\n");
    sb.append("    rate: ").append(toIndentedString(rate)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    noClickCount: ").append(toIndentedString(noClickCount)).append("\n");
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