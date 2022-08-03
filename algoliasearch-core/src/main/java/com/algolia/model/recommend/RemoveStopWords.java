// This file is generated, manual changes will be lost - read more on
// https://github.com/algolia/api-clients-automation.

package com.algolia.model.recommend;

import com.algolia.utils.CompoundType;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.List;

/**
 * Removes stop (common) words from the query before executing it. removeStopWords is used in
 * conjunction with the queryLanguages setting. list: language ISO codes for which ignoring plurals
 * should be enabled. This list will override any values that you may have set in queryLanguages.
 * true: enables the stop word functionality, ensuring that stop words are removed from
 * consideration in a search. The languages supported here are either every language, or those set
 * by queryLanguages. false: disables stop word functionality, allowing stop words to be taken into
 * account in a search.
 */
@JsonDeserialize(using = RemoveStopWords.RemoveStopWordsDeserializer.class)
@JsonSerialize(using = RemoveStopWords.RemoveStopWordsSerializer.class)
public abstract class RemoveStopWords implements CompoundType {

  public static RemoveStopWords of(Boolean inside) {
    return new RemoveStopWordsBoolean(inside);
  }

  public static RemoveStopWords of(List<String> inside) {
    return new RemoveStopWordsListOfString(inside);
  }

  public static class RemoveStopWordsSerializer extends StdSerializer<RemoveStopWords> {

    public RemoveStopWordsSerializer(Class<RemoveStopWords> t) {
      super(t);
    }

    public RemoveStopWordsSerializer() {
      this(null);
    }

    @Override
    public void serialize(RemoveStopWords value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {
      jgen.writeObject(value.getInsideValue());
    }
  }

  public static class RemoveStopWordsDeserializer extends StdDeserializer<RemoveStopWords> {

    public RemoveStopWordsDeserializer() {
      this(RemoveStopWords.class);
    }

    public RemoveStopWordsDeserializer(Class<?> vc) {
      super(vc);
    }

    @Override
    public RemoveStopWords deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      JsonNode tree = jp.readValueAsTree();
      RemoveStopWords deserialized = null;

      int match = 0;
      JsonToken token = tree.traverse(jp.getCodec()).nextToken();
      String currentType = "";
      // deserialize Boolean
      try {
        boolean attemptParsing = true;
        currentType = "Boolean";
        if (
          ((currentType.equals("Integer") || currentType.equals("Long")) && token == JsonToken.VALUE_NUMBER_INT) |
          ((currentType.equals("Float") || currentType.equals("Double")) && token == JsonToken.VALUE_NUMBER_FLOAT) |
          (currentType.equals("Boolean") && (token == JsonToken.VALUE_FALSE || token == JsonToken.VALUE_TRUE)) |
          (currentType.equals("String") && token == JsonToken.VALUE_STRING) |
          (currentType.startsWith("List<") && token == JsonToken.START_ARRAY)
        ) {
          deserialized = RemoveStopWords.of((Boolean) tree.traverse(jp.getCodec()).readValueAs(new TypeReference<Boolean>() {}));
          match++;
        } else if (token == JsonToken.START_OBJECT) {
          try {
            deserialized = RemoveStopWords.of((Boolean) tree.traverse(jp.getCodec()).readValueAs(new TypeReference<Boolean>() {}));
            match++;
          } catch (IOException e) {
            // do nothing
          }
        }
      } catch (Exception e) {
        // deserialization failed, continue
        System.err.println("Failed to deserialize oneOf Boolean (error: " + e.getMessage() + ") (type: " + currentType + ")");
      }

      // deserialize List<String>
      try {
        boolean attemptParsing = true;
        currentType = "List<String>";
        if (
          ((currentType.equals("Integer") || currentType.equals("Long")) && token == JsonToken.VALUE_NUMBER_INT) |
          ((currentType.equals("Float") || currentType.equals("Double")) && token == JsonToken.VALUE_NUMBER_FLOAT) |
          (currentType.equals("Boolean") && (token == JsonToken.VALUE_FALSE || token == JsonToken.VALUE_TRUE)) |
          (currentType.equals("String") && token == JsonToken.VALUE_STRING) |
          (currentType.startsWith("List<") && token == JsonToken.START_ARRAY)
        ) {
          deserialized = RemoveStopWords.of((List<String>) tree.traverse(jp.getCodec()).readValueAs(new TypeReference<List<String>>() {}));
          match++;
        } else if (token == JsonToken.START_OBJECT) {
          try {
            deserialized =
              RemoveStopWords.of((List<String>) tree.traverse(jp.getCodec()).readValueAs(new TypeReference<List<String>>() {}));
            match++;
          } catch (IOException e) {
            // do nothing
          }
        }
      } catch (Exception e) {
        // deserialization failed, continue
        System.err.println("Failed to deserialize oneOf List<String> (error: " + e.getMessage() + ") (type: " + currentType + ")");
      }

      if (match == 1) {
        return deserialized;
      }
      throw new IOException(String.format("Failed deserialization for RemoveStopWords: %d classes match result, expected 1", match));
    }

    /** Handle deserialization of the 'null' value. */
    @Override
    public RemoveStopWords getNullValue(DeserializationContext ctxt) throws JsonMappingException {
      throw new JsonMappingException(ctxt.getParser(), "RemoveStopWords cannot be null");
    }
  }
}

class RemoveStopWordsBoolean extends RemoveStopWords {

  private final Boolean insideValue;

  RemoveStopWordsBoolean(Boolean insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  public Boolean getInsideValue() {
    return insideValue;
  }
}

class RemoveStopWordsListOfString extends RemoveStopWords {

  private final List<String> insideValue;

  RemoveStopWordsListOfString(List<String> insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  public List<String> getInsideValue() {
    return insideValue;
  }
}