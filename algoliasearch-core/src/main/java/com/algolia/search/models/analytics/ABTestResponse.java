package com.algolia.search.models.analytics;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class ABTestResponse extends ABTest {

  // dummy constructor for serialization
  public ABTestResponse() {}

  public ABTestResponse(String name, List<Variant> variants, OffsetDateTime endAt) {
    super(name, variants, endAt);
  }

  public long getAbTestID() {
    return abTestID;
  }

  public void setAbTestID(long abTestID) {
    this.abTestID = abTestID;
  }

  public int getClickSignificance() {
    return clickSignificance;
  }

  public void setClickSignificance(int clickSignificance) {
    this.clickSignificance = clickSignificance;
  }

  public int getConversionSignificance() {
    return conversionSignificance;
  }

  public void setConversionSignificance(int conversionSignificance) {
    this.conversionSignificance = conversionSignificance;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "ABTestResponse{"
        + "abTestID="
        + abTestID
        + ", clickSignificance="
        + clickSignificance
        + ", conversionSignificance="
        + conversionSignificance
        + ", createdAt="
        + createdAt
        + ", status='"
        + status
        + '\''
        + '}';
  }

  // Properties set by the AB Testing API
  private long abTestID;
  private int clickSignificance;
  private int conversionSignificance;
  private ZonedDateTime createdAt;
  private String status;
}
