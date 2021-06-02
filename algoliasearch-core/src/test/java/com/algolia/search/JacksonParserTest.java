package com.algolia.search;

import static com.algolia.search.models.synonyms.SynonymType.ALT_CORRECTION_1;
import static com.algolia.search.models.synonyms.SynonymType.ONE_WAY_SYNONYM;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.models.common.InnerQuery;
import com.algolia.search.models.indexing.Alternative;
import com.algolia.search.models.indexing.AroundPrecision;
import com.algolia.search.models.indexing.AroundRadius;
import com.algolia.search.models.indexing.PartialUpdateOperation;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.rules.Alternatives;
import com.algolia.search.models.rules.AutomaticFacetFilter;
import com.algolia.search.models.rules.Condition;
import com.algolia.search.models.rules.Consequence;
import com.algolia.search.models.rules.ConsequenceParams;
import com.algolia.search.models.rules.ConsequencePromote;
import com.algolia.search.models.rules.ConsequenceQuery;
import com.algolia.search.models.rules.Edit;
import com.algolia.search.models.rules.EditType;
import com.algolia.search.models.rules.Rule;
import com.algolia.search.models.rules.TimeRange;
import com.algolia.search.models.settings.Distinct;
import com.algolia.search.models.settings.IgnorePlurals;
import com.algolia.search.models.settings.IndexSettings;
import com.algolia.search.models.settings.RemoveStopWords;
import com.algolia.search.models.settings.TypoTolerance;
import com.algolia.search.models.synonyms.SynonymQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    assertThat(settings.getNumericAttributesForFiltering())
        .isEqualTo(Arrays.asList("attr1", "attr2"));
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
            .readValue("{\"ignorePlurals\":\"true\"}", IndexSettings.class)
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

  @ParameterizedTest
  @ValueSource(strings = {"facetFilters", "optionalFilters", "tagFilters", "numericFilters"})
  void testLegacyFiltersFormat(String input) throws IOException {

    // Testing "one string" legacy filters => should be converted to "ORED" nested filters
    // [["color:green","color:yellow"]]
    String stringFilters = String.format("{\"%s\":\"color:green,color:yellow\"}", input);

    assertOREDResult(
        extractFilters(
            Defaults.getObjectMapper().readValue(stringFilters, ConsequenceParams.class), input));

    // Testing "one array" legacy filters => should be converted to "ORED" nested filters
    // [["color:green","color:yellow"]]
    String arrayFilters = String.format("{\"%s\":[\"color:green\",\"color:yellow\"]}", input);
    assertOREDResult(
        extractFilters(
            Defaults.getObjectMapper().readValue(arrayFilters, ConsequenceParams.class), input));

    String nestedArrayFilters =
        String.format("{\"%s\":[[\"color:green\",\"color:yellow\"]]}", input);
    assertOREDResult(
        extractFilters(
            Defaults.getObjectMapper().readValue(nestedArrayFilters, ConsequenceParams.class),
            input));

    // Testing the latest format of filters i.e nested arrays
    String nestedAndedArrayFilters =
        String.format("{\"%s\":[[\"color:green\",\"color:yellow\"],[\"color:blue\"]]}", input);

    List<List<String>> deserialized =
        extractFilters(
            Defaults.getObjectMapper().readValue(nestedAndedArrayFilters, ConsequenceParams.class),
            input);

    assertThat(deserialized).hasSize(2);
    assertThat(deserialized.get(0)).hasSize(2);
    assertThat(deserialized.get(0).get(0)).containsSubsequence("color:green");
    assertThat(deserialized.get(0).get(1)).containsSubsequence("color:yellow");
    assertThat(deserialized.get(1)).hasSize(1);
    assertThat(deserialized.get(1).get(0)).containsSubsequence("color:blue");
  }

  List<List<String>> extractFilters(ConsequenceParams consequenceParams, String input) {
    List<List<String>> result = null;

    switch (input) {
      case "facetFilters":
        result = consequenceParams.getFacetFilters();
        break;
      case "optionalFilters":
        result = consequenceParams.getOptionalFilters();
        break;
      case "tagFilters":
        result = consequenceParams.getTagFilters();
        break;
      case "numericFilters":
        result = consequenceParams.getNumericFilters();
        break;
    }

    return result;
  }

  void assertOREDResult(List<List<String>> result) {
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).hasSize(2);
    assertThat(result.get(0)).containsSequence("color:green");
    assertThat(result.get(0)).containsSequence("color:yellow");
  }

  @Test
  void testFiltersJSONDeserializerNonRegression() throws IOException {

    // Finally, testing that the custom deserializer is not breaking current implementation
    Rule ruleWithFilters =
        new Rule()
            .setConsequence(
                new Consequence()
                    .setParams(
                        new ConsequenceParams()
                            .setOptionalFilters(
                                Collections.singletonList(Collections.singletonList("a:b")))
                            .setTagFilters(
                                Arrays.asList(
                                    Arrays.asList("a:b", "c:d"), Collections.singletonList("d:e")))
                            .setFacetFilters(
                                Arrays.asList(
                                    Collections.singletonList("a:b"),
                                    Collections.singletonList("c:d")))
                            .setNumericFilters(
                                Collections.singletonList(Collections.singletonList("a=100")))));

    // Json "sent" to the API
    String json = Defaults.getObjectMapper().writeValueAsString(ruleWithFilters);

    // Json "retrieved" by the client
    Rule retrievedRule = Defaults.getObjectMapper().readValue(json, Rule.class);

    assertThat(retrievedRule.getConsequence().getParams().getOptionalFilters()).hasSize(1);
    assertThat(retrievedRule.getConsequence().getParams().getOptionalFilters().get(0)).hasSize(1);
    assertThat(retrievedRule.getConsequence().getParams().getOptionalFilters().get(0).get(0))
        .containsSubsequence("a:b");

    assertThat(retrievedRule.getConsequence().getParams().getTagFilters()).hasSize(2);
    assertThat(retrievedRule.getConsequence().getParams().getTagFilters().get(0)).hasSize(2);
    assertThat(retrievedRule.getConsequence().getParams().getTagFilters().get(0).get(0))
        .containsSubsequence("a:b");
    assertThat(retrievedRule.getConsequence().getParams().getTagFilters().get(0).get(1))
        .containsSubsequence("c:d");
    assertThat(retrievedRule.getConsequence().getParams().getTagFilters().get(1)).hasSize(1);
    assertThat(retrievedRule.getConsequence().getParams().getTagFilters().get(1).get(0))
        .containsSubsequence("d:e");

    assertThat(retrievedRule.getConsequence().getParams().getFacetFilters()).hasSize(2);
    assertThat(retrievedRule.getConsequence().getParams().getFacetFilters().get(0)).hasSize(1);
    assertThat(retrievedRule.getConsequence().getParams().getFacetFilters().get(1)).hasSize(1);

    assertThat(retrievedRule.getConsequence().getParams().getNumericFilters()).hasSize(1);
    assertThat(retrievedRule.getConsequence().getParams().getNumericFilters().get(0)).hasSize(1);
    assertThat(retrievedRule.getConsequence().getParams().getNumericFilters().get(0))
        .containsSubsequence("a=100");
  }

  @Test
  void testConsequenceQueryAsString() throws IOException {
    String payload =
        "{\n"
            + "  \"objectID\": \"rule-2\",\n"
            + "  \"condition\": {\n"
            + "    \"pattern\": \"toto\",\n"
            + "    \"anchoring\": \"is\"\n"
            + "  },\n"
            + "  \"consequence\": {\n"
            + "    \"params\": {\n"
            + "        \"query\": \"tata\",\n"
            + "        \"facetFilters\": [[\"facet\"]]\n"
            + "    }\n"
            + "  }\n"
            + "}";

    Rule rule = Defaults.getObjectMapper().readValue(payload, Rule.class);

    assertThat(rule).isNotNull();
    assertThat(rule.getObjectID()).isEqualTo("rule-2");

    assertThat(rule.getCondition().getPattern()).isEqualTo("toto");
    assertThat(rule.getCondition().getAnchoring()).isEqualTo("is");

    assertThat(rule.getConsequence().getParams().getQuery()).isEqualTo("tata");
    assertThat(rule.getConsequence().getParams().getFacetFilters()).hasSize(1);
    assertThat(rule.getConsequence().getParams().getFacetFilters().get(0))
        .containsSubsequence("facet");
  }

  @Test
  void testConsequenceQueryAsRemove() throws IOException {
    String payload =
        "{\n"
            + "  \"objectID\": \"rule-2\",\n"
            + "  \"condition\": {\n"
            + "    \"pattern\": \"toto\",\n"
            + "    \"anchoring\": \"is\"\n"
            + "  },\n"
            + "  \"consequence\": {\n"
            + "    \"params\": {\n"
            + "        \"query\": {\"remove\":[\"lastname\",\"firstname\"]},\n"
            + "        \"facetFilters\": [[\"facet\"]]\n"
            + "    }\n"
            + "  }\n"
            + "}";

    Rule rule = Defaults.getObjectMapper().readValue(payload, Rule.class);

    assertThat(rule).isNotNull();
    assertThat(rule.getObjectID()).isEqualTo("rule-2");

    assertThat(rule.getCondition().getPattern()).isEqualTo("toto");
    assertThat(rule.getCondition().getAnchoring()).isEqualTo("is");

    assertThat(rule.getConsequence().getParams().getFacetFilters()).hasSize(1);
    assertThat(rule.getConsequence().getParams().getFacetFilters().get(0))
        .containsSubsequence("facet");

    List<Edit> edits = rule.getConsequence().getParams().getConsequenceQuery().getEdits();

    assertThat(edits.get(0).getType()).isEqualTo(EditType.REMOVE);
    assertThat(edits.get(0).getDelete()).isEqualTo("lastname");
    assertThat(edits.get(0).getInsert()).isNull();

    assertThat(edits.get(1).getType()).isEqualTo(EditType.REMOVE);
    assertThat(edits.get(1).getDelete()).isEqualTo("firstname");
    assertThat(edits.get(1).getInsert()).isNull();
  }

  @Test
  void testConsequenceQueryAsEdits() throws IOException {
    String payload =
        "{\n"
            + "  \"objectID\": \"rule-2\",\n"
            + "  \"condition\": {\n"
            + "    \"pattern\": \"toto\",\n"
            + "    \"anchoring\": \"is\"\n"
            + "  },\n"
            + "  \"consequence\": {\n"
            + "    \"params\": {\n"
            + "        \"query\":{\n"
            + "    \"edits\": [\n"
            + "       { \"type\": \"remove\", \"delete\": \"old\" },\n"
            + "       { \"type\": \"replace\", \"delete\": \"new\", \"insert\": \"newer\" }\n"
            + "    ]\n"
            + "},"
            + "        \"facetFilters\": [[\"facet\"]]\n"
            + "    }\n"
            + "  }\n"
            + "}";

    Rule rule = Defaults.getObjectMapper().readValue(payload, Rule.class);

    assertThat(rule).isNotNull();
    assertThat(rule.getObjectID()).isEqualTo("rule-2");

    assertThat(rule.getCondition().getPattern()).isEqualTo("toto");
    assertThat(rule.getCondition().getAnchoring()).isEqualTo("is");

    assertThat(rule.getConsequence().getParams().getFacetFilters()).hasSize(1);
    assertThat(rule.getConsequence().getParams().getFacetFilters().get(0))
        .containsSubsequence("facet");

    List<Edit> edits = rule.getConsequence().getParams().getConsequenceQuery().getEdits();

    assertThat(edits.get(0).getType()).isEqualTo(EditType.REMOVE);
    assertThat(edits.get(0).getDelete()).isEqualTo("old");
    assertThat(edits.get(0).getInsert()).isNull();

    assertThat(edits.get(1).getType()).isEqualTo(EditType.REPLACE);
    assertThat(edits.get(1).getDelete()).isEqualTo("new");
    assertThat(edits.get(1).getInsert()).isEqualTo("newer");
  }

  @Test
  void testConsequenceQueryEditsAndRemove() throws IOException {
    String payload =
        "{\n"
            + "  \"objectID\": \"rule-2\",\n"
            + "  \"condition\": {\n"
            + "    \"pattern\": \"toto\",\n"
            + "    \"anchoring\": \"is\"\n"
            + "  },\n"
            + "  \"consequence\": {\n"
            + "    \"params\": {\n"
            + "        \"query\":{\"remove\": [\"term1\", \"term2\"], \"edits\": [{\"type\": \"remove\", \"delete\": \"term3\"}]},"
            + "        \"facetFilters\": [[\"facet\"]]\n"
            + "    }\n"
            + "  }\n"
            + "}";

    Rule rule = Defaults.getObjectMapper().readValue(payload, Rule.class);

    assertThat(rule).isNotNull();
    assertThat(rule.getObjectID()).isEqualTo("rule-2");
    assertThat(rule.getCondition().getPattern()).isEqualTo("toto");
    assertThat(rule.getCondition().getAnchoring()).isEqualTo("is");
    assertThat(rule.getConsequence().getParams().getFacetFilters()).hasSize(1);
    assertThat(rule.getConsequence().getParams().getFacetFilters().get(0))
        .containsSubsequence("facet");

    List<Edit> edits = rule.getConsequence().getParams().getConsequenceQuery().getEdits();

    assertThat(edits.get(0).getType()).isEqualTo(EditType.REMOVE);
    assertThat(edits.get(0).getDelete()).isEqualTo("term1");
    assertThat(edits.get(0).getInsert()).isNull();

    assertThat(edits.get(1).getType()).isEqualTo(EditType.REMOVE);
    assertThat(edits.get(1).getDelete()).isEqualTo("term2");
    assertThat(edits.get(1).getInsert()).isNull();

    assertThat(edits.get(2).getType()).isEqualTo(EditType.REMOVE);
    assertThat(edits.get(2).getDelete()).isEqualTo("term3");
    assertThat(edits.get(2).getInsert()).isNull();
  }

  @Test
  void consequenceQueryTestQueryStringOverride() throws IOException {

    /*
     The consequence query edits will override regular "query" - both can't be set at the same time
     https://www.algolia.com/doc/api-reference/api-methods/save-rule/#method-param-query
    */
    Consequence consequence =
        new Consequence()
            .setParams(
                new ConsequenceParams()
                    .setQuery("test")
                    .setConsequenceQuery(
                        new ConsequenceQuery()
                            .setEdits(
                                Arrays.asList(
                                    Edit.createDelete("mobile"),
                                    Edit.createReplace("phone", "iphone")))));

    Rule rule =
        new Rule()
            .setObjectID("rule-1")
            .setCondition(new Condition().setAnchoring("is").setPattern("word"))
            .setConsequence(consequence);

    String payload = Defaults.getObjectMapper().writeValueAsString(rule);

    Rule deserializedRule = Defaults.getObjectMapper().readValue(payload, Rule.class);

    List<Edit> edits =
        deserializedRule.getConsequence().getParams().getConsequenceQuery().getEdits();

    assertThat(edits.get(0).getType()).isEqualTo(EditType.REMOVE);
    assertThat(edits.get(0).getDelete()).isEqualTo("mobile");
    assertThat(edits.get(0).getInsert()).isNull();

    assertThat(edits.get(1).getType()).isEqualTo(EditType.REPLACE);
    assertThat(edits.get(1).getDelete()).isEqualTo("phone");
    assertThat(edits.get(1).getInsert()).isEqualTo("iphone");
  }

  @Test
  void ruleSerializationCycle() throws IOException {

    {
      Consequence consequenceToBatch =
          new Consequence()
              .setFilterPromotes(false)
              .setParams(
                  new ConsequenceParams()
                      .setConsequenceQuery(
                          new ConsequenceQuery()
                              .setEdits(
                                  Arrays.asList(
                                      Edit.createDelete("mobile"),
                                      Edit.createReplace("phone", "iphone")))));

      Rule rule =
          new Rule()
              .setObjectID("query_edits")
              .setCondition(
                  new Condition()
                      .setAnchoring("is")
                      .setPattern("mobile phone")
                      .setAlternatives(Alternatives.of(true)))
              .setConsequence(consequenceToBatch);

      String payload = Defaults.getObjectMapper().writeValueAsString(rule);

      Rule deserializedRule = Defaults.getObjectMapper().readValue(payload, Rule.class);

      assertThat(deserializedRule).isEqualToComparingFieldByFieldRecursively(rule);
    }

    {
      Consequence consequenceToBatch =
          new Consequence()
              .setFilterPromotes(false)
              .setParams(new ConsequenceParams().setQuery("test"));

      Rule rule =
          new Rule()
              .setObjectID("query_edits")
              .setCondition(
                  new Condition()
                      .setAnchoring("is")
                      .setPattern("mobile phone")
                      .setAlternatives(Alternatives.of(true)))
              .setConsequence(consequenceToBatch);

      String payload = Defaults.getObjectMapper().writeValueAsString(rule);

      Rule deserializedRule = Defaults.getObjectMapper().readValue(payload, Rule.class);

      assertThat(deserializedRule).isEqualToComparingFieldByFieldRecursively(rule);
    }
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
    assertThat(query.toParam()).isEqualTo("query=&distinct=0");
  }

  @Test
  void queryWithMultipleParams() {
    Query query =
        new Query("é®„")
            .setTagFilters(Collections.singletonList(Collections.singletonList("(attribute)")));

    assertThat(query.toParam())
        .isEqualTo("tagFilters=%5B%5B%22%28attribute%29%22%5D%5D&query=%C3%A9%C2%AE%E2%80%9E");

    Query query2 =
        new Query("")
            .setTagFilters(Arrays.asList(Arrays.asList("a", "b"), Collections.singletonList("c")));

    assertThat(query2.toParam())
        .isEqualTo("tagFilters=%5B%5B%22a%22%2C%22b%22%5D%2C%5B%22c%22%5D%5D&query=");
  }

  @Test
  void queryWithList() {

    Query query = new Query("").setFacets(Collections.singletonList("(attribute)"));

    assertThat(query.toParam()).isEqualTo("query=&facets=%28attribute%29");

    Query query2 = new Query("").setFacets(Arrays.asList("a", "b"));

    assertThat(query2.toParam()).isEqualTo("query=&facets=a%2Cb");

    Query query3 =
        new Query("").setRestrictSearchableAttributes(Collections.singletonList("attr1"));

    assertThat(query3.toParam()).isEqualTo("query=&restrictSearchableAttributes=attr1");
  }

  @Test
  void queryWithBooleanParams() {
    Query query = new Query("").setAroundLatLngViaIP(true);
    assertThat(query.toParam()).contains("aroundLatLngViaIP=true");
  }

  @Test
  void queryWithNestedLists() {

    // Expected: query=&facetFilters=[["facet1:true"],["facet2:true"]]
    Query query =
        new Query("")
            .setFacetFilters(
                Arrays.asList(
                    Collections.singletonList("facet1:true"),
                    Collections.singletonList("facet2:true")));

    assertThat(query.toParam())
        .isEqualTo(
            "facetFilters=%5B%5B%22facet1%3Atrue%22%5D%2C%5B%22facet2%3Atrue%22%5D%5D&query=");

    // Expected: query=&facetFilters=[["facet1:true","facet2:true"]]
    Query query2 =
        new Query("")
            .setFacetFilters(
                Collections.singletonList(Arrays.asList("facet1:true", "facet2:true")));

    assertThat(query2.toParam())
        .isEqualTo("facetFilters=%5B%5B%22facet1%3Atrue%22%2C%22facet2%3Atrue%22%5D%5D&query=");

    // Expected: query=&facetFilters=[["facet1:true","facet2:true"],["facet3:true"]]
    Query query3 =
        new Query("")
            .setFacetFilters(
                Arrays.asList(
                    Arrays.asList("facet1:true", "facet2:true"),
                    Collections.singletonList("facet3:true")));

    assertThat(query3.toParam())
        .isEqualTo(
            "facetFilters=%5B%5B%22facet1%3Atrue%22%2C%22facet2%3Atrue%22%5D%2C%5B%22facet3%3Atrue%22%5D%5D&query=");

    // Expected: query=&facetFilters=[["facet1:true"]]
    Query query4 =
        new Query("")
            .setFacetFilters(Collections.singletonList(Collections.singletonList("facet1:true")));

    assertThat(query4.toParam()).isEqualTo("facetFilters=%5B%5B%22facet1%3Atrue%22%5D%5D&query=");

    // Expected: insideBoundingBox=[[47.3165,4.9665,47.3424,5.0201],[40.9234,2.1185,38.643,1.9916]]
    Query query5 =
        new Query("")
            .setInsideBoundingBox(
                Arrays.asList(
                    Arrays.asList(47.3165f, 4.9665f, 47.3424f, 5.0201f),
                    Arrays.asList(40.9234f, 2.1185f, 38.643f, 1.9916f)));

    assertThat(query5.toParam())
        .isEqualTo(
            "query=&insideBoundingBox=%5B%5B47.3165%2C4.9665%2C47.3424%2C5.0201%5D%2C%5B40.9234%2C2.1185%2C38.643%2C1.9916%5D%5D");

    // Expected:insideBoundingBox=[[47.3165,4.9665,47.3424,5.0201]]
    Query query6 =
        new Query("")
            .setInsideBoundingBox(
                Collections.singletonList(Arrays.asList(47.3165f, 4.9665f, 47.3424f, 5.0201f)));

    assertThat(query6.toParam())
        .isEqualTo("query=&insideBoundingBox=%5B%5B47.3165%2C4.9665%2C47.3424%2C5.0201%5D%5D");
  }

  @Test
  void queryWithAroundPrecision() throws JsonProcessingException {
    List<AroundPrecision> aroundPrecisionFar =
        Arrays.asList(new AroundPrecision(0, 10000), new AroundPrecision(0, 100000));
    Query query = new Query("barcelona").setAroundPrecision(aroundPrecisionFar);
    assertThat(query.toParam())
        .isEqualTo(
            "aroundPrecision=%5B%7B%22from%22%3A0%2C%22value%22%3A10000%7D%2C%7B%22from%22%3A0%2C%22value%22%3A100000%7D%5D&query=barcelona");
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

  @Test
  void queryWithCustomParameters() throws JsonProcessingException {
    Query query = new Query("algolia").setCustomParameter("newParameter", 10);
    String serialized = Defaults.getObjectMapper().writeValueAsString(query);
    assertThat(serialized).isEqualTo("{\"query\":\"algolia\",\"newParameter\":10}");
  }

  @Test
  void settingsWithCustomSettings() throws JsonProcessingException {
    IndexSettings settings =
        new IndexSettings().setEnableRules(true).setCustomSetting("newSettings", true);
    String serialized = Defaults.getObjectMapper().writeValueAsString(settings);
    assertThat(serialized).isEqualTo("{\"enableRules\":true,\"newSettings\":true}");
  }

  private IndexSettings serializeDeserialize(IndexSettings obj) throws IOException {
    String serialized = Defaults.getObjectMapper().writeValueAsString(obj);
    return Defaults.getObjectMapper()
        .readValue(
            serialized,
            Defaults.getObjectMapper().getTypeFactory().constructType(IndexSettings.class));
  }

  @Test
  void searchSynonymsQueryWithType() throws JsonProcessingException {
    SynonymQuery query =
        new SynonymQuery("q").setType(Arrays.asList(ONE_WAY_SYNONYM, ALT_CORRECTION_1));
    String serialized = Defaults.getObjectMapper().writeValueAsString(query);
    assertThat(serialized).isEqualTo("{\"query\":\"q\",\"type\":\"oneWaySynonym,altCorrection1\"}");
  }

  @Test
  void partialUpdateOperation_increment() throws JsonProcessingException {
    RecordWithPartialUpdate record = new RecordWithPartialUpdate();
    record.setObjectID("myID");
    record.setUpdate(PartialUpdateOperation.increment(2));
    String serialized = Defaults.getObjectMapper().writeValueAsString(record);
    assertThat(serialized)
        .isEqualTo("{\"objectID\":\"myID\",\"update\":{\"_operation\":\"Increment\",\"value\":2}}");
  }

  @Test
  void partialUpdateOperation_increment_from() throws JsonProcessingException {
    RecordWithPartialUpdate record = new RecordWithPartialUpdate();
    record.setObjectID("myID");
    record.setUpdate(PartialUpdateOperation.incrementFrom(2));
    String serialized = Defaults.getObjectMapper().writeValueAsString(record);
    assertThat(serialized)
        .isEqualTo(
            "{\"objectID\":\"myID\",\"update\":{\"_operation\":\"IncrementFrom\",\"value\":2}}");
  }

  @Test
  void partialUpdateOperation_increment_set() throws JsonProcessingException {
    RecordWithPartialUpdate record = new RecordWithPartialUpdate();
    record.setObjectID("myID");
    record.setUpdate(PartialUpdateOperation.incrementSet(2));
    String serialized = Defaults.getObjectMapper().writeValueAsString(record);
    assertThat(serialized)
        .isEqualTo(
            "{\"objectID\":\"myID\",\"update\":{\"_operation\":\"IncrementSet\",\"value\":2}}");
  }

  @Test
  void partialUpdateOperation_decrement() throws JsonProcessingException {
    RecordWithPartialUpdate record = new RecordWithPartialUpdate();
    record.setObjectID("myID");
    record.setUpdate(PartialUpdateOperation.decrement(2));
    String serialized = Defaults.getObjectMapper().writeValueAsString(record);
    assertThat(serialized)
        .isEqualTo("{\"objectID\":\"myID\",\"update\":{\"_operation\":\"Decrement\",\"value\":2}}");
  }

  @Test
  void partialUpdateOperation_add() throws JsonProcessingException {
    RecordWithPartialUpdate record = new RecordWithPartialUpdate();
    record.setObjectID("myID");
    record.setUpdate(PartialUpdateOperation.add("something"));
    String serialized = Defaults.getObjectMapper().writeValueAsString(record);
    assertThat(serialized)
        .isEqualTo(
            "{\"objectID\":\"myID\",\"update\":{\"_operation\":\"Add\",\"value\":\"something\"}}");
  }

  @Test
  void partialUpdateOperation_addUnique() throws JsonProcessingException {
    RecordWithPartialUpdate record = new RecordWithPartialUpdate();
    record.setObjectID("myID");
    record.setUpdate(PartialUpdateOperation.addUnique("something"));
    String serialized = Defaults.getObjectMapper().writeValueAsString(record);
    assertThat(serialized)
        .isEqualTo(
            "{\"objectID\":\"myID\",\"update\":{\"_operation\":\"AddUnique\",\"value\":\"something\"}}");
  }

  @Test
  void partialUpdateOperation_remove() throws JsonProcessingException {
    RecordWithPartialUpdate record = new RecordWithPartialUpdate();
    record.setObjectID("myID");
    record.setUpdate(PartialUpdateOperation.remove("something"));
    String serialized = Defaults.getObjectMapper().writeValueAsString(record);
    assertThat(serialized)
        .isEqualTo(
            "{\"objectID\":\"myID\",\"update\":{\"_operation\":\"Remove\",\"value\":\"something\"}}");
  }

  @Test
  void innerQuery() throws IOException {
    InnerQuery innerQuery =
        new InnerQuery("index").setOffset(0).setUserToken("token").setQueryID("queryID");

    String json = Defaults.getObjectMapper().writeValueAsString(innerQuery);
    InnerQuery retrievedInnerQuery = Defaults.getObjectMapper().readValue(json, InnerQuery.class);

    assertThat(json)
        .isEqualTo(
            "{\"indexName\":\"index\",\"offset\":0,\"query_id\":\"queryID\",\"user_token\":\"token\"}");

    assertThat(retrievedInnerQuery.getIndexName()).isNotNull();
  }

  @Test
  void enablePersonalization() throws IOException {
    IndexSettings settings = new IndexSettings().setEnablePersonalization(true);
    IndexSettings result = serializeDeserialize(settings);
    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getEnablePersonalization()).isEqualTo(true);
  }

  @Test
  void alternativeType() throws IOException {
    String typeString = "{\"type\":\"type1,type2\"}";
    Alternative altString = Defaults.getObjectMapper().readValue(typeString, Alternative.class);
    assertThat(altString.getType()).isEqualTo("type1,type2");

    String typeList = "{\"type\":[\"type1\",\"type2\"]}";
    Alternative altList = Defaults.getObjectMapper().readValue(typeList, Alternative.class);
    assertThat(altList.getType()).isEqualTo("type1,type2");
  }

  @Test
  void testConsequencePromotion() throws IOException {
    Consequence consequence =
        new Consequence()
            .setPromote(
                Arrays.asList(
                    new ConsequencePromote().setObjectID("a").setPosition(0),
                    new ConsequencePromote().setObjectIDs(Arrays.asList("b", "c")).setPosition(1)));

    String json = Defaults.getObjectMapper().writeValueAsString(consequence);
    Consequence retrieveConsequence = Defaults.getObjectMapper().readValue(json, Consequence.class);
    List<ConsequencePromote> promote = retrieveConsequence.getPromote();

    assertThat(promote).hasSize(2);
    // Single ObjectID case
    assertThat(promote.get(0).getObjectID()).isEqualTo("a");
    assertThat(promote.get(0).getObjectIDs()).isNull();
    // Multiple ObjectIDs case
    assertThat(promote.get(1).getObjectID()).isNull();
    assertThat(promote.get(1).getObjectIDs()).isEqualTo(Arrays.asList("b", "c"));
  }

  @Test
  void rulesValidityTimeRange() throws IOException {
    ZoneOffset offset = ZoneOffset.of("+05:30");
    // Nanoseconds precisions will be lost and should not be considered.
    TimeRange timerange =
        new TimeRange(
            OffsetDateTime.of(2021, 5, 1, 0, 0, 0, 0, offset),
            OffsetDateTime.of(2021, 8, 30, 0, 23, 59, 0, offset));

    String json = Defaults.getObjectMapper().writeValueAsString(timerange);
    assertThat(json).isEqualTo("{\"from\":1619807400,\"until\":1630263239}");

    TimeRange retrieveTimeRange = Defaults.getObjectMapper().readValue(json, TimeRange.class);
    assertThat(timerange.getFrom()).isEqualTo(retrieveTimeRange.getFrom());
    assertThat(timerange.getUntil()).isEqualTo(retrieveTimeRange.getUntil());
  }
}
