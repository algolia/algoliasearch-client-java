package com.algolia.search.serialization;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.algolia.search.Defaults;
import com.algolia.search.models.rules.AutomaticFacetFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

public class TestAutomaticFacetFilterDeserializer {

  @Test
  void testDeserializer() throws JsonProcessingException {
    String json = "{\"facet\":\"brand_name\"}";
    AutomaticFacetFilter automaticFacetFilter =
        Defaults.getObjectMapper().readValue(json, AutomaticFacetFilter.class);
    assertThat(automaticFacetFilter.getFacet()).isEqualTo("brand_name");
  }

  @Test
  void testDeserializerLegacy() throws JsonProcessingException {
    String json = "\"brand_name\"";
    AutomaticFacetFilter automaticFacetFilter =
        Defaults.getObjectMapper().readValue(json, AutomaticFacetFilter.class);
    assertThat(automaticFacetFilter.getFacet()).isEqualTo("brand_name");
  }
}
