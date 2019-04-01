package com.algolia.search;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.models.indexing.AroundRadius;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.personalization.EventScoring;
import com.algolia.search.models.personalization.FacetScoring;
import com.algolia.search.models.personalization.SetStrategyRequest;
import com.algolia.search.models.rules.*;
import com.algolia.search.models.settings.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.*;
import org.junit.jupiter.api.Disabled;
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
  void deserializeLegacySettings() throws IOException {
    IndexSettings settings;

    settings =
        Defaults.getObjectMapper()
            .readValue(
                "{ \"attributesToIndex\":[\"attr1\", \"attr2\"],\"numericAttributesToIndex\": [\"attr1\", \"attr2\"],\"slaves\":[\"index1\", \"index2\"]}",
                IndexSettings.class);

    assertThat(settings.getReplicas()).isEqualTo(Arrays.asList("index1", "index2"));
    assertThat(settings.getSearchableAttributes()).isEqualTo(Arrays.asList("attr1", "attr2"));
    assertThat(settings.getNumericAttributesForFiltering()).isEqualTo(Arrays.asList("attr1", "attr2"));
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

  @Test
  void deserializeLegacyEdit() throws IOException {
    List<Edit> edits = Defaults.getObjectMapper().readValue("[\"lastname\",\"firstname\"]", ConsequenceQuery.class).getEdits();

    assertThat(edits.get(0).getType()).isEqualTo(EditType.REMOVE);
    assertThat(edits.get(0).getDelete()).isEqualTo("lastname");
    assertThat(edits.get(0).getInsert()).isNull();

    assertThat(edits.get(1).getType()).isEqualTo(EditType.REMOVE);
    assertThat(edits.get(1).getDelete()).isEqualTo("firstname");
    assertThat(edits.get(1).getInsert()).isNull();
  }

  @Test
  void typoToleranceBoolean() throws IOException {
    IndexSettings settings = new IndexSettings().setTypoTolerance(TypoTolerance.of(true));
    IndexSettings result = serializeDeserialize(settings);
    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getTypoTolerance()).isEqualTo(TypoTolerance.of(true));

    settings = new IndexSettings().setTypoTolerance(TypoTolerance.of(false));
    result = serializeDeserialize(settings);
    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getTypoTolerance()).isEqualTo(TypoTolerance.of(false));
  }

  @Test
  void keepDiacriticsOnCharacters() throws IOException {
    IndexSettings settings = new IndexSettings().setKeepDiacriticsOnCharacters("øé");
    IndexSettings result = serializeDeserialize(settings);
    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getKeepDiacriticsOnCharacters()).isEqualTo("øé");
  }

  @Test
  void queryLanguages() throws IOException {
    IndexSettings settings = new IndexSettings().setQueryLanguages(Arrays.asList("a", "b"));
    IndexSettings result = serializeDeserialize(settings);
    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getQueryLanguages()).isEqualTo(Arrays.asList("a", "b"));
  }

  @Test
  void camelCaseAttributes() throws IOException {
    IndexSettings settings = new IndexSettings().setCamelCaseAttributes(Arrays.asList("a", "b"));
    IndexSettings result = serializeDeserialize(settings);
    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getCamelCaseAttributes()).isEqualTo(Arrays.asList("a", "b"));
  }

  @Test
  void decompoundedAttributes() throws IOException {
    Map<String, List<String>> expected = new HashMap<>();
    expected.put("de", Arrays.asList("attr1", "attr2"));
    IndexSettings settings = new IndexSettings().setDecompoundedAttributes(expected);
    IndexSettings result = serializeDeserialize(settings);
    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getDecompoundedAttributes()).isEqualTo(expected);
  }

  @Test
  void typoToleranceString() throws IOException {
    IndexSettings settings = new IndexSettings().setTypoTolerance(TypoTolerance.of("min"));
    IndexSettings result = serializeDeserialize(settings);

    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getTypoTolerance()).isEqualTo(TypoTolerance.of("min"));
  }

  @Test
  void removeStopWordsBoolean() throws IOException {
    IndexSettings settings = new IndexSettings().setRemoveStopWords(RemoveStopWords.of(true));
    IndexSettings result = serializeDeserialize(settings);
    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getRemoveStopWords()).isEqualTo(RemoveStopWords.of(true));

    settings = new IndexSettings().setRemoveStopWords(RemoveStopWords.of(false));
    result = serializeDeserialize(settings);
    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getRemoveStopWords()).isEqualTo(RemoveStopWords.of(false));
  }

  @Test
  void removeStopWordsList() throws IOException {
    IndexSettings settings =
        new IndexSettings().setRemoveStopWords(RemoveStopWords.of(Arrays.asList("a", "b")));
    IndexSettings result = serializeDeserialize(settings);

    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getRemoveStopWords()).isEqualTo(RemoveStopWords.of(Arrays.asList("a", "b")));
  }

  @Test
  void queryStringWithQuery() {
    Query query = new Query();
    query.setQuery("search");
    assertThat(query.toParam()).isEqualTo("query=search");
  }

  @Test
  void queryStringEmpty() {
    Query query = new Query();
    assertThat(query.toParam()).isEqualTo("");
  }

  @Test
  void queryWithHTMLEntities() {
    Query query = new Query("&?@:=");
    assertThat(query.toParam()).isEqualTo("query=%26%3F%40%3A%3D");
  }

  @Test
  void queryWithUTF8() {
    Query query = new Query("é®„");
    assertThat(query.toParam()).isEqualTo("query=%C3%A9%C2%AE%E2%80%9E");
  }

  @Test
  void queryWithDistinct() {
    Query query = new Query("").setDistinct(Distinct.of(0));
    assertThat(query.toParam()).isEqualTo("distinct=0&query=");
  }

  @Disabled
  void queryWithMultipleParams() {
    Query query = new Query("é®„").setTagFilters(Collections.singletonList("(attribute)"));
    assertThat(query.toParam()).isEqualTo("tagFilters=%28attribute%29&query=%C3%A9%C2%AE%E2%80%9E");
  }

  @Test
  void queryWithRemoveStopWords() {
    Query query = new Query("").setRemoveStopWords(RemoveStopWords.of(true));
    assertThat(query.toParam()).isEqualTo("removeStopWords=true&query=");
  }

  @Test
  void queryWithIgnorePlurals() {
    Query query = new Query("").setIgnorePlurals(IgnorePlurals.of(true));
    assertThat(query.toParam()).isEqualTo("ignorePlurals=true&query=");
  }

  @Test
  void queryWithAroundRadius() throws JsonProcessingException {
    Query query = new Query("").setAroundRadius(AroundRadius.of("all"));
    String serialized = Defaults.getObjectMapper().writeValueAsString(query);
    assertThat(query.toParam()).isEqualTo("aroundRadius=all&query=");
    assertThat(serialized).isEqualTo("{\"aroundRadius\":\"all\",\"query\":\"\"}");

    query = new Query("").setAroundRadius(AroundRadius.of(1));
    serialized = Defaults.getObjectMapper().writeValueAsString(query);
    assertThat(query.toParam()).isEqualTo("aroundRadius=1&query=");
    assertThat(serialized).isEqualTo("{\"aroundRadius\":1,\"query\":\"\"}");
  }

  private IndexSettings serializeDeserialize(IndexSettings obj) throws IOException {
    String serialized = Defaults.getObjectMapper().writeValueAsString(obj);
    return Defaults.getObjectMapper()
        .readValue(
            serialized,
            Defaults.getObjectMapper().getTypeFactory().constructType(IndexSettings.class));
  }
}
