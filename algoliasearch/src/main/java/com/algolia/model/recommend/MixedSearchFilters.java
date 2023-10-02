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

/** MixedSearchFilters */
@JsonDeserialize(using = MixedSearchFilters.Deserializer.class)
public interface MixedSearchFilters {
  /** MixedSearchFilters as List<String> wrapper. */
  static MixedSearchFilters of(List<String> value) {
    return new ListOfStringWrapper(value);
  }

  /** MixedSearchFilters as String wrapper. */
  static MixedSearchFilters of(String value) {
    return new StringWrapper(value);
  }

  /** MixedSearchFilters as List<String> wrapper. */
  @JsonSerialize(using = ListOfStringWrapper.Serializer.class)
  class ListOfStringWrapper implements MixedSearchFilters {

    private final List<String> value;

    ListOfStringWrapper(List<String> value) {
      this.value = value;
    }

    public List<String> getValue() {
      return value;
    }

    static class Serializer extends JsonSerializer<ListOfStringWrapper> {

      @Override
      public void serialize(ListOfStringWrapper value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getValue());
      }
    }
  }

  /** MixedSearchFilters as String wrapper. */
  @JsonSerialize(using = StringWrapper.Serializer.class)
  class StringWrapper implements MixedSearchFilters {

    private final String value;

    StringWrapper(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    static class Serializer extends JsonSerializer<StringWrapper> {

      @Override
      public void serialize(StringWrapper value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getValue());
      }
    }
  }

  class Deserializer extends JsonDeserializer<MixedSearchFilters> {

    private static final Logger LOGGER = Logger.getLogger(Deserializer.class.getName());

    @Override
    public MixedSearchFilters deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      JsonNode tree = jp.readValueAsTree();

      // deserialize List<String>
      if (tree.isArray()) {
        try (JsonParser parser = tree.traverse(jp.getCodec())) {
          return parser.readValueAs(new TypeReference<List<String>>() {});
        } catch (Exception e) {
          // deserialization failed, continue
          LOGGER.finest("Failed to deserialize oneOf List<String> (error: " + e.getMessage() + ") (type: List<String>)");
        }
      }

      // deserialize String
      if (tree.isValueNode()) {
        try (JsonParser parser = tree.traverse(jp.getCodec())) {
          String value = parser.readValueAs(String.class);
          return MixedSearchFilters.of(value);
        } catch (Exception e) {
          // deserialization failed, continue
          LOGGER.finest("Failed to deserialize oneOf String (error: " + e.getMessage() + ") (type: String)");
        }
      }
      throw new AlgoliaRuntimeException(String.format("Failed to deserialize json element: %s", tree));
    }

    /** Handle deserialization of the 'null' value. */
    @Override
    public MixedSearchFilters getNullValue(DeserializationContext ctxt) throws JsonMappingException {
      throw new JsonMappingException(ctxt.getParser(), "MixedSearchFilters cannot be null");
    }
  }
}