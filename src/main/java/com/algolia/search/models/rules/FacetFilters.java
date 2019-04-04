package com.algolia.search.models.rules;

import com.algolia.search.Defaults;
import com.algolia.search.models.CompoundType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Facet filters More information:
 *
 * @see <a href="https://www.algolia.com/doc/api-client/methods/query-rules>Algolia.com</a>
 */
@JsonDeserialize(using = FacetFiltersJsonDeserializer.class)
@JsonSerialize(using = FacetFiltersJsonSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class FacetFilters implements Serializable, CompoundType {

  public static FacetFilters ofList(List<String> filters) {
    return new FacetFiltersAsListOfString(filters);
  }

  public static FacetFilters ofListOfList(List<List<String>> filters) {
    return new FacetFiltersAsListOfList(filters);
  }

  @JsonIgnore
  public abstract Object getInsideValue();
}

class FacetFiltersJsonDeserializer extends JsonDeserializer {

  @Override
  @SuppressWarnings("unchecked")
  public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    List list = p.readValueAs(List.class);
    if (list.isEmpty()) {
      return new FacetFiltersAsListOfString();
    }
    if (list.get(0) instanceof String) {
      return new FacetFiltersAsListOfString(list);
    }
    return new FacetFiltersAsListOfList(list);
  }
}

class FacetFiltersJsonSerializer extends JsonSerializer<FacetFilters> {

  @Override
  public void serialize(FacetFilters value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {

    ObjectMapper objectMapper = Defaults.getObjectMapper();

    if (value instanceof FacetFiltersAsListOfString) {
      FacetFiltersAsListOfString filters = (FacetFiltersAsListOfString) value;
      objectMapper.writeValue(gen, filters.getFilters());

    } else if (value instanceof FacetFiltersAsListOfList) {
      FacetFiltersAsListOfList filters = (FacetFiltersAsListOfList) value;
      objectMapper.writeValue(gen, filters.getFilters());

    } else {
      throw new IOException(
          "cannot serialize facetFilters: unknown input type '" + value.getClass().getName() + "'");
    }
  }
}
