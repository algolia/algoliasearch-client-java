// This file is generated, manual changes will be lost - read more on
// https://github.com/algolia/api-clients-automation.

package com.algolia.model.abtesting;

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

/** AddABTestsVariant */
@JsonDeserialize(using = AddABTestsVariant.AddABTestsVariantDeserializer.class)
@JsonSerialize(using = AddABTestsVariant.AddABTestsVariantSerializer.class)
public abstract class AddABTestsVariant implements CompoundType {

  public static AddABTestsVariant of(AbTestsVariant inside) {
    return new AddABTestsVariantAbTestsVariant(inside);
  }

  public static AddABTestsVariant of(AbTestsVariantSearchParams inside) {
    return new AddABTestsVariantAbTestsVariantSearchParams(inside);
  }

  public static class AddABTestsVariantSerializer extends StdSerializer<AddABTestsVariant> {

    public AddABTestsVariantSerializer(Class<AddABTestsVariant> t) {
      super(t);
    }

    public AddABTestsVariantSerializer() {
      this(null);
    }

    @Override
    public void serialize(AddABTestsVariant value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {
      jgen.writeObject(value.getInsideValue());
    }
  }

  public static class AddABTestsVariantDeserializer extends StdDeserializer<AddABTestsVariant> {

    public AddABTestsVariantDeserializer() {
      this(AddABTestsVariant.class);
    }

    public AddABTestsVariantDeserializer(Class<?> vc) {
      super(vc);
    }

    @Override
    public AddABTestsVariant deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      JsonNode tree = jp.readValueAsTree();
      AddABTestsVariant deserialized = null;

      int match = 0;
      JsonToken token = tree.traverse(jp.getCodec()).nextToken();
      String currentType = "";
      // deserialize AbTestsVariant
      try {
        boolean attemptParsing = true;
        currentType = "AbTestsVariant";
        if (
          ((currentType.equals("Integer") || currentType.equals("Long")) && token == JsonToken.VALUE_NUMBER_INT) |
          ((currentType.equals("Float") || currentType.equals("Double")) && token == JsonToken.VALUE_NUMBER_FLOAT) |
          (currentType.equals("Boolean") && (token == JsonToken.VALUE_FALSE || token == JsonToken.VALUE_TRUE)) |
          (currentType.equals("String") && token == JsonToken.VALUE_STRING) |
          (currentType.startsWith("List<") && token == JsonToken.START_ARRAY)
        ) {
          deserialized =
            AddABTestsVariant.of((AbTestsVariant) tree.traverse(jp.getCodec()).readValueAs(new TypeReference<AbTestsVariant>() {}));
          match++;
        } else if (token == JsonToken.START_OBJECT) {
          try {
            deserialized =
              AddABTestsVariant.of((AbTestsVariant) tree.traverse(jp.getCodec()).readValueAs(new TypeReference<AbTestsVariant>() {}));
            match++;
          } catch (IOException e) {
            // do nothing
          }
        }
      } catch (Exception e) {
        // deserialization failed, continue
        System.err.println("Failed to deserialize oneOf AbTestsVariant (error: " + e.getMessage() + ") (type: " + currentType + ")");
      }

      // deserialize AbTestsVariantSearchParams
      try {
        boolean attemptParsing = true;
        currentType = "AbTestsVariantSearchParams";
        if (
          ((currentType.equals("Integer") || currentType.equals("Long")) && token == JsonToken.VALUE_NUMBER_INT) |
          ((currentType.equals("Float") || currentType.equals("Double")) && token == JsonToken.VALUE_NUMBER_FLOAT) |
          (currentType.equals("Boolean") && (token == JsonToken.VALUE_FALSE || token == JsonToken.VALUE_TRUE)) |
          (currentType.equals("String") && token == JsonToken.VALUE_STRING) |
          (currentType.startsWith("List<") && token == JsonToken.START_ARRAY)
        ) {
          deserialized =
            AddABTestsVariant.of(
              (AbTestsVariantSearchParams) tree.traverse(jp.getCodec()).readValueAs(new TypeReference<AbTestsVariantSearchParams>() {})
            );
          match++;
        } else if (token == JsonToken.START_OBJECT) {
          try {
            deserialized =
              AddABTestsVariant.of(
                (AbTestsVariantSearchParams) tree.traverse(jp.getCodec()).readValueAs(new TypeReference<AbTestsVariantSearchParams>() {})
              );
            match++;
          } catch (IOException e) {
            // do nothing
          }
        }
      } catch (Exception e) {
        // deserialization failed, continue
        System.err.println(
          "Failed to deserialize oneOf AbTestsVariantSearchParams (error: " + e.getMessage() + ") (type: " + currentType + ")"
        );
      }

      if (match == 1) {
        return deserialized;
      }
      throw new IOException(String.format("Failed deserialization for AddABTestsVariant: %d classes match result, expected 1", match));
    }

    /** Handle deserialization of the 'null' value. */
    @Override
    public AddABTestsVariant getNullValue(DeserializationContext ctxt) throws JsonMappingException {
      throw new JsonMappingException(ctxt.getParser(), "AddABTestsVariant cannot be null");
    }
  }
}

class AddABTestsVariantAbTestsVariant extends AddABTestsVariant {

  private final AbTestsVariant insideValue;

  AddABTestsVariantAbTestsVariant(AbTestsVariant insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  public AbTestsVariant getInsideValue() {
    return insideValue;
  }
}

class AddABTestsVariantAbTestsVariantSearchParams extends AddABTestsVariant {

  private final AbTestsVariantSearchParams insideValue;

  AddABTestsVariantAbTestsVariantSearchParams(AbTestsVariantSearchParams insideValue) {
    this.insideValue = insideValue;
  }

  @Override
  public AbTestsVariantSearchParams getInsideValue() {
    return insideValue;
  }
}