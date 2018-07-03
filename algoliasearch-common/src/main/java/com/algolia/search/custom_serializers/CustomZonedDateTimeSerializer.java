package com.algolia.search.custom_serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomZonedDateTimeSerializer extends StdSerializer<ZonedDateTime> {
  private static DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

  public CustomZonedDateTimeSerializer() {
    this(null);
  }

  public CustomZonedDateTimeSerializer(Class<ZonedDateTime> t) {
    super(t);
  }

  @Override
  public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeString(formatter.format(value));
  }
}
