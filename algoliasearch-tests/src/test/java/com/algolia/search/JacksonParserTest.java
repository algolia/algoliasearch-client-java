package com.algolia.search;

import static com.algolia.search.Defaults.DEFAULT_OBJECT_MAPPER;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.models.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class JacksonParserTest {

  @Test
  void serializeDistinct() throws IOException {
    IndexSettings settings;

    settings = new IndexSettings().setDistinct(Distinct.of(true));
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings)).isEqualTo("{\"distinct\":true}");

    settings = new IndexSettings().setDistinct(Distinct.of(1));
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings)).isEqualTo("{\"distinct\":1}");
  }

  @Test
  void deserializeDistinct() throws IOException {
    Distinct distinct;

    distinct =
        DEFAULT_OBJECT_MAPPER.readValue("{\"distinct\":true}", IndexSettings.class).getDistinct();
    assertThat(distinct).isEqualTo(Distinct.of(true));

    distinct =
        DEFAULT_OBJECT_MAPPER.readValue("{\"distinct\":1}", IndexSettings.class).getDistinct();
    assertThat(distinct).isEqualTo(Distinct.of(1));
  }

  @Test
  void serializeRemoveStopWords() throws IOException {
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
  void deserializeRemoveStopWords() throws IOException {
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
  void serializeIgnorePlurals() throws IOException {
    IndexSettings settings;

    settings = new IndexSettings().setIgnorePlurals(true);
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings))
        .isEqualTo("{\"ignorePlurals\":true}");

    settings =
        new IndexSettings().setIgnorePlurals(IgnorePlurals.of(Collections.singletonList("en")));
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings))
        .isEqualTo("{\"ignorePlurals\":[\"en\"]}");

    settings = new IndexSettings().setIgnorePlurals(Arrays.asList("en", "fr"));
    assertThat(DEFAULT_OBJECT_MAPPER.writeValueAsString(settings))
        .isEqualTo("{\"ignorePlurals\":[\"en\",\"fr\"]}");
  }

  @Test
  void deserializeIgnorePlurals() throws IOException {
    IgnorePlurals ignorePlurals;

    ignorePlurals =
        DEFAULT_OBJECT_MAPPER
            .readValue("{\"ignorePlurals\":true}", IndexSettings.class)
            .getIgnorePlurals();
    assertThat(ignorePlurals).isEqualTo(IgnorePlurals.of(true));

    ignorePlurals =
        DEFAULT_OBJECT_MAPPER
            .readValue("{\"ignorePlurals\":[\"en\"]}", IndexSettings.class)
            .getIgnorePlurals();
    assertThat(ignorePlurals).isEqualTo(IgnorePlurals.of(Collections.singletonList("en")));

    ignorePlurals =
        DEFAULT_OBJECT_MAPPER
            .readValue("{\"ignorePlurals\":[\"en\",\"fr\"]}", IndexSettings.class)
            .getIgnorePlurals();
    assertThat(ignorePlurals).isEqualTo(IgnorePlurals.of(Arrays.asList("en", "fr")));
  }
}
