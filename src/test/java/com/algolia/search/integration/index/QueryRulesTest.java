package com.algolia.search.integration.index;

import static com.algolia.search.integration.IntegrationTestExtension.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SearchIndex;
import com.algolia.search.integration.IntegrationTestExtension;
import com.algolia.search.iterators.RulesIterable;
import com.algolia.search.models.indexing.BatchIndexingResponse;
import com.algolia.search.models.indexing.SearchResult;
import com.algolia.search.models.rules.*;
import com.algolia.search.models.settings.IndexSettings;
import com.algolia.search.models.settings.SetSettingsResponse;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@ExtendWith({IntegrationTestExtension.class})
class QueryRulesTest {

  private SearchIndex<AlgoliaRule> index;
  private List<AlgoliaRule> objectsToSave;

  QueryRulesTest() {
    index = searchClient.initIndex(getTestIndexName("rules"), AlgoliaRule.class);
    objectsToSave =
        Arrays.asList(
            new AlgoliaRule("iphone_7", "Apple", "7"),
            new AlgoliaRule("iphone_8", "Apple", "8"),
            new AlgoliaRule("iphone_X", "Apple", "X"),
            new AlgoliaRule("one_plus_one", "OnePlus", "One"),
            new AlgoliaRule("one_plus_two", "OnePlus", "Two"));
  }

  @Test
  void RulesTest() {

    CompletableFuture<BatchIndexingResponse> saveObjectsFuture =
        index.saveObjectsAsync(objectsToSave);

    // Set attributesForFaceting to [“brand”] using setSettings and collect the taskID
    IndexSettings settings =
        new IndexSettings().setAttributesForFaceting(Collections.singletonList("brand"));

    CompletableFuture<SetSettingsResponse> settingsFuture = index.setSettingsAsync(settings);

    Condition condition = new Condition().setAnchoring("is").setPattern("{facet:brand}");

    // First rule to save
    List<TimeRange> validity =
        Arrays.asList(
            new TimeRange(
                OffsetDateTime.of(2018, 7, 24, 13, 35, 0, 0, ZoneOffset.UTC),
                OffsetDateTime.of(2018, 7, 25, 13, 35, 0, 0, ZoneOffset.UTC)),
            new TimeRange(
                OffsetDateTime.of(2018, 7, 26, 13, 35, 0, 0, ZoneOffset.UTC),
                OffsetDateTime.of(2018, 7, 27, 13, 35, 0, 0, ZoneOffset.UTC)));

    Consequence consequence =
        new Consequence()
            .setParams(
                new ConsequenceParams()
                    .setAutomaticFacetFilters(
                        Collections.singletonList(new AutomaticFacetFilter("brand", true, 42))));

    Rule ruleToSave =
        new Rule()
            .setObjectID("brand_automatic_faceting")
            .setEnabled(false)
            .setValidity(validity)
            .setCondition(condition)
            .setConsequence(consequence)
            .setDescription(
                "Automatic apply the faceting on `brand` if a brand value is found in the query");

    // Second rule to save
    Consequence consequenceToBatch =
        new Consequence()
            .setParams(
                new ConsequenceParams()
                    .setConsequenceQuery(
                        new ConsequenceQuery()
                            .setEdits(
                                Arrays.asList(
                                    Edit.createDelete("mobile"),
                                    Edit.createReplace("phone", "iphone")))));

    Rule ruleToSave2 =
        new Rule()
            .setObjectID("query_edits")
            .setCondition(new Condition().setAnchoring("is").setPattern("mobile phone"))
            .setConsequence(consequenceToBatch);

    // Sending rules request
    CompletableFuture<SaveRuleResponse> saveRuleFuture = index.saveRuleAsync(ruleToSave);
    CompletableFuture<SaveRuleResponse> batchRuleFuture =
        index.saveRulesAsync(Collections.singletonList(ruleToSave2));

    // Wait for the settings to be complete before saving
    saveObjectsFuture.join().waitTask();
    settingsFuture.join().waitTask();
    saveRuleFuture.join().waitTask();
    batchRuleFuture.join().waitTask();

    CompletableFuture<Rule> getRuleFuture = index.getRuleAsync(ruleToSave.getObjectID());
    CompletableFuture<Rule> getBatchedRuleFuture = index.getRuleAsync(ruleToSave2.getObjectID());

    Rule retrievedRule = getRuleFuture.join();
    Rule retrievedRule2 = getBatchedRuleFuture.join();

    assertThat(retrievedRule).usingRecursiveComparison().isEqualTo(ruleToSave);

    assertThat(retrievedRule2).usingRecursiveComparison().isEqualTo(ruleToSave2);

    SearchResult<Rule> searchRules = index.searchRulesAsync(new RuleQuery("")).join();
    assertThat(searchRules.getHits()).hasSize(2);

    assertThat(
            searchRules.getHits().stream()
                .filter(r -> r.getObjectID().equals(ruleToSave.getObjectID()))
                .findFirst()
                .get())
        .usingRecursiveComparison()
        .isEqualTo(ruleToSave);

    assertThat(
            searchRules.getHits().stream()
                .filter(r -> r.getObjectID().equals(retrievedRule2.getObjectID()))
                .findFirst()
                .get())
        .usingRecursiveComparison()
        .isEqualTo(retrievedRule2);

    List<Rule> rulesFromIterator = new ArrayList<>();

    for (Rule rule : new RulesIterable(index)) {
      rulesFromIterator.add(rule);
    }

    assertThat(
            rulesFromIterator.stream()
                .filter(r -> r.getObjectID().equals(ruleToSave.getObjectID()))
                .findFirst()
                .get())
        .usingRecursiveComparison()
        .isEqualTo(ruleToSave);

    assertThat(
            rulesFromIterator.stream()
                .filter(r -> r.getObjectID().equals(retrievedRule2.getObjectID()))
                .findFirst()
                .get())
        .usingRecursiveComparison()
        .isEqualTo(retrievedRule2);

    index.deleteRuleAsync(ruleToSave.getObjectID()).join().waitTask();

    SearchResult<Rule> searchRulesAfterDelete = index.searchRulesAsync(new RuleQuery("")).join();
    assertThat(searchRulesAfterDelete.getHits()).hasSize(1);
    assertThat(searchRulesAfterDelete.getHits())
        .extracting(Rule::getObjectID)
        .containsExactly(retrievedRule2.getObjectID());

    index.clearRulesAsync().join().waitTask();

    SearchResult<Rule> searchRulesAfterClear = index.searchRulesAsync(new RuleQuery("")).join();
    assertThat(searchRulesAfterClear.getHits()).hasSize(0);
  }

  class AlgoliaRule {

    AlgoliaRule(String objectID, String brand, String model) {
      this.objectID = objectID;
      this.brand = brand;
      this.model = model;
    }

    public String getObjectID() {
      return objectID;
    }

    public void setObjectID(String objectID) {
      this.objectID = objectID;
    }

    public String getBrand() {
      return brand;
    }

    public void setBrand(String brand) {
      this.brand = brand;
    }

    public String getModel() {
      return model;
    }

    public void setModel(String model) {
      this.model = model;
    }

    private String objectID;
    private String brand;
    private String model;
  }
}
