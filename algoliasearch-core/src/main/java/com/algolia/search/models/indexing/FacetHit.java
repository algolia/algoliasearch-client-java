package com.algolia.search.models.indexing;

import java.io.Serializable;

public class FacetHit implements Serializable {

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

  public FacetHit setCount(Long count) {
    this.count = count;
    return this;
  }

  private String value;
  private String highlighted;
  private Long count;
}
