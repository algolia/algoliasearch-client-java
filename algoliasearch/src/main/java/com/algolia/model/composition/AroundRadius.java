// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.composition;

import com.algolia.exceptions.AlgoliaRuntimeException;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Maximum radius for a search around a central location. This parameter works in combination with
 * the `aroundLatLng` and `aroundLatLngViaIP` parameters. By default, the search radius is
 * determined automatically from the density of hits around the central location. The search radius
 * is small if there are many hits close to the central coordinates.
 */
@JsonDeserialize(using = AroundRadius.Deserializer.class)
public interface AroundRadius {
  // AroundRadius as Integer wrapper.
  static AroundRadius of(Integer value) {
    return new IntegerWrapper(value);
  }

  // AroundRadius as Integer wrapper.
  @JsonSerialize(using = IntegerWrapper.Serializer.class)
  class IntegerWrapper implements AroundRadius {

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

  class Deserializer extends JsonDeserializer<AroundRadius> {

    private static final Logger LOGGER = Logger.getLogger(Deserializer.class.getName());

    @Override
    public AroundRadius deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      JsonNode tree = jp.readValueAsTree();
      // deserialize Integer
      if (tree.isInt()) {
        try (JsonParser parser = tree.traverse(jp.getCodec())) {
          Integer value = parser.readValueAs(Integer.class);
          return new AroundRadius.IntegerWrapper(value);
        } catch (Exception e) {
          // deserialization failed, continue
          LOGGER.finest("Failed to deserialize oneOf Integer (error: " + e.getMessage() + ") (type: Integer)");
        }
      }
      // deserialize AroundRadiusAll
      if (tree.isTextual()) {
        try (JsonParser parser = tree.traverse(jp.getCodec())) {
          return parser.readValueAs(AroundRadiusAll.class);
        } catch (Exception e) {
          // deserialization failed, continue
          LOGGER.finest("Failed to deserialize oneOf AroundRadiusAll (error: " + e.getMessage() + ") (type: AroundRadiusAll)");
        }
      }
      throw new AlgoliaRuntimeException(String.format("Failed to deserialize json element: %s", tree));
    }

    /** Handle deserialization of the 'null' value. */
    @Override
    public AroundRadius getNullValue(DeserializationContext ctxt) throws JsonMappingException {
      throw new JsonMappingException(ctxt.getParser(), "AroundRadius cannot be null");
    }
  }
}
