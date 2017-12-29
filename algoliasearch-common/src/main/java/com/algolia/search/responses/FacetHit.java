package com.algolia.search.responses;

import com.fasterxml.jackson.annotation.JsonSetter;
import java.io.Serializable;

@SuppressWarnings("unused")
public class FacetHit implements Serializable {

  private String value;
  private String highlighted;
  private Long count;

  public String getValue() {
    return value;
  }

  public FacetHit setValue(String value) {
    this.value = value;
    return this;
  }

  public String getHighlighted() {
    return highlighted;
  }

  public FacetHit setHighlighted(String highlighted) {
    this.highlighted = highlighted;
    return this;
  }

  public Long getCount() {
    return count;
  }

  public FacetHit setCount(Integer count) {
    return this.setCount(count.longValue());
  }

  @JsonSetter
  public FacetHit setCount(Long count) {
    this.count = count;
    return this;
  }

  @Override
  public String toString() {
    return "FacetHit{"
        + "value='"
        + value
        + '\''
        + ", highlighted='"
        + highlighted
        + '\''
        + ", count="
        + count
        + '}';
  }
}
