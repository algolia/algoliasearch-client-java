package com.algolia.search.integration.index;

import static com.algolia.search.integration.TestHelpers.getTestIndexName;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.iterators.RulesIterable;
import com.algolia.search.models.indexing.BatchIndexingResponse;
import com.algolia.search.models.indexing.Query;
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
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public abstract class QueryRulesTest {

  protected final SearchClient searchClient;

  protected QueryRulesTest(SearchClient searchClient) {
    this.searchClient = searchClient;
  }

  @Test
  void RulesTest() throws ExecutionException, InterruptedException {
    SearchIndex<AlgoliaRule> index =
        searchClient.initIndex(getTestIndexName("rules"), AlgoliaRule.class);
    List<AlgoliaRule> objectsToSave =
        Arrays.asList(
            new AlgoliaRule("iphone_7", "Apple", "7"),
            new AlgoliaRule("iphone_8", "Apple", "8"),
            new AlgoliaRule("iphone_X", "Apple", "X"),
            new AlgoliaRule("one_plus_one", "OnePlus", "One"),
            new AlgoliaRule("one_plus_two", "OnePlus", "Two"));

    CompletableFuture<BatchIndexingResponse> saveObjectsFuture =
        index.saveObjectsAsync(objectsToSave);

    // Set attributesForFaceting to [“brand”, "model"] using setSettings and collect the taskID
    IndexSettings settings =
        new IndexSettings().setAttributesForFaceting(Arrays.asList("brand", "model"));

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
            .setCondition(
                new Condition()
                    .setAnchoring("is")
                    .setPattern("mobile phone")
                    .setAlternatives(Alternatives.of(true)))
            .setConsequence(consequenceToBatch);

    // Third rule to save
    Consequence consequenceToBatch2 =
        new Consequence().setParams(new ConsequenceParams().setFilters("brand:OnePlus"));

    Rule ruleToSave3 = new Rule().setObjectID("query_promo").setConsequence(consequenceToBatch2);

    // Fourth rule to save
    Consequence consequenceToBatch3 =
        new Consequence().setParams(new ConsequenceParams().setFilters("model:One"));

    Rule ruleToSave4 =
        new Rule()
            .setObjectID("query_promo_only_summer")
            .setConsequence(consequenceToBatch3)
            .setCondition(new Condition().setContext("summer"));

    // Sending rules request
    CompletableFuture<SaveRuleResponse> saveRuleFuture = index.saveRuleAsync(ruleToSave);
    CompletableFuture<SaveRuleResponse> batchRuleFuture =
        index.saveRulesAsync(Arrays.asList(ruleToSave2, ruleToSave3, ruleToSave4));

    // Wait for the settings to be complete before saving
    saveObjectsFuture.get().waitTask();
    settingsFuture.get().waitTask();
    saveRuleFuture.get().waitTask();
    batchRuleFuture.get().waitTask();

    CompletableFuture<Rule> rule1Future = index.getRuleAsync(ruleToSave.getObjectID());
    CompletableFuture<Rule> rule2Future = index.getRuleAsync(ruleToSave2.getObjectID());
    CompletableFuture<Rule> rule3Future = index.getRuleAsync(ruleToSave3.getObjectID());
    CompletableFuture<Rule> rule4Future = index.getRuleAsync(ruleToSave4.getObjectID());

    Rule retrievedRule = rule1Future.get();
    Rule retrievedRule2 = rule2Future.get();
    Rule retrievedRule3 = rule3Future.get();
    Rule retrievedRule4 = rule4Future.get();

    assertThat(retrievedRule).usingRecursiveComparison().isEqualTo(ruleToSave);
    assertThat(retrievedRule2).usingRecursiveComparison().isEqualTo(ruleToSave2);
    assertThat(retrievedRule3).usingRecursiveComparison().isEqualTo(ruleToSave3);
    assertThat(retrievedRule4).usingRecursiveComparison().isEqualTo(ruleToSave4);

    SearchResult<AlgoliaRule> searchWithContext =
        index.searchAsync(new Query().setRuleContexts(Collections.singletonList("summer"))).get();

    assertThat(searchWithContext.getHits()).hasSize(1);

    SearchResult<Rule> searchRules = index.searchRulesAsync(new RuleQuery("")).get();
    assertThat(searchRules.getHits()).hasSize(4);

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

    index.deleteRuleAsync(ruleToSave.getObjectID()).get().waitTask();

    SearchResult<Rule> searchRulesAfterDelete = index.searchRulesAsync(new RuleQuery("")).get();
    assertThat(searchRulesAfterDelete.getHits()).hasSize(3);
    assertThat(searchRulesAfterDelete.getHits())
        .extracting(Rule::getObjectID)
        .containsExactlyInAnyOrder(
            retrievedRule2.getObjectID(),
            retrievedRule3.getObjectID(),
            retrievedRule4.getObjectID());

    index.clearRulesAsync().get().waitTask();

    SearchResult<Rule> searchRulesAfterClear = index.searchRulesAsync(new RuleQuery("")).get();
    assertThat(searchRulesAfterClear.getHits()).hasSize(0);
  }
}

class AlgoliaRule {

  public AlgoliaRule() {}

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
