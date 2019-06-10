package com.algolia.search.models.rules;

import com.algolia.search.Defaults;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Automatic facet filters parameter. More information:
 *
 * @see <a href="https://www.algolia.com/doc/api-client/methods/query-rules">Algolia.com</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutomaticFacetFilter implements Serializable {

  private String facet;
  private Boolean disjunctive = false;
  private Integer score;

  public AutomaticFacetFilter() {}

  public AutomaticFacetFilter(String facet, Boolean disjunctive, Integer score) {
    this.facet = facet;
    this.disjunctive = disjunctive;
    this.score = score;
  }

  public AutomaticFacetFilter(String facet) {
    this.facet = facet;
  }

  public AutomaticFacetFilter(String facet, Boolean disjunctive) {
    this.facet = facet;
    this.disjunctive = disjunctive;
  }

  public String getFacet() {
    return facet;
  }

  public AutomaticFacetFilter setFacet(String facet) {
    this.facet = facet;
    return this;
  }

  public Boolean getDisjunctive() {
    return disjunctive;
  }

  public AutomaticFacetFilter setDisjunctive(Boolean disjunctive) {
    this.disjunctive = disjunctive;
    return this;
  }

  public Integer getScore() {
    return score;
  }

  public AutomaticFacetFilter setScore(Integer score) {
    this.score = score;
    return this;
  }
}

class AutomaticFacetFilterDeserializer extends JsonDeserializer {

  /**
   * This object can be a List<String> or a List<AutomaticFacetFilter> so it needs a custom
   * deserializer
   */
  @Override
  public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

    ObjectCodec oc = jp.getCodec();
    JsonNode node = oc.readTree(jp);
    ObjectMapper objectMapper = Defaults.getObjectMapper();

    if ((!node.isNull() && node.size() > 0)) {
      if (node.get(0).has("disjunctive") || node.get(0).has("score")) {
        ObjectReader reader =
            objectMapper.readerFor(new TypeReference<List<AutomaticFacetFilter>>() {});
        return reader.readValue(node);
      } else {
        ObjectReader reader = objectMapper.readerFor(List.class);
        List<String> list = reader.readValue(node);

        return list.stream()
            .map(r -> new AutomaticFacetFilter(r, false, null))
            .collect(Collectors.toList());
      }
    } else {
      return null;
    }
  }
}
