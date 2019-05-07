package com.algolia.search.models.indexing;

import java.io.Serializable;

public class AroundPrecision implements Serializable {

  public AroundPrecision() {}

  public AroundPrecision(long from, long value) {
    this.from = from;
    this.value = value;
  }

  public long getFrom() {
    return from;
  }

  public AroundPrecision setFrom(long from) {
    this.from = from;
    return this;
  }

  public long getValue() {
    return value;
  }

  public AroundPrecision setValue(long value) {
    this.value = value;
    return this;
  }

  private long from;
  private long value;
}
