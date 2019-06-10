package com.algolia.search.integration.client;

import static com.algolia.search.integration.TestHelpers.getTestIndexName;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.integration.models.CopyIndexTestObject;
import com.algolia.search.models.indexing.BatchIndexingResponse;
import com.algolia.search.models.indexing.CopyResponse;
import com.algolia.search.models.rules.*;
import com.algolia.search.models.settings.IndexSettings;
import com.algolia.search.models.settings.SetSettingsResponse;
import com.algolia.search.models.synonyms.SaveSynonymResponse;
import com.algolia.search.models.synonyms.Synonym;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

public abstract class CopyIndexTest {

  protected final SearchClient searchClient;

  protected CopyIndexTest(SearchClient searchClient) {
    this.searchClient = searchClient;
  }

  @Test
  void testCopyIndex() throws ExecutionException, InterruptedException {
    String sourceIndexName = getTestIndexName("source_index");
    String settingsIndexName = getTestIndexName("copy_index_settings");
    String rulesIndexName = getTestIndexName("copy_index_rules");
    String synonymsIndexName = getTestIndexName("copy_index_synonyms");
    String fullIndexName = getTestIndexName("copy_index_full");

    SearchIndex<CopyIndexTestObject> sourceIndex =
        searchClient.initIndex(sourceIndexName, CopyIndexTestObject.class);
    SearchIndex<CopyIndexTestObject> settingsIndex =
        searchClient.initIndex(settingsIndexName, CopyIndexTestObject.class);
    SearchIndex<CopyIndexTestObject> rulesIndex =
        searchClient.initIndex(rulesIndexName, CopyIndexTestObject.class);
    SearchIndex<CopyIndexTestObject> syonymsIndex =
        searchClient.initIndex(synonymsIndexName, CopyIndexTestObject.class);
    SearchIndex<CopyIndexTestObject> fullIndex =
        searchClient.initIndex(fullIndexName, CopyIndexTestObject.class);

    List<CopyIndexTestObject> objectsToAdd =
        Arrays.asList(
            new CopyIndexTestObject("one", "apple"), new CopyIndexTestObject("two", "algolia"));

    CompletableFuture<BatchIndexingResponse> objectsToSaveFuture =
        sourceIndex.saveObjectsAsync(objectsToAdd);

    IndexSettings settings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("company"));

    objectsToSaveFuture.get().waitTask();

    CompletableFuture<SetSettingsResponse> setSettingsFuture =
        sourceIndex.setSettingsAsync(settings);

    Synonym synonymToSave =
        Synonym.createPlaceHolder("google_placeholder", "<GOOG>", Arrays.asList("Google", "GOOG"));

    CompletableFuture<SaveSynonymResponse> saveSynonymsFuture =
        sourceIndex.saveSynonymAsync(synonymToSave);

    Rule ruleToSave =
        new Rule()
            .setObjectID("company_automatic_faceting")
            .setCondition(new Condition().setAnchoring("contains").setPattern("{facet:company}"))
            .setConsequence(
                new Consequence()
                    .setParams(
                        new ConsequenceParams()
                            .setAutomaticFacetFilters(
                                Collections.singletonList(new AutomaticFacetFilter("company")))));

    CompletableFuture<SaveRuleResponse> saveRuleFuture = sourceIndex.saveRuleAsync(ruleToSave);

    CompletableFuture.allOf(
        objectsToSaveFuture, saveRuleFuture, saveSynonymsFuture, setSettingsFuture);

    // Algolia Wait
    objectsToSaveFuture.get().waitTask();
    saveRuleFuture.get().waitTask();
    saveSynonymsFuture.get().waitTask();
    setSettingsFuture.get().waitTask();

    CompletableFuture<CopyResponse> copySettingsFuture =
        searchClient.copySettingsAsync(sourceIndexName, settingsIndexName);
    CompletableFuture<CopyResponse> copyRulesFuture =
        searchClient.copyRulesAsync(sourceIndexName, rulesIndexName);
    CompletableFuture<CopyResponse> copySynonymsFuture =
        searchClient.copySynonymsAsync(sourceIndexName, synonymsIndexName);
    CompletableFuture<CopyResponse> copyFullFuture =
        searchClient.copyIndexAsync(sourceIndexName, fullIndexName);

    CompletableFuture.allOf(
        copySettingsFuture, copyRulesFuture, copySynonymsFuture, copyFullFuture);

    copySettingsFuture.get().waitTask();
    copyFullFuture.get().waitTask();
    copyRulesFuture.get().waitTask();
    copySynonymsFuture.get().waitTask();

    // Check index with copied settings
    CompletableFuture<IndexSettings> originalSettingsFutures = sourceIndex.getSettingsAsync();
    CompletableFuture<IndexSettings> copiedSettingsFuture = settingsIndex.getSettingsAsync();
    assertThat(copiedSettingsFuture.get())
        .usingRecursiveComparison()
        .isEqualTo(originalSettingsFutures.get());

    // Check index with copied rules
    Rule copiedRules = rulesIndex.getRule(ruleToSave.getObjectID());
    assertThat(copiedRules).usingRecursiveComparison().isEqualTo(ruleToSave);

    // Check index with copied synonyms
    Synonym copiedSynonym = syonymsIndex.getSynonymAsync(synonymToSave.getObjectID()).get();
    assertThat(copiedSynonym).usingRecursiveComparison().isEqualTo(synonymToSave);

    // Check index full copy
    CompletableFuture<IndexSettings> fullSettingsFuture = fullIndex.getSettingsAsync();
    CompletableFuture<Rule> fullRuleFuture = fullIndex.getRuleAsync(ruleToSave.getObjectID());
    CompletableFuture<Synonym> fullSynonymFuture =
        fullIndex.getSynonymAsync(synonymToSave.getObjectID());

    CompletableFuture.allOf(fullSettingsFuture, fullRuleFuture, fullSynonymFuture);
  }
}
