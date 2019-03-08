package com.algolia.search.serializer;

import com.algolia.search.Defaults;
import com.algolia.search.models.ConsequenceQuery;
import com.algolia.search.models.Edit;
import com.algolia.search.models.EditType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ConsequenceQueryDeserializer extends JsonDeserializer<ConsequenceQuery> {
  @Override
  public ConsequenceQuery deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException {
    ObjectCodec oc = jp.getCodec();
    JsonNode node = oc.readTree(jp);
    ObjectMapper objectMapper = Defaults.DEFAULT_OBJECT_MAPPER;

    if (node.has("edits")) {
      ObjectReader reader = objectMapper.readerFor(new TypeReference<List<Edit>>() {});
      List<Edit> list = reader.readValue(node.get("edits"));
      return new ConsequenceQuery().setEdits(list);
    } else {

      ObjectReader reader = objectMapper.readerFor(new TypeReference<List<String>>() {});
      List<String> list = reader.readValue(node);

      List<Edit> edits =
          list.stream().map(r -> new Edit(EditType.Remove, r, null)).collect(Collectors.toList());

      return new ConsequenceQuery().setEdits(edits);
    }
  }
}
