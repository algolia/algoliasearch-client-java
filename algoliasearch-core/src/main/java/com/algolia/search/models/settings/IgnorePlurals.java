package com.algolia.search.models.settings;

import com.algolia.search.models.CompoundType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.BooleanDeserializer;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@JsonDeserialize(using = IgnorePluralsDeserializer.class)
@JsonSerialize(using = IgnorePluralsSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class IgnorePlurals implements Serializable, CompoundType {

  public static IgnorePlurals of(Boolean bool) {
    return new IgnorePluralsBoolean(bool);
  }

  public static IgnorePlurals of(List<String> strings) {
    return new IgnorePluralsListString(strings);
  }

  @JsonIgnore
  public abstract Object getInsideValue();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    IgnorePlurals that = (IgnorePlurals) o;

    return getInsideValue() != null
        ? getInsideValue().equals(that.getInsideValue())
        : that.getInsideValue() == null;
  }

  @Override
  public int hashCode() {
    return getInsideValue() != null ? getInsideValue().hashCode() : 0;
  }
}

class IgnorePluralsBoolean extends IgnorePlurals {

  private final boolean insideValue;

  IgnorePluralsBoolean(boolean insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  public Object getInsideValue() {
    return insideValue;
  }

  @Override
  public String toString() {
    return "IgnorePlurals{" + "boolean=" + insideValue + '}';
  }
}

class IgnorePluralsListString extends IgnorePlurals {

  private final List<String> insideValue;

  IgnorePluralsListString(List<String> insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  public Object getInsideValue() {
    return insideValue;
  }

  @Override
  public String toString() {
    return "IgnorePlurals{" + "list=" + insideValue + '}';
  }
}

class IgnorePluralsDeserializer extends JsonDeserializer<IgnorePlurals> {

  @Override
  @SuppressWarnings("unchecked")
  public IgnorePlurals deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

    if (p.isExpectedStartArrayToken()) {
      List languages = p.readValueAs(List.class);
      return IgnorePlurals.of(languages);
    }

    BooleanDeserializer booleanDeserializer = new BooleanDeserializer(Boolean.TYPE, Boolean.FALSE);
    return IgnorePlurals.of(booleanDeserializer.deserialize(p, ctxt));
  }
}

class IgnorePluralsSerializer extends JsonSerializer<IgnorePlurals> {

  @SuppressWarnings("unchecked")
  @Override
  public void serialize(IgnorePlurals value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (value instanceof IgnorePluralsBoolean) {
      gen.writeBoolean((Boolean) value.getInsideValue());
    } else if (value instanceof IgnorePluralsListString) {
      List<String> languages = (List<String>) value.getInsideValue();
      gen.writeStartArray();
      for (String lang : languages) {
        gen.writeString(lang);
      }
      gen.writeEndArray();
    }
  }
}
