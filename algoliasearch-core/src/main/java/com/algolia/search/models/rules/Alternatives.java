package com.algolia.search.models.rules;

import com.algolia.search.models.CompoundType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.io.Serializable;

/**
 * Alternatives may transform into JSON object in the future. That's why it's implementing
 * CompoundType.
 */
@JsonDeserialize(using = AlternativesDeserializer.class)
@JsonSerialize(using = AlternativesSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Alternatives implements Serializable, CompoundType {

  public static Alternatives of(Boolean bool) {
    return new AlternativesBoolean(bool);
  }

  @Override
  public int hashCode() {
    return getInsideValue() != null ? getInsideValue().hashCode() : 0;
  }
}

class AlternativesBoolean extends Alternatives {
  private final boolean insideValue;

  AlternativesBoolean(boolean insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  public Object getInsideValue() {
    return insideValue;
  }
}

class AlternativesDeserializer extends JsonDeserializer<Alternatives> {
  @Override
  public Alternatives deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return Alternatives.of(p.getBooleanValue());
  }
}

class AlternativesSerializer extends JsonSerializer<Alternatives> {
  @Override
  public void serialize(Alternatives value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (value instanceof AlternativesBoolean) {
      gen.writeBoolean((Boolean) value.getInsideValue());
    }
  }
}
