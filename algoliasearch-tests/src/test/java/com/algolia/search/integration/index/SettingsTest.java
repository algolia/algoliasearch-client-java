package com.algolia.search.integration.index;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.algolia.search.clients.SearchIndex;
import com.algolia.search.integration.AlgoliaBaseIntegrationTest;
import com.algolia.search.integration.AlgoliaObject;
import com.algolia.search.models.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

class SettingsTest extends AlgoliaBaseIntegrationTest {

  private SearchIndex<AlgoliaObject> index;
  private String indexName;

  void init() {
    indexName = getTestIndexName("settings");
    index = searchClient.initIndex(indexName, AlgoliaObject.class);
  }

  @Test
  void testSettings() {
    init();

    CompletableFuture<BatchIndexingResponse> saveObjectFuture =
        index.saveObjectAsync(new AlgoliaObject("one", "value"));

    IndexSettings settings = new IndexSettings();

    // Attributes
    settings.setSearchableAttributes(
        Arrays.asList(
            "attribute1",
            "attribute2",
            "attribute3",
            "ordered(attribute4)",
            "unordered(attribute5)"));
    settings.setAttributesForFaceting(
        Arrays.asList("attribute1", "filterOnly(attribute2)", "searchable(attribute3)"));
    settings.setUnretrievableAttributes(Arrays.asList("attribute1", "attribute2"));
    settings.setAttributesToRetrieve(Arrays.asList("attribute3", "attribute4"));

    // Ranking
    settings.setRanking(
        Arrays.asList(
            "asc(attribute1)",
            "desc(attribute2)",
            "attribute",
            "custom",
            "exact",
            "filters",
            "geo",
            "proximity",
            "typo",
            "words"));
    settings.setCustomRanking(Arrays.asList("asc(attribute1)", "desc(attribute1)"));
    settings.setReplicas(Arrays.asList(indexName + "_replica1", indexName + "_replica2"));

    // Faceting
    settings.setMaxValuesPerFacet(100);
    settings.setSortFacetValuesBy("count");

    // Highligthing/snippeting
    settings.setAttributesToHighlight(Arrays.asList("attribute1", "attribute2"));
    settings.setAttributesToSnippet(Arrays.asList("attribute1:10", "attribute2:8"));
    settings.setHighlightPreTag("<strong>");
    settings.setHighlightPostTag("</strong>");
    settings.setSnippetEllipsisText("and so on.");
    settings.setRestrictHighlightAndSnippetArrays(true);

    // Pagination
    settings.setHitsPerPage(42);
    settings.setPaginationLimitedTo(43);

    // Typos
    settings.setMinWordSizefor1Typo(2);
    settings.setMinWordSizefor2Typos(6);
    settings.setTypoTolerance(TypoTolerance.of(false));
    settings.setAllowTyposOnNumericTokens(false);
    settings.setIgnorePlurals(true);
    settings.setDisableTypoToleranceOnAttributes(Arrays.asList("attribute1", "attribute2"));
    settings.setDisableTypoToleranceOnWords(Arrays.asList("word1", "word2"));
    settings.setSeparatorsToIndex("()[]");

    // Query
    settings.setQueryType("prefixNone");
    settings.setRemoveWordsIfNoResults("allOptional");
    settings.setAdvancedSyntax(true);
    settings.setOptionalWords(Arrays.asList("word1", "word2"));
    settings.setRemoveStopWords(true);
    settings.setDisablePrefixOnAttributes(Arrays.asList("attribute1", "attribute2"));
    settings.setDisableExactOnAttributes(Arrays.asList("attribute1", "attribute2"));
    settings.setExactOnSingleWordQuery("word");

    // Query rules
    settings.setEnableRules(true);

    // Performance
    settings.setNumericAttributesForFiltering(Arrays.asList("attributes1", "attribute2"));
    settings.setAllowCompressionOfIntegerArray(true);

    // Advanced
    settings.setAttributeForDistinct("attribute1");
    settings.setDistinct(2);
    settings.setReplaceSynonymsInHighlight(false);
    settings.setMinProximity(7);
    settings.setResponseFields(Arrays.asList("hits", "hitsPerPage"));
    settings.setMaxFacetHits(100);
    settings.setCamelCaseAttributes(Arrays.asList("attribute1", "attribute2"));
    settings.setDecompoundedAttributes(
        new HashMap<String, List<String>>() {
          {
            put("de", Arrays.asList("attribute1", "attribute2"));
            put("fi", Collections.singletonList("attribute3"));
          }
        });
    settings.setKeepDiacriticsOnCharacters("øé");

    // Wait for the save object task to finish on the API Side
    saveObjectFuture.join().waitTask();
    CompletableFuture<SetSettingsResponse> saveSettingsFuture = index.setSettingsAsync(settings);
    saveSettingsFuture.join().waitTask();

    // Get and check that the settings are the same
    CompletableFuture<IndexSettings> getSettingsFuture = index.getSettingsAsync();
    IndexSettings settingsAfterSave = getSettingsFuture.join();
    assertThat(settings)
        .usingRecursiveComparison()
        .ignoringFields("alternativesAsExact", "typoTolerance")
        .isEqualTo(settingsAfterSave);

    // assertThat(settingsAfterSave.getTypoTolerance()).isEqualTo(TypoTolerance.of(false));
    // Check serializer failure (not failing on previous version)

    // Set new values
    settings.setTypoTolerance(TypoTolerance.of("min"));
    settings.setIgnorePlurals(Arrays.asList("en", "fr"));
    // settings.setRemoveStopWords(Arrays.asList("en", "fr") Improve serializer (not failing on
    // previous version)
    settings.setDistinct(true);

    // Save new settings
    CompletableFuture<SetSettingsResponse> saveSettingsAfterChangesFuture =
        index.setSettingsAsync(settings);
    saveSettingsAfterChangesFuture.join().waitTask();

    // Get and check that the settings are the same
    CompletableFuture<IndexSettings> getSettingsAfterChangesFuture = index.getSettingsAsync();
    IndexSettings settingsAfterChanges = getSettingsAfterChangesFuture.join();
    assertThat(settings)
        .usingRecursiveComparison()
        .ignoringFields("alternativesAsExact", "typoTolerance")
        .isEqualTo(settingsAfterChanges);

    assertThat(settingsAfterChanges.getTypoTolerance()).isEqualTo(TypoTolerance.of("min"));
  }
}
