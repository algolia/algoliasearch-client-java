package com.algolia.search.inputs.query_rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
@JsonDeserialize(using = ConsequenceQueryDeserializer.class)
@JsonSerialize(using = ConsequenceQuerySerializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ConsequenceQuery {}

class ConsequenceQueryDeserializer extends JsonDeserializer<ConsequenceQuery> {

  @Override
  public ConsequenceQuery deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    JsonToken currentToken = p.getCurrentToken();
    if (currentToken.equals(JsonToken.VALUE_STRING)) {
      return new ConsequenceQueryString(p.getValueAsString());
    }

    JavaType jacksonType = ctxt.getTypeFactory().constructType(ConsequenceQueryObject.class);
    JsonDeserializer<?> deserializer = ctxt.findRootValueDeserializer(jacksonType);
    return (ConsequenceQueryObject) deserializer.deserialize(p, ctxt);
  }
}

class ConsequenceQuerySerializer extends JsonSerializer<ConsequenceQuery> {

  @Override
  public void serialize(ConsequenceQuery value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (value instanceof ConsequenceQueryString) {
      gen.writeString(((ConsequenceQueryString) value).getQuery());
    } else {
      gen.writeObject(value);
    }
  }
}
