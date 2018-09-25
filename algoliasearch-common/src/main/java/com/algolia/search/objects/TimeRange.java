package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeRange implements Serializable {

  private long from;
  private long until;

  public TimeRange() {}

  public TimeRange(long from, long until) {
    this.from = from;
    this.until = until;
  }

  public long getFrom() {
    return from;
  }

  public void setFrom(int from) {
    this.from = from;
  }

  public long getUntil() {
    return until;
  }

  public void setUntil(int until) {
    this.until = until;
  }
}
