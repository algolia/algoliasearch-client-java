package com.algolia.search.integration.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.algolia.search.AccountClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.integration.AlgoliaBaseIntegrationTest;
import com.algolia.search.integration.AlgoliaObject;
import com.algolia.search.models.indexing.BatchIndexingResponse;
import com.algolia.search.models.rules.*;
import com.algolia.search.models.settings.IndexSettings;
import com.algolia.search.models.settings.SetSettingsResponse;
import com.algolia.search.models.synonyms.SaveSynonymResponse;
import com.algolia.search.models.synonyms.Synonym;
import com.algolia.search.models.synonyms.SynonymType;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

class AccountCopyTest extends AlgoliaBaseIntegrationTest {

  @Test
  void accountCopyIndexSameApp() {
    String indexOneName = getTestIndexName("copy_index_ex");
    String indexTwoName = getTestIndexName("copy_index_ex_2");

    SearchIndex<AlgoliaObject> index1 = searchClient.initIndex(indexOneName, AlgoliaObject.class);
    SearchIndex<AlgoliaObject> index2 = searchClient.initIndex(indexTwoName, AlgoliaObject.class);

    AccountClient accountClient = new AccountClient();

    assertThatThrownBy(() -> accountClient.copyIndexAsync(index1, index2).get())
        .hasMessageContaining(
            "Source and Destination indices should not be on the same application.");
  }

  @Test
  void testAccountCopyIndex() throws ExecutionException, InterruptedException {
    AccountClient accountClient = new AccountClient();

    String indexOneName = getTestIndexName("copy_index");
    String indexTwoName = getTestIndexName("copy_index_2");

    SearchIndex<AlgoliaObject> index1 = searchClient.initIndex(indexOneName, AlgoliaObject.class);
    SearchIndex<AlgoliaObject> index2 = searchClient2.initIndex(indexTwoName, AlgoliaObject.class);

    AlgoliaObject objectToAdd = new AlgoliaObject().setObjectID("one");

    CompletableFuture<BatchIndexingResponse> addObjectFuture = index1.saveObjectAsync(objectToAdd);

    // Rule to save
    Consequence consequenceToBatch =
        new Consequence()
            .setParams(
                new ConsequenceParams()
                    .setConsequenceQuery(
                        new ConsequenceQuery()
                            .setEdits(Collections.singletonList(Edit.createDelete("patter")))));

    Rule ruleToSave =
        new Rule()
            .setObjectID("one")
            .setCondition(new Condition().setAnchoring("is").setPattern("pattern"))
            .setConsequence(consequenceToBatch);

    CompletableFuture<SaveRuleResponse> saveRuleFuture = index1.saveRuleAsync(ruleToSave);

    Synonym synonymToSave =
        new Synonym()
            .setObjectID("one")
            .setType(SynonymType.SYNONYM)
            .setSynonyms(Arrays.asList("one", "two"));
    CompletableFuture<SaveSynonymResponse> saveSynonymFuture =
        index1.saveSynonymAsync(synonymToSave);

    IndexSettings settings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("company"));

    CompletableFuture<SetSettingsResponse> setSettingsFuture = index1.setSettingsAsync(settings);

    CompletableFuture.allOf(addObjectFuture, saveRuleFuture, saveSynonymFuture, setSettingsFuture)
        .get();

    addObjectFuture.get().waitTask();
    saveRuleFuture.get().waitTask();
    saveSynonymFuture.join().waitTask();
    setSettingsFuture.get().waitTask();

    accountClient.copyIndexAsync(index1, index2).get().waitTask();

    CompletableFuture<AlgoliaObject> getObjectFuture =
        index2.getObjectAsync(objectToAdd.getObjectID());
    CompletableFuture<Rule> getRuleFuture = index2.getRuleAsync(ruleToSave.getObjectID());
    CompletableFuture<Synonym> getSynonymFuture =
        index2.getSynonymAsync(synonymToSave.getObjectID());
    CompletableFuture<IndexSettings> getSettingsFuture = index2.getSettingsAsync();
    CompletableFuture<IndexSettings> getOriginalSettings = index1.getSettingsAsync();

    assertThat(objectToAdd).usingRecursiveComparison().isEqualTo(getObjectFuture.get());
    assertThat(ruleToSave).usingRecursiveComparison().isEqualTo(getRuleFuture.get());
    assertThat(synonymToSave).usingRecursiveComparison().isEqualTo(getSynonymFuture.get());
    assertThat(getOriginalSettings.get())
        .usingRecursiveComparison()
        .isEqualTo(getSettingsFuture.get());

    index2.deleteAsync().get();
  }
}
