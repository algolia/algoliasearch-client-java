package com.algolia.search.custom_serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.ZonedDateTime;

public class CustomZonedDateTimeEpochSerializer extends StdSerializer<ZonedDateTime> {

  public CustomZonedDateTimeEpochSerializer() {
    this(null);
  }

  public CustomZonedDateTimeEpochSerializer(Class<ZonedDateTime> t) {
    super(t);
  }

  @Override
  public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeNumber(value.toEpochSecond());
  }
}
