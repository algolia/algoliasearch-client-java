package com.algolia.search.models.analytics;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ABTest implements Serializable {

  // Properties available at construction time
  private String name;
  private List<Variant> variants;
  private OffsetDateTime endAt;

  // dummy constructor for serialization
  public ABTest() {}

  public ABTest(String name, List<Variant> variants, OffsetDateTime endAt) {
    this.name = name;
    this.variants = variants;
    this.endAt = endAt;
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

  public OffsetDateTime getEndAt() {
    return endAt;
  }

  public void setEndAt(OffsetDateTime endAt) {
    this.endAt = endAt;
  }
}
