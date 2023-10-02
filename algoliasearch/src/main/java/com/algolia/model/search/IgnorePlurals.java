// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.search;

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
 * Treats singular, plurals, and other forms of declensions as matching terms. `ignorePlurals` is
 * used in conjunction with the `queryLanguages` setting. _list_: language ISO codes for which
 * ignoring plurals should be enabled. This list will override any values that you may have set in
 * `queryLanguages`. _true_: enables the ignore plurals feature, where singulars and plurals are
 * considered equivalent (\"foot\" = \"feet\"). The languages supported here are either [every
 * language](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/handling-natural-languages-nlp/in-depth/supported-languages/)
 * (this is the default) or those set by `queryLanguages`. _false_: turns off the ignore plurals
 * feature, so that singulars and plurals aren't considered to be the same (\"foot\" will not find
 * \"feet\").
 */
@JsonDeserialize(using = IgnorePlurals.Deserializer.class)
public interface IgnorePlurals {
  /** IgnorePlurals as Boolean wrapper. */
  static IgnorePlurals of(Boolean value) {
    return new BooleanWrapper(value);
  }

  /** IgnorePlurals as List<String> wrapper. */
  static IgnorePlurals of(List<String> value) {
    return new ListOfStringWrapper(value);
  }

  /** IgnorePlurals as Boolean wrapper. */
  @JsonSerialize(using = BooleanWrapper.Serializer.class)
  class BooleanWrapper implements IgnorePlurals {

    private final Boolean value;

    BooleanWrapper(Boolean value) {
      this.value = value;
    }

    public Boolean getValue() {
      return value;
    }

    static class Serializer extends JsonSerializer<BooleanWrapper> {

      @Override
      public void serialize(BooleanWrapper value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getValue());
      }
    }
  }

  /** IgnorePlurals as List<String> wrapper. */
  @JsonSerialize(using = ListOfStringWrapper.Serializer.class)
  class ListOfStringWrapper implements IgnorePlurals {

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

  class Deserializer extends JsonDeserializer<IgnorePlurals> {

    private static final Logger LOGGER = Logger.getLogger(Deserializer.class.getName());

    @Override
    public IgnorePlurals deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      JsonNode tree = jp.readValueAsTree();

      // deserialize Boolean
      if (tree.isValueNode()) {
        try (JsonParser parser = tree.traverse(jp.getCodec())) {
          Boolean value = parser.readValueAs(Boolean.class);
          return IgnorePlurals.of(value);
        } catch (Exception e) {
          // deserialization failed, continue
          LOGGER.finest("Failed to deserialize oneOf Boolean (error: " + e.getMessage() + ") (type: Boolean)");
        }
      }

      // deserialize List<String>
      if (tree.isArray()) {
        try (JsonParser parser = tree.traverse(jp.getCodec())) {
          return parser.readValueAs(new TypeReference<List<String>>() {});
        } catch (Exception e) {
          // deserialization failed, continue
          LOGGER.finest("Failed to deserialize oneOf List<String> (error: " + e.getMessage() + ") (type: List<String>)");
        }
      }
      throw new AlgoliaRuntimeException(String.format("Failed to deserialize json element: %s", tree));
    }

    /** Handle deserialization of the 'null' value. */
    @Override
    public IgnorePlurals getNullValue(DeserializationContext ctxt) throws JsonMappingException {
      throw new JsonMappingException(ctxt.getParser(), "IgnorePlurals cannot be null");
    }
  }
}