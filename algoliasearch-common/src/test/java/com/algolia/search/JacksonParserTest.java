package com.algolia.search;

import com.algolia.search.inputs.synonym.*;
import org.junit.Test;

import java.io.IOException;

import static com.algolia.search.Defaults.DEFAULT_OBJECT_MAPPER;
import static org.assertj.core.api.Assertions.assertThat;

public class JacksonParserTest {

  @Test
  public void shouldDeserializeSynonyms() throws IOException {
    AbstractSynonym synonym;

    synonym = DEFAULT_OBJECT_MAPPER.readValue("{\"type\":\"altCorrection1\",\"objectID\":\"synonymID\",\"corrections\":[\"1\", \"2\"],\"word\":\"word\"}", AbstractSynonym.class);
    assertThat(synonym).isInstanceOf(AltCorrection1.class);

    synonym = DEFAULT_OBJECT_MAPPER.readValue("{\"type\":\"altCorrection2\",\"objectID\":\"synonymID\",\"corrections\":[\"1\", \"2\"],\"word\":\"word\"}", AbstractSynonym.class);
    assertThat(synonym).isInstanceOf(AltCorrection2.class);

    synonym = DEFAULT_OBJECT_MAPPER.readValue("{\"type\":\"oneWaySynonym\",\"objectID\":\"synonymID\",\"synonyms\":[\"1\", \"2\"],\"input\":\"input\"}", AbstractSynonym.class);
    assertThat(synonym).isInstanceOf(OneWaySynonym.class);

    synonym = DEFAULT_OBJECT_MAPPER.readValue("{\"type\":\"placeholder\",\"objectID\":\"synonymID\",\"replacements\":[\"1\", \"2\"],\"placeholder\":\"placeholder\"}", AbstractSynonym.class);
    assertThat(synonym).isInstanceOf(Placeholder.class);

    synonym = DEFAULT_OBJECT_MAPPER.readValue("{\"type\":\"synonym\",\"objectID\":\"synonymID\",\"synonyms\":[\"1\", \"2\"]}", AbstractSynonym.class);
    assertThat(synonym).isInstanceOf(Synonym.class);
  }

}