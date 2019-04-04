package com.algolia.search.models.settings;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.models.CompoundType;
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
import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = RemoveStopWordsDeserializer.class)
@JsonSerialize(using = RemoveStopWordsSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class RemoveStopWords implements Serializable, CompoundType {

  public static RemoveStopWords of(Boolean bool) {
    return new RemoveStopWordsBoolean(bool);
  }

  public static RemoveStopWords of(List<String> strings) {
    return new RemoveStopWordsListString(strings);
  }

  @JsonIgnore
  public abstract Object getInsideValue();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RemoveStopWords that = (RemoveStopWords) o;

    return getInsideValue() != null
        ? getInsideValue().equals(that.getInsideValue())
        : that.getInsideValue() == null;
  }

  @Override
  public int hashCode() {
    return getInsideValue() != null ? getInsideValue().hashCode() : 0;
  }
}

class RemoveStopWordsBoolean extends RemoveStopWords {

  private final boolean insideValue;

  RemoveStopWordsBoolean(boolean insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  public Object getInsideValue() {
    return insideValue;
  }

  @Override
  public String toString() {
    return "RemoveStopWords{" + "boolean=" + insideValue + '}';
  }
}

class RemoveStopWordsListString extends RemoveStopWords {

  private final List<String> insideValue;

  RemoveStopWordsListString(List<String> insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  public Object getInsideValue() {
    return insideValue;
  }

  @Override
  public String toString() {
    return "RemoveStopWords{" + "list=" + insideValue + '}';
  }
}

class RemoveStopWordsDeserializer extends JsonDeserializer<RemoveStopWords> {

  @Override
  public RemoveStopWords deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    JsonToken currentToken = p.getCurrentToken();

    if (currentToken.equals(JsonToken.VALUE_FALSE) || currentToken.equals(JsonToken.VALUE_TRUE)) {
      return RemoveStopWords.of(p.getBooleanValue());
    }

    if (currentToken == JsonToken.START_ARRAY) {

      List<String> removeStopWords = new ArrayList<>();

      while (p.nextToken() != JsonToken.END_ARRAY) {
        removeStopWords.add(p.getValueAsString());
      }
      return RemoveStopWords.of(removeStopWords);
    }

    throw new AlgoliaRuntimeException("Unsupported deserialization for RemoveStopWords");
  }
}

class RemoveStopWordsSerializer extends JsonSerializer<RemoveStopWords> {

  @SuppressWarnings("unchecked")
  @Override
  public void serialize(RemoveStopWords value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (value instanceof RemoveStopWordsBoolean) {
      gen.writeBoolean((Boolean) value.getInsideValue());
    } else if (value instanceof RemoveStopWordsListString) {
      List<String> list = (List<String>) value.getInsideValue();
      gen.writeObject(list);
    }
  }
}
