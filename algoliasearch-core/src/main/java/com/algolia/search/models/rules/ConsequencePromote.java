package com.algolia.search.models.rules;

import static com.algolia.search.models.rules.ConsequencePromote.*;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Consequence parameter. More information:
 *
 * @see <a href="https://www.algolia.com/doc/api-client/methods/query-rules">Algolia.com</a>
 */
@JsonSerialize(using = ConsequencePromoteSerializer.class)
@JsonDeserialize(using = ConsequencePromoteDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ConsequencePromote implements Serializable {

  static final String KEY_POSITION = "position";
  static final String KEY_OBJECT_ID = "objectID";
  static final String KEY_OBJECT_IDS = "objectIDs";

  public static ConsequencePromote of(String objectID, Integer position) {
    return new Single().setObjectID(objectID).setPosition(position);
  }

  public static ConsequencePromote of(List<String> objectIDs, Integer position) {
    return new Multiple().setObjectID(objectIDs).setPosition(position);
  }

  @JsonProperty(KEY_POSITION)
  private Integer position;

  public Integer getPosition() {
    return position;
  }

  public ConsequencePromote setPosition(Integer position) {
    this.position = position;
    return this;
  }

  public static class Single extends ConsequencePromote {

    @JsonProperty(KEY_OBJECT_ID)
    private String objectID;

    public String getObjectID() {
      return objectID;
    }

    public Single setObjectID(String objectID) {
      this.objectID = objectID;
      return this;
    }

    @Override
    public Single setPosition(Integer position) {
      super.setPosition(position);
      return this;
    }
  }

  public static class Multiple extends ConsequencePromote {

    @JsonProperty(KEY_OBJECT_IDS)
    private List<String> objectIDs;

    public List<String> getObjectIDs() {
      return objectIDs;
    }

    public Multiple setObjectID(List<String> objectIDs) {
      this.objectIDs = objectIDs;
      return this;
    }

    @Override
    public Multiple setPosition(Integer position) {
      super.setPosition(position);
      return this;
    }
  }
}

class ConsequencePromoteSerializer extends JsonSerializer<ConsequencePromote> {

  @Override
  public void serialize(ConsequencePromote value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();
    gen.writeObjectField(KEY_POSITION, value.getPosition());
    if (value instanceof ConsequencePromote.Single) {
      ConsequencePromote.Single promote = (ConsequencePromote.Single) value;
      gen.writeObjectField(KEY_OBJECT_ID, promote.getObjectID());
    } else if (value instanceof ConsequencePromote.Multiple) {
      ConsequencePromote.Multiple promote = (ConsequencePromote.Multiple) value;
      gen.writeObjectField(KEY_OBJECT_IDS, promote.getObjectIDs());
    }
    gen.writeEndObject();
  }
}

class ConsequencePromoteDeserializer extends JsonDeserializer<ConsequencePromote> {

  @Override
  public ConsequencePromote deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
    ObjectNode object = objectMapper.readTree(p);
    int position = object.get(KEY_POSITION).asInt();

    if (object.has(KEY_OBJECT_ID)) {
      String objectID = object.get(KEY_OBJECT_ID).asText();
      return ConsequencePromote.of(objectID, position);
    } else if (object.has(KEY_OBJECT_IDS)) {
      JsonNode node = object.get(KEY_OBJECT_IDS);
      TypeReference<List<String>> type = new TypeReference<List<String>>() {};
      List<String> objectIDs = objectMapper.readerFor(type).readValue(node);
      return ConsequencePromote.of(objectIDs, position);
    } else {
      throw new AlgoliaRuntimeException(
          "Unexpected JSON format occurred during the deserialization of promote rules");
    }
  }
}
