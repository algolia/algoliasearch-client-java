package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeRange implements Serializable {

  @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
  private OffsetDateTime from;

  @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
  private OffsetDateTime until;

  public TimeRange() {}

  public TimeRange(OffsetDateTime from, OffsetDateTime until) {
    this.from = from;
    this.until = until;
  }

  public OffsetDateTime getFrom() {
    return from;
  }

  public void setFrom(OffsetDateTime from) {
    this.from = from;
  }

  public OffsetDateTime getUntil() {
    return until;
  }

  public void setUntil(OffsetDateTime until) {
    this.until = until;
  }

  @Override
  public String toString() {
    return "TimeRange{" + "from=" + from + ", until=" + until + '}';
  }
}
