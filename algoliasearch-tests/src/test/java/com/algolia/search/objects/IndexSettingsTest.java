package com.algolia.search.objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;

import com.algolia.search.Defaults;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;

public class IndexSettingsTest {

  private ObjectMapper objectMapper = Defaults.DEFAULT_OBJECT_MAPPER;

  private IndexSettings serializeDeserialize(IndexSettings obj) throws IOException {
    String serialized = objectMapper.writeValueAsString(obj);
    return objectMapper.readValue(
        serialized, objectMapper.getTypeFactory().constructType(IndexSettings.class));
  }

  @Test
  public void typoToleranceBoolean() throws IOException {
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
  public void typoToleranceString() throws IOException {
    IndexSettings settings = new IndexSettings().setTypoTolerance(TypoTolerance.of("min"));
    IndexSettings result = serializeDeserialize(settings);

    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getTypoTolerance()).isEqualTo(TypoTolerance.of("min"));
  }

  @Test
  public void removeStopWordsBoolean() throws IOException {
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
  public void removeStopWordsList() throws IOException {
    IndexSettings settings =
        new IndexSettings().setRemoveStopWords(RemoveStopWords.of(Arrays.asList("a", "b")));
    IndexSettings result = serializeDeserialize(settings);

    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getRemoveStopWords()).isEqualTo(RemoveStopWords.of(Arrays.asList("a", "b")));
  }

  @Test
  public void customSettings() throws IOException {
    IndexSettings settings = new IndexSettings().setCustomSetting("a", "b");
    IndexSettings result = serializeDeserialize(settings);

    assertThat(result).isEqualToComparingFieldByField(settings);
    assertThat(result.getCustomSettings()).containsExactly(entry("a", "b"));
  }
}
