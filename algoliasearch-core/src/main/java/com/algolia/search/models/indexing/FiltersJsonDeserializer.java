package com.algolia.search.models.indexing;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class FiltersJsonDeserializer extends JsonDeserializer<List<List<String>>> {

  /**
   * Algolia's specific deserializer handling multiple form of (legacy) filters This reader is
   * converting single string to nested arrays. This reader is also converting single array to
   * nested arrays
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<List<String>> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {

    JsonToken currentToken = p.getCurrentToken();

    List<List<String>> result = null;

    switch (currentToken) {
      case START_ARRAY:
        List<Object> list = p.readValueAs(List.class);
        result = buildFilters(list);
        break;
      case VALUE_STRING:
        result =
            Arrays.stream(p.getValueAsString().split(","))
                .map(Collections::singletonList)
                .collect(Collectors.toList());
        break;
      case VALUE_NULL:
        break;
      default:
        throw new AlgoliaRuntimeException(
            "Unexpected JSON format occurred during the deserialization of filters.");
    }

    return result;
  }

  @SuppressWarnings("unchecked")
  private List<List<String>> buildFilters(List list) {
    return (List<List<String>>)
        list.stream()
            .map(
                element -> {
                  if (element instanceof String) {
                    return Collections.singletonList((String) element);
                  } else {
                    return element; // we suppose it's a List<String>
                  }
                })
            .collect(Collectors.toList());
  }
}
