package com.algolia.search.inputs.analytics;

import com.algolia.search.custom_serializers.CustomZonedDateTimeDeserializer;
import com.algolia.search.custom_serializers.CustomZonedDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class ABTest implements Serializable {

  // Properties available at construction time
  private String name;
  private List<Variant> variants;

  @JsonSerialize(using = CustomZonedDateTimeSerializer.class)
  @JsonDeserialize(using = CustomZonedDateTimeDeserializer.class)
  private ZonedDateTime endAt;

  // Properties set by the AB Testing API
  private long abTestID;
  private int clickSignificance;
  private int conversionSignificance;

  @JsonDeserialize(using = CustomZonedDateTimeDeserializer.class)
  private ZonedDateTime createdAt;

  private String status;

  public ABTest() {}

  public ABTest(String name, List<Variant> variants, LocalDateTime endAt) {
    this.name = name;
    this.variants = variants;
    this.endAt = endAt.atZone(ZoneId.of("UTC"));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Variant> getVariants() {
    return variants;
  }

  public void setVariants(List<Variant> variants) {
    this.variants = variants;
  }

  public ZonedDateTime getEndAt() {
    return endAt;
  }

  public void setEndAt(ZonedDateTime endAt) {
    this.endAt = endAt;
  }

  @JsonIgnore
  public long getAbTestID() {
    return abTestID;
  }

  @JsonProperty
  public void setAbTestID(long abTestID) {
    this.abTestID = abTestID;
  }

  @JsonIgnore
  public int getClickSignificance() {
    return clickSignificance;
  }

  @JsonProperty
  public void setClickSignificance(int clickSignificance) {
    this.clickSignificance = clickSignificance;
  }

  @JsonIgnore
  public int getConversionSignificance() {
    return conversionSignificance;
  }

  @JsonProperty
  public void setConversionSignificance(int conversionSignificance) {
    this.conversionSignificance = conversionSignificance;
  }

  @JsonIgnore
  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  @JsonProperty
  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @JsonIgnore
  public String getStatus() {
    return status;
  }

  @JsonProperty
  public void setStatus(String status) {
    this.status = status;
  }
}
