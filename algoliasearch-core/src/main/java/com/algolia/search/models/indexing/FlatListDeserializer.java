package com.algolia.search.models.indexing;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.List;

public class FlatListDeserializer extends StdDeserializer<String> {

  public FlatListDeserializer() {
    this(null);
  }

  private FlatListDeserializer(Class<List<String>> t) {
    super(t);
  }

  @Override
  public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    if (p.isExpectedStartArrayToken()) {
      ObjectMapper mapper = (ObjectMapper) p.getCodec();
      List<String> list = mapper.readValue(p, new TypeReference<List<String>>() {});
      return String.join(",", list);
    }

    return p.getText();
  }
}
