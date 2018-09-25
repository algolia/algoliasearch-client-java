package com.algolia.search.inputs.query_rules;

import com.algolia.search.Defaults;
import com.algolia.search.objects.Edit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = ConsequenceQueryDeserializer.class)
public abstract class ConsequenceQuery implements Serializable {}

class ConsequenceQueryDeserializer extends JsonDeserializer<ConsequenceQuery> {
  @Override
  public ConsequenceQuery deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException {
    ObjectCodec oc = jp.getCodec();
    JsonNode node = oc.readTree(jp);
    ObjectMapper objectMapper = Defaults.DEFAULT_OBJECT_MAPPER;

    if (node.has("edits")) {
      ObjectReader reader = objectMapper.readerFor(new TypeReference<List<Edit>>() {});
      List<Edit> list = reader.readValue(node.get("edits"));
      return new ConsequenceQueryObject().setEdits(list);
    } else if (node.has("remove")) {
      ObjectReader reader = objectMapper.readerFor(new TypeReference<List<String>>() {});

      List<String> list = reader.readValue(node.get("remove"));

      return new ConsequenceQueryObject().setRemove(list);
    } else {
      return new ConsequenceQueryString(
          node.asText()); // not sure it's not even possible to save a rule like that
    }
  }
}
