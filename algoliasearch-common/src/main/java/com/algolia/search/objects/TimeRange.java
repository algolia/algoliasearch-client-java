package com.algolia.search.objects;

import com.algolia.search.custom_serializers.CustomZonedDateTimeEpochDeserializer;
import com.algolia.search.custom_serializers.CustomZonedDateTimeEpochSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeRange implements Serializable {

  @JsonSerialize(using = CustomZonedDateTimeEpochSerializer.class)
  @JsonDeserialize(using = CustomZonedDateTimeEpochDeserializer.class)
  private ZonedDateTime from;

  @JsonSerialize(using = CustomZonedDateTimeEpochSerializer.class)
  @JsonDeserialize(using = CustomZonedDateTimeEpochDeserializer.class)
  private ZonedDateTime until;

  public TimeRange() {}

  public TimeRange(ZonedDateTime from, ZonedDateTime until) {
    this.from = from;
    this.until = until;
  }

  public ZonedDateTime getFrom() {
    return from;
  }

  public void setFrom(ZonedDateTime from) {
    this.from = from;
  }

  public ZonedDateTime getUntil() {
    return until;
  }

  public void setUntil(ZonedDateTime until) {
    this.until = until;
  }
}
