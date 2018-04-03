package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.io.Serializable;

@JsonDeserialize(using = AroundRadiusJsonDeserializer.class)
@JsonSerialize(using = AroundRadiusJsonSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AroundRadius implements Serializable, CompoundType {

  public static AroundRadius of(String s) {
    return new AroundRadiusString(s);
  }

  public static AroundRadius of(Integer radius) {
    return new AroundRadiusInteger(radius);
  }

  @JsonIgnore
  public abstract Object getInsideValue();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AroundRadius that = (AroundRadius) o;

    return getInsideValue() != null
        ? getInsideValue().equals(that.getInsideValue())
        : that.getInsideValue() == null;
  }

  @Override
  public int hashCode() {
    return getInsideValue() != null ? getInsideValue().hashCode() : 0;
  }
}

class AroundRadiusString extends AroundRadius {

  private String insideValue;

  AroundRadiusString(String insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  @JsonIgnore
  public String getInsideValue() {
    return insideValue;
  }

  @Override
  public String toString() {
    return "AroundRadius{" + "string=" + insideValue + '}';
  }
}

class AroundRadiusInteger extends AroundRadius {

  private Integer insideValue;

  AroundRadiusInteger(Integer insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  @JsonIgnore
  public Integer getInsideValue() {
    return insideValue;
  }

  @Override
  public String toString() {
    return "AroundRadius{" + "integer=" + insideValue + '}';
  }
}

class AroundRadiusJsonDeserializer extends JsonDeserializer<AroundRadius> {

  @Override
  public AroundRadius deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    JsonToken currentToken = p.getCurrentToken();
    if (currentToken.equals(JsonToken.VALUE_STRING)) {
      return AroundRadius.of(p.getValueAsString());
    }

    return AroundRadius.of(p.getIntValue());
  }
}

class AroundRadiusJsonSerializer extends JsonSerializer<AroundRadius> {

  @SuppressWarnings("unchecked")
  @Override
  public void serialize(AroundRadius value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (value instanceof AroundRadiusString) {
      gen.writeString(value.getInsideValue().toString());
    } else if (value instanceof AroundRadiusInteger) {
      gen.writeNumber((Integer) value.getInsideValue());
    }
  }
}
