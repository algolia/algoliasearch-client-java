// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.recommend;

import com.algolia.exceptions.AlgoliaRuntimeException;
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
import java.util.logging.Logger;

/**
 * [Filter hits by facet
 * value](https://www.algolia.com/doc/api-reference/api-parameters/facetFilters/).
 */
@JsonDeserialize(using = FacetFilters.Deserializer.class)
@JsonSerialize(using = FacetFilters.Serializer.class)
public interface FacetFilters<T> extends CompoundType<T> {
  static FacetFilters<List<MixedSearchFilters>> of(List<MixedSearchFilters> inside) {
    return new FacetFiltersListOfMixedSearchFilters(inside);
  }

  static FacetFilters<String> of(String inside) {
    return new FacetFiltersString(inside);
  }

  class Serializer extends StdSerializer<FacetFilters> {

    public Serializer(Class<FacetFilters> t) {
      super(t);
    }

    public Serializer() {
      this(null);
    }

    @Override
    public void serialize(FacetFilters value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
      jgen.writeObject(value.get());
    }
  }

  class Deserializer extends StdDeserializer<FacetFilters> {

    private static final Logger LOGGER = Logger.getLogger(Deserializer.class.getName());

    public Deserializer() {
      this(FacetFilters.class);
    }

    public Deserializer(Class<?> vc) {
      super(vc);
    }

    @Override
    public FacetFilters deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      JsonNode tree = jp.readValueAsTree();

      // deserialize List<MixedSearchFilters>
      if (tree.isArray()) {
        try (JsonParser parser = tree.traverse(jp.getCodec())) {
          List<MixedSearchFilters> value = parser.readValueAs(new TypeReference<List<MixedSearchFilters>>() {});
          return FacetFilters.of(value);
        } catch (Exception e) {
          // deserialization failed, continue
          LOGGER.finest(
            "Failed to deserialize oneOf List<MixedSearchFilters> (error: " + e.getMessage() + ") (type: List<MixedSearchFilters>)"
          );
        }
      }

      // deserialize String
      if (tree.isValueNode()) {
        try (JsonParser parser = tree.traverse(jp.getCodec())) {
          String value = parser.readValueAs(new TypeReference<String>() {});
          return FacetFilters.of(value);
        } catch (Exception e) {
          // deserialization failed, continue
          LOGGER.finest("Failed to deserialize oneOf String (error: " + e.getMessage() + ") (type: String)");
        }
      }
      throw new AlgoliaRuntimeException(String.format("Failed to deserialize json element: %s", tree));
    }

    /** Handle deserialization of the 'null' value. */
    @Override
    public FacetFilters getNullValue(DeserializationContext ctxt) throws JsonMappingException {
      throw new JsonMappingException(ctxt.getParser(), "FacetFilters cannot be null");
    }
  }
}

class FacetFiltersListOfMixedSearchFilters implements FacetFilters<List<MixedSearchFilters>> {

  private final List<MixedSearchFilters> value;

  FacetFiltersListOfMixedSearchFilters(List<MixedSearchFilters> value) {
    this.value = value;
  }

  @Override
  public List<MixedSearchFilters> get() {
    return value;
  }
}

class FacetFiltersString implements FacetFilters<String> {

  private final String value;

  FacetFiltersString(String value) {
    this.value = value;
  }

  @Override
  public String get() {
    return value;
  }
}
