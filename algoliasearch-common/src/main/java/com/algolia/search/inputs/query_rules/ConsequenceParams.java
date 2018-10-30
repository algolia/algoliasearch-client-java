package com.algolia.search.inputs.query_rules;

import com.algolia.search.Defaults;
import com.algolia.search.objects.AutomaticFacetFilter;
import com.algolia.search.objects.Query;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsequenceParams<T> extends Query<ConsequenceParams<T>> {
  private ConsequenceQuery query;

  @JsonDeserialize(using = AutomaticFacetFilterDeserializer.class)
  private List<T> automaticFacetFilters;

  @JsonDeserialize(using = AutomaticFacetFilterDeserializer.class)
  private List<T> automaticOptionalFacetFilters;

  public ConsequenceParams() {}

  public ConsequenceQuery getQuery() {
    return query;
  }

  public ConsequenceParams setQuery(ConsequenceQuery query) {
    this.query = query;
    return this;
  }

  public List<T> getAutomaticFacetFilters() {
    return automaticFacetFilters;
  }

  public ConsequenceParams setAutomaticFacetFilters(List<T> automaticFacetFilters) {
    this.automaticFacetFilters = automaticFacetFilters;
    return this;
  }

  public List<T> getAutomaticOptionalFacetFilters() {
    return automaticOptionalFacetFilters;
  }

  public ConsequenceParams setAutomaticOptionalFacetFilters(List<T> automaticOptionalFacetFilters) {
    this.automaticOptionalFacetFilters = automaticOptionalFacetFilters;
    return this;
  }
}

class AutomaticFacetFilterDeserializer extends JsonDeserializer {

  /**
   * This object can be a List<String> or a List<AutomaticFacetFilter> so it needs a custom
   * deserializer
   *
   * @param jp
   * @param ctxt
   * @return
   * @throws IOException
   */
  @Override
  public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

    ObjectCodec oc = jp.getCodec();
    JsonNode node = oc.readTree(jp);
    ObjectMapper objectMapper = Defaults.DEFAULT_OBJECT_MAPPER;

    if ((!node.isNull() && node.size() > 0)) {
      if (node.get(0).has("disjunctive") || node.get(0).has("score")) {
        ObjectReader reader =
            objectMapper.readerFor(new TypeReference<List<AutomaticFacetFilter>>() {});
        return reader.readValue(node);
      } else {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<String>>() {});
        return reader.readValue(node);
      }
    } else {
      return null;
    }
  }
}
