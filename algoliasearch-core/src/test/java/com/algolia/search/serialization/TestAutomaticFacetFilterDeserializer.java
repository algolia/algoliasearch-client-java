package com.algolia.search.serialization;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.algolia.search.Defaults;
import com.algolia.search.models.rules.AutomaticFacetFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestAutomaticFacetFilterDeserializer {

  @Test
  void testDeserializer() throws JsonProcessingException {
    String json = "{\"facet\":\"brand_name\",\"disjunctive\":true,\"score\":10}";
    AutomaticFacetFilter automaticFacetFilter =
        Defaults.getObjectMapper().readValue(json, AutomaticFacetFilter.class);

    assertThat(automaticFacetFilter.getFacet()).isEqualTo("brand_name");
    assertThat(automaticFacetFilter.getDisjunctive()).isTrue();
    assertThat(automaticFacetFilter.getScore()).isEqualTo(10);
  }

  @Test
  void testDeserializerOneAttribute() throws JsonProcessingException {
    String json = "{\"facet\":\"brand_name\"}";
    AutomaticFacetFilter automaticFacetFilter =
        Defaults.getObjectMapper().readValue(json, AutomaticFacetFilter.class);

    assertThat(automaticFacetFilter.getFacet()).isEqualTo("brand_name");
    assertThat(automaticFacetFilter.getDisjunctive()).isFalse();
    assertThat(automaticFacetFilter.getScore()).isNull();
  }

  @Test
  void testDeserializerLegacy() throws JsonProcessingException {
    String json = "\"brand_name\"";
    AutomaticFacetFilter automaticFacetFilter =
        Defaults.getObjectMapper().readValue(json, AutomaticFacetFilter.class);

    assertThat(automaticFacetFilter.getFacet()).isEqualTo("brand_name");
    assertThat(automaticFacetFilter.getDisjunctive()).isFalse();
    assertThat(automaticFacetFilter.getScore()).isNull();
  }

  @Test
  void testDeserializerLegacyList() throws JsonProcessingException {
    String json = "[\"lastname\",\"firstname\"]";
    List<AutomaticFacetFilter> deserialized =
        Defaults.getObjectMapper()
            .readValue(json, new TypeReference<List<AutomaticFacetFilter>>() {});

    AutomaticFacetFilter lastname = deserialized.get(0);
    assertThat(lastname.getFacet()).isEqualTo("lastname");
    assertThat(lastname.getDisjunctive()).isFalse();
    assertThat(lastname.getScore()).isNull();

    AutomaticFacetFilter firstname = deserialized.get(1);
    assertThat(firstname.getFacet()).isEqualTo("firstname");
    assertThat(firstname.getDisjunctive()).isFalse();
    assertThat(firstname.getScore()).isNull();
  }
}
