package com.algolia.search.custom_serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class CustomZonedDateTimeEpochDeserializer extends StdDeserializer<ZonedDateTime> {

  public CustomZonedDateTimeEpochDeserializer() {
    this(null);
  }

  public CustomZonedDateTimeEpochDeserializer(Class<ZonedDateTime> t) {
    super(t);
  }

  @Override
  public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    Instant i = Instant.ofEpochSecond(p.getLongValue());
    return ZonedDateTime.ofInstant(i, ZoneOffset.UTC);
  }
}
