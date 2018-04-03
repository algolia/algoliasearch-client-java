package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.io.Serializable;

@JsonDeserialize(using = DistinctJsonDeserializer.class)
@JsonSerialize(using = DistinctJsonSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Distinct implements Serializable, CompoundType {

  public static Distinct of(Integer integer) {
    return new DistinctAsInteger(integer);
  }

  public static Distinct of(Boolean bool) {
    return new DistinctAsBoolean(bool);
  }

  @JsonIgnore
  public abstract Object getInsideValue();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Distinct that = (Distinct) o;

    return getInsideValue() != null
        ? getInsideValue().equals(that.getInsideValue())
        : that.getInsideValue() == null;
  }

  @Override
  public int hashCode() {
    return getInsideValue() != null ? getInsideValue().hashCode() : 0;
  }
}

@JsonDeserialize(as = DistinctAsInteger.class)
class DistinctAsInteger extends Distinct {

  private Integer insideValue;

  DistinctAsInteger(Integer insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  @JsonIgnore
  public Integer getInsideValue() {
    return insideValue;
  }

  @Override
  public String toString() {
    return "Distinct{" + "integer=" + insideValue + '}';
  }
}

@JsonDeserialize(as = DistinctAsBoolean.class)
class DistinctAsBoolean extends Distinct {

  private Boolean insideValue;

  DistinctAsBoolean(Boolean insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  @JsonIgnore
  public Boolean getInsideValue() {
    return insideValue;
  }

  @Override
  public String toString() {
    return "Distinct{" + "boolean=" + insideValue + '}';
  }
}

class DistinctJsonDeserializer extends JsonDeserializer<Distinct> {

  @Override
  public Distinct deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonToken currentToken = p.getCurrentToken();
    if (currentToken.equals(JsonToken.VALUE_NUMBER_INT)) {
      return Distinct.of(p.getIntValue());
    }

    return Distinct.of(p.getBooleanValue());
  }
}

class DistinctJsonSerializer extends JsonSerializer<Distinct> {

  @Override
  public void serialize(Distinct value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException, JsonProcessingException {
    if (value instanceof DistinctAsBoolean) {
      DistinctAsBoolean d = (DistinctAsBoolean) value;
      gen.writeBoolean(d.getInsideValue());
    } else if (value instanceof DistinctAsInteger) {
      DistinctAsInteger d = (DistinctAsInteger) value;
      gen.writeNumber(d.getInsideValue());
    }
  }
}
