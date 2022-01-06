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
        List list = p.readValueAs(List.class);
        if (list.stream().allMatch(String.class::isInstance)) { // are all elements strings?
          result = Collections.singletonList(list);
        } else {
          result = buildFilters(list);
        }
        break;
      case VALUE_STRING:
        result = Collections.singletonList(Arrays.asList(p.getValueAsString().split(",")));
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