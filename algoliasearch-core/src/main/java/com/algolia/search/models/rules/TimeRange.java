package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(using = TimeRangeSerializer.class)
@JsonDeserialize(using = TimeRangeDeserializer.class)
public class TimeRange implements Serializable {

  private OffsetDateTime from;

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
}

class TimeRangeSerializer extends JsonSerializer<TimeRange> {

  @Override
  public void serialize(TimeRange value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();
    gen.writeObjectField("from", value.getFrom().toEpochSecond());
    gen.writeObjectField("until", value.getUntil().toEpochSecond());
    gen.writeEndObject();
  }
}

class TimeRangeDeserializer extends JsonDeserializer<TimeRange> {

  @Override
  public TimeRange deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    JsonNode node = p.getCodec().readTree(p);

    int fromTimestamp = (Integer) node.get("from").numberValue();
    Instant fromInstant = Instant.ofEpochSecond(fromTimestamp);
    OffsetDateTime from = OffsetDateTime.ofInstant(fromInstant, ZoneOffset.UTC);

    int untilTimestamp = (Integer) node.get("until").numberValue();
    Instant untilInstant = Instant.ofEpochSecond(untilTimestamp);
    OffsetDateTime until = OffsetDateTime.ofInstant(untilInstant, ZoneOffset.UTC);

    return new TimeRange(from, until);
  }
}
