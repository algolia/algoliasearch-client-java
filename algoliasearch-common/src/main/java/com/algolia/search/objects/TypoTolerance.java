package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@JsonDeserialize(using = TypoToleranceJsonDeserializer.class)
@JsonSerialize(using = TypoToleranceJsonSerializer.class)
public abstract class TypoTolerance {

  public static TypoTolerance of(String string) {
    return new TypoToleranceAsString(string);
  }

  public static TypoTolerance of(Boolean bool) {
    return new TypoToleranceAsBoolean(bool);
  }

  @JsonIgnore
  abstract Object getInsideValue();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TypoTolerance that = (TypoTolerance) o;

    return getInsideValue() != null ? getInsideValue().equals(that.getInsideValue()) : that.getInsideValue() == null;

  }

  @Override
  public int hashCode() {
    return getInsideValue() != null ? getInsideValue().hashCode() : 0;
  }
}

@JsonDeserialize(as = TypoToleranceAsString.class)
class TypoToleranceAsString extends TypoTolerance {

  private String insideValue;

  TypoToleranceAsString(String insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  @JsonIgnore
  public String getInsideValue() {
    return insideValue;
  }

}

@JsonDeserialize(as = TypoToleranceAsBoolean.class)
class TypoToleranceAsBoolean extends TypoTolerance {

  private Boolean insideValue;

  TypoToleranceAsBoolean(Boolean insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  @JsonIgnore
  public Boolean getInsideValue() {
    return insideValue;
  }
}

class TypoToleranceJsonDeserializer extends JsonDeserializer<TypoTolerance> {

  @Override
  public TypoTolerance deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    JsonToken currentToken = p.getCurrentToken();
    if (currentToken.equals(JsonToken.VALUE_NUMBER_INT)) {
      return TypoTolerance.of(p.getValueAsString());
    }

    return TypoTolerance.of(p.getBooleanValue());
  }
}

class TypoToleranceJsonSerializer extends JsonSerializer<TypoTolerance> {

  @Override
  public void serialize(TypoTolerance value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    if (value instanceof TypoToleranceAsBoolean) {
      gen.writeBoolean((Boolean) value.getInsideValue());
    } else if (value instanceof TypoToleranceAsString) {
      gen.writeString((String) value.getInsideValue());
    }
  }
}


