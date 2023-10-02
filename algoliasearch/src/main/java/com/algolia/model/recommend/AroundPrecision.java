// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.recommend;

import com.algolia.exceptions.AlgoliaRuntimeException;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Precision of a geographical search (in meters), to [group results that are more or less the same
 * distance from a central
 * point](https://www.algolia.com/doc/guides/managing-results/refine-results/geolocation/in-depth/geo-ranking-precision/).
 */
@JsonDeserialize(using = AroundPrecision.Deserializer.class)
public interface AroundPrecision {
  /** AroundPrecision as Integer wrapper. */
  static AroundPrecision of(Integer value) {
    return new IntegerWrapper(value);
  }

  /** AroundPrecision as List<AroundPrecisionFromValueInner> wrapper. */
  static AroundPrecision of(List<AroundPrecisionFromValueInner> value) {
    return new ListOfAroundPrecisionFromValueInnerWrapper(value);
  }

  /** AroundPrecision as Integer wrapper. */
  @JsonSerialize(using = IntegerWrapper.Serializer.class)
  class IntegerWrapper implements AroundPrecision {

    private final Integer value;

    IntegerWrapper(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }

    static class Serializer extends JsonSerializer<IntegerWrapper> {

      @Override
      public void serialize(IntegerWrapper value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getValue());
      }
    }
  }

  /** AroundPrecision as List<AroundPrecisionFromValueInner> wrapper. */
  @JsonSerialize(using = ListOfAroundPrecisionFromValueInnerWrapper.Serializer.class)
  class ListOfAroundPrecisionFromValueInnerWrapper implements AroundPrecision {

    private final List<AroundPrecisionFromValueInner> value;

    ListOfAroundPrecisionFromValueInnerWrapper(List<AroundPrecisionFromValueInner> value) {
      this.value = value;
    }

    public List<AroundPrecisionFromValueInner> getValue() {
      return value;
    }

    static class Serializer extends JsonSerializer<ListOfAroundPrecisionFromValueInnerWrapper> {

      @Override
      public void serialize(ListOfAroundPrecisionFromValueInnerWrapper value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
        gen.writeObject(value.getValue());
      }
    }
  }

  class Deserializer extends JsonDeserializer<AroundPrecision> {

    private static final Logger LOGGER = Logger.getLogger(Deserializer.class.getName());

    @Override
    public AroundPrecision deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      JsonNode tree = jp.readValueAsTree();

      // deserialize Integer
      if (tree.isValueNode()) {
        try (JsonParser parser = tree.traverse(jp.getCodec())) {
          Integer value = parser.readValueAs(Integer.class);
          return AroundPrecision.of(value);
        } catch (Exception e) {
          // deserialization failed, continue
          LOGGER.finest("Failed to deserialize oneOf Integer (error: " + e.getMessage() + ") (type: Integer)");
        }
      }

      // deserialize List<AroundPrecisionFromValueInner>
      if (tree.isArray()) {
        try (JsonParser parser = tree.traverse(jp.getCodec())) {
          return parser.readValueAs(new TypeReference<List<AroundPrecisionFromValueInner>>() {});
        } catch (Exception e) {
          // deserialization failed, continue
          LOGGER.finest(
            "Failed to deserialize oneOf List<AroundPrecisionFromValueInner> (error: " +
            e.getMessage() +
            ") (type: List<AroundPrecisionFromValueInner>)"
          );
        }
      }
      throw new AlgoliaRuntimeException(String.format("Failed to deserialize json element: %s", tree));
    }

    /** Handle deserialization of the 'null' value. */
    @Override
    public AroundPrecision getNullValue(DeserializationContext ctxt) throws JsonMappingException {
      throw new JsonMappingException(ctxt.getParser(), "AroundPrecision cannot be null");
    }
  }
}