package com.algolia.search.responses;

public class FacetHit {

  private String value;
  private String highlighted;
  private Integer count;

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

  public Integer getCount() {
    return count;
  }

  public FacetHit setCount(Integer count) {
    this.count = count;
    return this;
  }
}
