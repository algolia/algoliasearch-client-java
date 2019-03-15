package com.algolia.search.integration.index;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.algolia.search.clients.SearchIndex;
import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.integration.AlgoliaBaseIntegrationTest;
import com.algolia.search.integration.AlgoliaObject;
import com.algolia.search.models.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

class ReplacingTest extends AlgoliaBaseIntegrationTest {

  private SearchIndex<AlgoliaObject> index;

  void init() {
    index = searchClient.initIndex(getTestIndexName("replacing"), AlgoliaObject.class);
  }

  @Test
  void testReplacing() throws ExecutionException, InterruptedException {
    init();

    CompletableFuture<BatchIndexingResponse> addObjectFuture =
        index.saveObjectAsync(new AlgoliaObject().setObjectID("one"));

    // Second rule to save
    Consequence consequenceToBatch =
        new Consequence()
            .setParams(
                new ConsequenceParams()
                    .setConsequenceQuery(
                        new ConsequenceQuery()
                            .setEdits(Collections.singletonList(Edit.createDelete("pattern")))));

    Rule ruleToSave =
        new Rule()
            .setObjectID("one")
            .setCondition(new Condition().setAnchoring("is").setPattern("pattern"))
            .setConsequence(consequenceToBatch);

    CompletableFuture<SaveRuleResponse> saveRuleFuture = index.saveRuleAsync(ruleToSave);

    Synonym synonymToSave =
        new Synonym()
            .setObjectID("one")
            .setType(SynonymType.Synonym)
            .setSynonyms(Arrays.asList("one", "two"));

    CompletableFuture<SaveSynonymResponse> saveSynonymFuture =
        index.saveSynonymAsync(synonymToSave);

    CompletableFuture.allOf(addObjectFuture, saveRuleFuture, saveSynonymFuture).get();

    addObjectFuture.get().waitTask();
    saveRuleFuture.get().waitTask();
    saveSynonymFuture.get().waitTask();

    CompletableFuture<MultiResponse> replaceAllFuture =
        index.replaceAllObjectsAsync(Collections.singleton(new AlgoliaObject().setObjectID("two")));

    replaceAllFuture.get().waitTask();

    // Second rule to save
    Rule ruleToSave2 =
        new Rule()
            .setObjectID("two")
            .setCondition(new Condition().setAnchoring("is").setPattern("pattern"))
            .setConsequence(consequenceToBatch);

    CompletableFuture<SaveRuleResponse> replaceAllRulesFuture =
        index.replaceAllRulesAsync(Collections.singletonList(ruleToSave2));

    Synonym synonymToSave2 =
        new Synonym()
            .setObjectID("two")
            .setType(SynonymType.Synonym)
            .setSynonyms(Arrays.asList("one", "two"));

    CompletableFuture<SaveSynonymResponse> replaceAllSynonymsFuture =
        index.replaceAllSynonymsAsync(Collections.singletonList(synonymToSave2));

    CompletableFuture.allOf(replaceAllRulesFuture, replaceAllSynonymsFuture);

    replaceAllRulesFuture.get().waitTask();
    replaceAllSynonymsFuture.get().waitTask();

    assertThatThrownBy(() -> index.getObjectAsync("one").join())
        .hasCauseInstanceOf(AlgoliaApiException.class)
        .hasMessageContaining("ObjectID does not exist");

    assertThatThrownBy(() -> index.getRuleAsync("one").join())
        .hasCauseInstanceOf(AlgoliaApiException.class)
        .hasMessageContaining("ObjectID does not exist");

    assertThatThrownBy(() -> index.getSynonymAsync("one").join())
        .hasCauseInstanceOf(AlgoliaApiException.class)
        .hasMessageContaining("Synonym set does not exist");

    CompletableFuture<Rule> ruleAfterReplaceFuture = index.getRuleAsync("two");
    CompletableFuture<Synonym> synonymAfterReplaceFuture = index.getSynonymAsync("two");

    CompletableFuture.allOf(ruleAfterReplaceFuture, synonymAfterReplaceFuture);

    assertThat(ruleAfterReplaceFuture.get()).usingRecursiveComparison().isEqualTo(ruleToSave2);
    assertThat(synonymAfterReplaceFuture.get())
        .usingRecursiveComparison()
        .isEqualTo(synonymToSave2);
  }
}
