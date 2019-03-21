package com.algolia.search.models.indexing;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.List;

public class FlatListSerializer extends StdSerializer<List<String>> {

  public FlatListSerializer() {
    this(null);
  }

  private FlatListSerializer(Class<List<String>> t) {
    super(t);
  }

  @Override
  public void serialize(List<String> value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    gen.writeString(String.join(";", value));
  }
}
