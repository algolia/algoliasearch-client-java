package com.algolia.search.custom_serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomZonedDateTimeDeserializer extends StdDeserializer<ZonedDateTime> {
  private static DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

  public CustomZonedDateTimeDeserializer() {
    this(null);
  }

  public CustomZonedDateTimeDeserializer(Class<ZonedDateTime> t) {
    super(t);
  }

  @Override
  public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return ZonedDateTime.from(formatter.parse(p.getText()));
  }
}
