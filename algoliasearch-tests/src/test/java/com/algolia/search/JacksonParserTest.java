package com.algolia.search;

import static com.algolia.search.Defaults.DEFAULT_OBJECT_MAPPER;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.inputs.synonym.*;
import com.algolia.search.objects.Distinct;
import com.algolia.search.objects.IgnorePlurals;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.RemoveStopWords;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

public class JacksonParserTest {

  @Test
  public void shouldDeserializeSynonyms() throws IOException {
    AbstractSynonym synonym;

    synonym =
        DEFAULT_OBJECT_MAPPER.readValue(
            "{\"type\":\"altCorrection1\",\"objectID\":\"synonymID\",\"corrections\":[\"1\", \"2\"],\"word\":\"word\"}",
            AbstractSynonym.class);
    assertThat(synonym).isInstanceOf(AltCorrection1.class);

    synonym =
        DEFAULT_OBJECT_MAPPER.readValue(
            "{\"type\":\"altCorrection2\",\"objectID\":\"synonymID\",\"corrections\":[\"1\", \"2\"],\"word\":\"word\"}",
            AbstractSynonym.class);
    assertThat(synonym).isInstanceOf(AltCorrection2.class);

    synonym =
        DEFAULT_OBJECT_MAPPER.readValue(
            "{\"type\":\"oneWaySynonym\",\"objectID\":\"synonymID\",\"synonyms\":[\"1\", \"2\"],\"input\":\"input\"}",
            AbstractSynonym.class);
    assertThat(synonym).isInstanceOf(OneWaySynonym.class);

    synonym =
        DEFAULT_OBJECT_MAPPER.readValue(
            "{\"type\":\"placeholder\",\"objectID\":\"synonymID\",\"replacements\":[\"1\", \"2\"],\"placeholder\":\"placeholder\"}",
            AbstractSynonym.class);
    assertThat(synonym).isInstanceOf(Placeholder.class);

    synonym =
        DEFAULT_OBJECT_MAPPER.readValue(
            "{\"type\":\"synonym\",\"objectID\":\"synonymID\",\"synonyms\":[\"1\", \"2\"]}",
            AbstractSynonym.class);
    assertThat(synonym).isInstanceOf(Synonym.class);
  }

  @Test
  public void serializeDistinct() throws IOException {
    IndexSettings settings;

    settings = new IndexSettings().setDistinct(Distinct.of(true));
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings)).isEqualTo("{\"distinct\":true}");

    settings = new IndexSettings().setDistinct(Distinct.of(1));
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings)).isEqualTo("{\"distinct\":1}");
  }

  @Test
  public void deserializeDistinct() throws IOException {
    Distinct distinct;

    distinct =
        DEFAULT_OBJECT_MAPPER.readValue("{\"distinct\":true}", IndexSettings.class).getDistinct();
    assertThat(distinct).isEqualTo(Distinct.of(true));

    distinct =
        DEFAULT_OBJECT_MAPPER.readValue("{\"distinct\":1}", IndexSettings.class).getDistinct();
    assertThat(distinct).isEqualTo(Distinct.of(1));
  }

  @Test
  public void serializeRemoveStopWords() throws IOException {
    IndexSettings settings;

    settings = new IndexSettings().setRemoveStopWords(true);
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings))
        .isEqualTo("{\"removeStopWords\":true}");

    settings = new IndexSettings().setRemoveStopWords(Arrays.asList("a", "b"));
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings))
        .isEqualTo("{\"removeStopWords\":\"a,b\"}");

    settings = new IndexSettings().setRemoveStopWords(RemoveStopWords.of(false));
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings))
        .isEqualTo("{\"removeStopWords\":false}");
  }

  @Test
  public void deserializeRemoveStopWords() throws IOException {
    RemoveStopWords removeStopWords;

    removeStopWords =
        DEFAULT_OBJECT_MAPPER
            .readValue("{\"removeStopWords\":true}", IndexSettings.class)
            .getRemoveStopWords();
    assertThat(removeStopWords).isEqualTo(RemoveStopWords.of(true));

    removeStopWords =
        DEFAULT_OBJECT_MAPPER
            .readValue("{\"removeStopWords\":\"a,b\"}", IndexSettings.class)
            .getRemoveStopWords();
    assertThat(removeStopWords).isEqualTo(RemoveStopWords.of(Arrays.asList("a", "b")));
  }

  @Test
  public void serializeIgnorePlurals() throws IOException {
    IndexSettings settings;

    settings = new IndexSettings().setIgnorePlurals(true);
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings))
        .isEqualTo("{\"ignorePlurals\":true}");

    settings =
        new IndexSettings().setIgnorePlurals(IgnorePlurals.of(Collections.singletonList("en")));
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings))
        .isEqualTo("{\"ignorePlurals\":\"en\"}");

    settings = new IndexSettings().setIgnorePlurals(Arrays.asList("en", "fr"));
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings))
        .isEqualTo("{\"ignorePlurals\":\"en,fr\"}");
  }

  @Test
  public void deserializeIgnorePlurals() throws IOException {
    IgnorePlurals ignorePlurals;

    ignorePlurals =
        DEFAULT_OBJECT_MAPPER
            .readValue("{\"ignorePlurals\":true}", IndexSettings.class)
            .getIgnorePlurals();
    assertThat(ignorePlurals).isEqualTo(IgnorePlurals.of(true));

    ignorePlurals =
        DEFAULT_OBJECT_MAPPER
            .readValue("{\"ignorePlurals\":\"en\"}", IndexSettings.class)
            .getIgnorePlurals();
    assertThat(ignorePlurals).isEqualTo(IgnorePlurals.of(Collections.singletonList("en")));

    ignorePlurals =
        DEFAULT_OBJECT_MAPPER
            .readValue("{\"ignorePlurals\":\"en,fr\"}", IndexSettings.class)
            .getIgnorePlurals();
    assertThat(ignorePlurals).isEqualTo(IgnorePlurals.of(Arrays.asList("en", "fr")));
  }
}
