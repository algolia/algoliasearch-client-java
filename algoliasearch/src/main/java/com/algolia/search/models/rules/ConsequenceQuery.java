package com.algolia.search.models.rules;

import com.algolia.search.Defaults;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = ConsequenceQueryDeserializer.class)
public class ConsequenceQuery implements Serializable {

  private List<Edit> edits;

  public List<Edit> getEdits() {
    return edits;
  }

  public ConsequenceQuery setEdits(List<Edit> edits) {
    this.edits = edits;
    return this;
  }
}

class ConsequenceQueryDeserializer extends JsonDeserializer<ConsequenceQuery> {
  @Override
  public ConsequenceQuery deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException {
    ObjectCodec oc = jp.getCodec();
    JsonNode node = oc.readTree(jp);
    ObjectMapper objectMapper = Defaults.getObjectMapper();

    if (node.has("edits")) {
      ObjectReader reader = objectMapper.readerFor(new TypeReference<List<Edit>>() {});
      List<Edit> list = reader.readValue(node.get("edits"));
      return new ConsequenceQuery().setEdits(list);
    } else {

      ObjectReader reader = objectMapper.readerFor(new TypeReference<List<String>>() {});
      List<String> list = reader.readValue(node);

      List<Edit> edits =
          list.stream().map(r -> new Edit(EditType.REMOVE, r, null)).collect(Collectors.toList());

      return new ConsequenceQuery().setEdits(edits);
    }
  }
}
