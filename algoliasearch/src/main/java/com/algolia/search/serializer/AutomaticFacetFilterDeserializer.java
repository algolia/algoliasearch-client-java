package com.algolia.search.serializer;

import com.algolia.search.Defaults;
import com.algolia.search.models.rules.AutomaticFacetFilter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AutomaticFacetFilterDeserializer extends JsonDeserializer {

  /**
   * This object can be a List<String> or a List<AutomaticFacetFilter> so it needs a custom
   * deserializer
   */
  @Override
  public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

    ObjectCodec oc = jp.getCodec();
    JsonNode node = oc.readTree(jp);
    ObjectMapper objectMapper = Defaults.getObjectMapper();

    if ((!node.isNull() && node.size() > 0)) {
      if (node.get(0).has("disjunctive") || node.get(0).has("score")) {
        ObjectReader reader =
            objectMapper.readerFor(new TypeReference<List<AutomaticFacetFilter>>() {});
        return reader.readValue(node);
      } else {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<String>>() {});
        List<String> list = reader.readValue(node);

        return list.stream()
            .map(r -> new AutomaticFacetFilter(r, false, null))
            .collect(Collectors.toList());
      }
    } else {
      return null;
    }
  }
}
