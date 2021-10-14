package com.algolia.search.models.indexing;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class FiltersJsonDeserializer extends JsonDeserializer {

  /**
   * Algolia's specific deserializer handling multiple form of (legacy) filters This reader is
   * converting single string to nested arrays. This reader is also converting single array to
   * nested arrays
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

    JsonToken currentToken = p.getCurrentToken();

    ArrayList<List<String>> result = new ArrayList<>();

    switch (currentToken) {
      case START_ARRAY:
        List list = p.readValueAs(List.class);
        list.forEach(
          v -> {
            if (v instanceof String) {
              result.add(Collections.singletonList((String) v));
            } else {
              result.add((List<String>) v);
            }
        });
        break;
      case VALUE_STRING:
        result.add(Arrays.asList(p.getValueAsString().split(",")));
        break;
      case VALUE_NULL:
        break;
      default:
        throw new AlgoliaRuntimeException(
            "Unexpected JSON format occurred during the deserialization of filters.");
    }

    return result;
  }
}
