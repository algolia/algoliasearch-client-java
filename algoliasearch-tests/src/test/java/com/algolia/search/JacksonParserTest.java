package com.algolia.search;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.models.personalization.EventScoring;
import com.algolia.search.models.personalization.FacetScoring;
import com.algolia.search.models.personalization.SetStrategyRequest;
import com.algolia.search.models.rules.AutomaticFacetFilter;
import com.algolia.search.models.rules.ConsequenceParams;
import com.algolia.search.models.settings.Distinct;
import com.algolia.search.models.settings.IgnorePlurals;
import com.algolia.search.models.settings.IndexSettings;
import com.algolia.search.models.settings.RemoveStopWords;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

class JacksonParserTest {

  @Test
  void serializeDistinct() throws IOException {
    IndexSettings settings;

    settings = new IndexSettings().setDistinct(Distinct.of(true));
    assertThat(Defaults.getObjectMapper().writeValueAsString(settings))
        .isEqualTo("{\"distinct\":true}");

    settings = new IndexSettings().setDistinct(Distinct.of(1));
    assertThat(Defaults.getObjectMapper().writeValueAsString(settings))
        .isEqualTo("{\"distinct\":1}");
  }

  @Test
  void deserializeDistinct() throws IOException {
    Distinct distinct;

    distinct =
        Defaults.getObjectMapper()
            .readValue("{\"distinct\":true}", IndexSettings.class)
            .getDistinct();
    assertThat(distinct).isEqualTo(Distinct.of(true));

    distinct =
        Defaults.getObjectMapper().readValue("{\"distinct\":1}", IndexSettings.class).getDistinct();
    assertThat(distinct).isEqualTo(Distinct.of(1));
  }

  @Test
  void serializeRemoveStopWords() throws IOException {
    IndexSettings settings;

    settings = new IndexSettings().setRemoveStopWords(true);
    assertThat(Defaults.getObjectMapper().writeValueAsString(settings))
        .isEqualTo("{\"removeStopWords\":true}");

    settings = new IndexSettings().setRemoveStopWords(Arrays.asList("a", "b"));
    assertThat(Defaults.getObjectMapper().writeValueAsString(settings))
        .isEqualTo("{\"removeStopWords\":[\"a\",\"b\"]}");

    settings = new IndexSettings().setRemoveStopWords(RemoveStopWords.of(false));
    assertThat(Defaults.getObjectMapper().writeValueAsString(settings))
        .isEqualTo("{\"removeStopWords\":false}");
  }

  @Test
  void deserializeRemoveStopWords() throws IOException {
    RemoveStopWords removeStopWords;

    removeStopWords =
        Defaults.getObjectMapper()
            .readValue("{\"removeStopWords\":true}", IndexSettings.class)
            .getRemoveStopWords();
    assertThat(removeStopWords).isEqualTo(RemoveStopWords.of(true));

    removeStopWords =
        Defaults.getObjectMapper()
            .readValue("{\"removeStopWords\":[\"a\",\"b\"]}", IndexSettings.class)
            .getRemoveStopWords();
    assertThat(removeStopWords).isEqualTo(RemoveStopWords.of(Arrays.asList("a", "b")));
  }

  @Test
  void serializeIgnorePlurals() throws IOException {
    IndexSettings settings;

    settings = new IndexSettings().setIgnorePlurals(true);
    assertThat(Defaults.getObjectMapper().writeValueAsString(settings))
        .isEqualTo("{\"ignorePlurals\":true}");

    settings =
        new IndexSettings().setIgnorePlurals(IgnorePlurals.of(Collections.singletonList("en")));
    assertThat(Defaults.getObjectMapper().writeValueAsString(settings))
        .isEqualTo("{\"ignorePlurals\":[\"en\"]}");

    settings = new IndexSettings().setIgnorePlurals(Arrays.asList("en", "fr"));
    assertThat(Defaults.getObjectMapper().writeValueAsString(settings))
        .isEqualTo("{\"ignorePlurals\":[\"en\",\"fr\"]}");
  }

  @Test
  void deserializeIgnorePlurals() throws IOException {
    IgnorePlurals ignorePlurals;

    ignorePlurals =
        Defaults.getObjectMapper()
            .readValue("{\"ignorePlurals\":true}", IndexSettings.class)
            .getIgnorePlurals();
    assertThat(ignorePlurals).isEqualTo(IgnorePlurals.of(true));

    ignorePlurals =
        Defaults.getObjectMapper()
            .readValue("{\"ignorePlurals\":[\"en\"]}", IndexSettings.class)
            .getIgnorePlurals();
    assertThat(ignorePlurals).isEqualTo(IgnorePlurals.of(Collections.singletonList("en")));

    ignorePlurals =
        Defaults.getObjectMapper()
            .readValue("{\"ignorePlurals\":[\"en\",\"fr\"]}", IndexSettings.class)
            .getIgnorePlurals();
    assertThat(ignorePlurals).isEqualTo(IgnorePlurals.of(Arrays.asList("en", "fr")));
  }

  @Test
  void serializePersonalization() throws JsonProcessingException {

    HashMap<String, EventScoring> eventScoring = new HashMap<>();
    eventScoring.put("Add to cart", new EventScoring(50, "conversion"));
    eventScoring.put("Purchase", new EventScoring(100, "conversion"));

    HashMap<String, FacetScoring> facetScoring = new HashMap<>();
    facetScoring.put("brand", new FacetScoring(100));
    facetScoring.put("categories", new FacetScoring(10));

    SetStrategyRequest strategyTosave = new SetStrategyRequest(eventScoring, facetScoring);

    assertThat(Defaults.getObjectMapper().writeValueAsString(strategyTosave))
        .isEqualTo(
            "{\"eventsScoring\":{\"Purchase\":{\"score\":100,\"type\":\"conversion\"},\"Add to cart\":{\"score\":50,\"type\":\"conversion\"}},\"facetsScoring\":{\"categories\":{\"score\":10},\"brand\":{\"score\":100}}}");
  }

  @Test
  void serializeFacetFilters() throws IOException {

    List<AutomaticFacetFilter> automaticFacetFilters =
        Defaults.getObjectMapper()
            .readValue(
                "{\"automaticFacetFilters\":[\"lastname\",\"firstname\"]}", ConsequenceParams.class)
            .getAutomaticFacetFilters();

    assertThat(
            automaticFacetFilters.stream()
                .anyMatch(r -> r.getFacet().equals("lastname") && !r.getDisjunctive()))
        .isTrue();
    assertThat(
            automaticFacetFilters.stream()
                .anyMatch(r -> r.getFacet().equals("firstname") && !r.getDisjunctive()))
        .isTrue();
  }
}
