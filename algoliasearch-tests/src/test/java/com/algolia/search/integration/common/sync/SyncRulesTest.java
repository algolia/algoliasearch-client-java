package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.query_rules.*;
import com.algolia.search.objects.*;
import com.algolia.search.responses.SearchResult;
import com.algolia.search.responses.SearchRuleResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

class DummyRecord {
  private String objectID;
  private String company;

  public DummyRecord() {}

  public String getObjectID() {
    return objectID;
  }

  public void setObjectID(String objectID) {
    this.objectID = objectID;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }
}

@SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
public abstract class SyncRulesTest extends SyncAlgoliaIntegrationTest {

  @Test
  public void saveAndGetRule() throws AlgoliaException {
    String ruleId = "queryRule1";

    Index<?> index = createIndex();

    waitForCompletion(index.saveRule(ruleId, generateRule(ruleId)));

    Optional<Rule> queryRule1 = index.getRule(ruleId);
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule(ruleId));
  }

  @Test
  public void trySaveRuleWithEmptyObjectID() throws Exception {
    Index<?> index = createIndex();

    assertThatThrownBy(() -> index.saveRule("", generateRule("")))
        .hasMessageContaining("Cannot save rule with empty queryRuleID");
  }

  @Test
  public void getRuleUserDataFromQueryResponse() throws Exception {
    Index<DummyRecord> index = createIndex(DummyRecord.class);
    DummyRecord record = new DummyRecord();
    record.setObjectID("one");
    record.setCompany("algolia");
    waitForCompletion(index.addObject(record));

    waitForCompletion(index.saveRule("id", generateRule("id")));
    SearchResult<DummyRecord> res = index.search(new Query("my pattern").setGetRankingInfo(true));

    assertThat(res.getAppliedRules()).hasSize(1);
    assertThat(res.getUserData()).isEqualTo(Arrays.asList(ImmutableMap.of("a", "b")));
  }

  @Test
  public void deleteRule() throws AlgoliaException {
    String queryRuleID = "queryRule2";

    Index<?> index = createIndex();

    waitForCompletion(index.saveRule(queryRuleID, generateRule(queryRuleID)));
    waitForCompletion(index.deleteRule(queryRuleID));

    SearchRuleResult searchResult = index.searchRules(new RuleQuery(""));
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void clearRules() throws AlgoliaException {
    String queryRuleID = "queryRule3";
    Index<?> index = createIndex();

    waitForCompletion(index.saveRule(queryRuleID, generateRule(queryRuleID)));
    waitForCompletion(index.clearRules());

    SearchRuleResult searchResult = index.searchRules(new RuleQuery(""));
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void batchRules() throws AlgoliaException {
    Rule queryRule1 = generateRule("queryRule4");
    Rule queryRule2 = generateRule("queryRule5");

    Index<?> index = createIndex();

    waitForCompletion(index.batchRules(Arrays.asList(queryRule1, queryRule2)));

    SearchRuleResult searchResult = index.searchRules(new RuleQuery(""));
    assertThat(searchResult.getHits()).hasSize(2);
  }

  @Test
  public void queryRuleSerialization() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();

    Condition condition =
        new Condition().setPattern("{facet:products.properties.fbrand}").setAnchoring("contains");

    ConsequenceQueryObject consequenceQuery =
        new ConsequenceQueryObject()
            .setRemove(Collections.singletonList("{facet:products.properties.fbrand}"));

    ConsequenceParams params =
        new ConsequenceParams()
            .setAutomaticFacetFilters(Collections.singletonList("products.properties.fbrand"));

    params.setQuery(consequenceQuery);
    Consequence consequence = new Consequence().setParams(params);

    Rule rule1 =
        new Rule()
            .setObjectID("1528811588947")
            .setDescription("Boost Brands")
            .setCondition(condition)
            .setConsequence(consequence);

    objectMapper.writeValueAsString(rule1);
  }

  /** Test if enabled flag is saved */
  @Test
  public void enabledFlag() throws Exception {
    Rule queryRule = generateRule("queryRule").setEnabled(false);

    Index<?> index = createIndex();

    waitForCompletion(index.saveRule(queryRule.getObjectID(), queryRule));

    Optional<Rule> queryRule1 = index.getRule(queryRule.getObjectID());
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(queryRule);
  }

  /** Should return both rules enabled and disabled */
  @Test
  public void searchRulesEnabledFlag() throws Exception {
    Rule queryRule1 = generateRule("queryRule1").setEnabled(true);
    Rule queryRule2 = generateRule("queryRule2").setEnabled(false);
    Rule queryRule3 = generateRule("queryRule3");

    Index<?> index = createIndex();

    waitForCompletion(index.batchRules(Arrays.asList(queryRule1, queryRule2, queryRule3)));

    SearchRuleResult searchResult = index.searchRules(new RuleQuery(""));
    assertThat(searchResult.getHits()).hasSize(3);
  }

  /**
   * Match empty query only if the “is” anchoring is set.
   *
   * @throws Exception
   */
  @Test
  public void matchTheEmptyQuery() throws Exception {
    Condition ruleCondition = new Condition().setPattern("").setAnchoring("is");
    Consequence ruleConsequence = new Consequence().setUserData(ImmutableMap.of("a", "b"));

    Rule queryRule =
        new Rule()
            .setObjectID("ruleId1")
            .setCondition(ruleCondition)
            .setConsequence(ruleConsequence);

    Index<?> index = createIndex();

    waitForCompletion(index.saveRule(queryRule.getObjectID(), queryRule));

    Optional<Rule> queryRule1 = index.getRule(queryRule.getObjectID());
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(queryRule);
  }

  /**
   * The engine should return an error because only the "is" anchoring is allowed
   *
   * @throws Exception
   */
  @Test
  public void trySaveRuleEmptyQueryWithWrongAnchoring() {
    Condition ruleCondition = new Condition().setPattern("").setAnchoring("contains");
    Consequence ruleConsequence = new Consequence().setUserData(ImmutableMap.of("a", "b"));

    Rule queryRule =
        new Rule()
            .setObjectID("ruleId1")
            .setCondition(ruleCondition)
            .setConsequence(ruleConsequence);

    Index<?> index = createIndex();

    assertThatThrownBy(() -> index.saveRule(queryRule.getObjectID(), queryRule))
        .hasMessageContaining("An empty pattern is only allowed when `anchoring` = `is`");
  }

  /** Test if the validity period is well saved */
  @Test
  public void validityTimeFrame() throws Exception {
    Rule queryRule = generateRule("RuleID1");

    /**
     * Setting the date format in UTC and removing the ms for test purpose if we don't remove the ms
     * it will fail on the equal Assert Also setting the zone offset to UTC for test purpose
     */
    List<TimeRange> validity =
        Arrays.asList(
            new TimeRange(
                ZonedDateTime.now(ZoneOffset.UTC).withNano(0),
                ZonedDateTime.now(ZoneOffset.UTC).plusDays(5).withNano(0)));

    queryRule.setValidity(validity);

    Index<?> index = createIndex();

    waitForCompletion(index.saveRule(queryRule.getObjectID(), queryRule));

    Optional<Rule> queryRule1 = index.getRule(queryRule.getObjectID());
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(queryRule);
  }

  /**
   * Adds the ability to hide a product from the hits, even if it matches the query. Test if hidden
   * object are correctly saved
   *
   * @throws Exception
   */
  @Test
  public void demoteHide() throws Exception {
    Index<DummyRecord> index = createIndex(DummyRecord.class);
    DummyRecord record = new DummyRecord();
    record.setObjectID("one");
    waitForCompletion(index.addObject(record));

    Rule queryRule = generateRule("RuleID1");

    List<Hide> hides = Arrays.asList(new Hide("one"));
    queryRule.getConsequence().setHide(hides);

    waitForCompletion(index.saveRule(queryRule.getObjectID(), queryRule));

    Optional<Rule> queryRule1 = index.getRule(queryRule.getObjectID());
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(queryRule);
  }

  @Test
  public void editsInQuery() throws Exception {
    Condition condition =
        new Condition().setPattern("{facet:products.properties.fbrand}").setAnchoring("contains");

    List<Edit> edits = Arrays.asList(new Edit("replace", "toto", "tata"));

    ConsequenceQueryObject consequenceQuery = new ConsequenceQueryObject().setEdits(edits);

    ConsequenceParams params =
        new ConsequenceParams()
            .setAutomaticFacetFilters(Collections.singletonList("products.properties.fbrand"));

    params.setQuery(consequenceQuery);
    Consequence consequence = new Consequence().setParams(params);

    Rule rule1 =
        new Rule()
            .setObjectID("1528811588947")
            .setDescription("Boost Brands")
            .setCondition(condition)
            .setConsequence(consequence);

    Index<?> index = createIndex();

    waitForCompletion(index.saveRule(rule1.getObjectID(), rule1));

    Optional<Rule> queryRule1 = index.getRule(rule1.getObjectID());
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(rule1);
  }

  @Test
  public void nonRegressionRemoveAttribute() throws Exception {
    Condition condition =
        new Condition().setPattern("{facet:products.properties.fbrand}").setAnchoring("contains");

    ConsequenceQueryObject consequenceQuery =
        new ConsequenceQueryObject()
            .setRemove(Collections.singletonList("{facet:products.properties.fbrand}"));

    ConsequenceParams params =
        new ConsequenceParams()
            .setAutomaticFacetFilters(Collections.singletonList("products.properties.fbrand"));
    ;

    params.setQuery(consequenceQuery);
    Consequence consequence = new Consequence().setParams(params);

    Rule rule1 =
        new Rule()
            .setObjectID("1528811588947")
            .setDescription("Boost Brands")
            .setCondition(condition)
            .setConsequence(consequence);

    Index<?> index = createIndex();

    waitForCompletion(index.saveRule(rule1.getObjectID(), rule1));

    Optional<Rule> queryRule1 = index.getRule(rule1.getObjectID());
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(rule1);
  }

  @Test
  public void automaticFacetFilters() throws Exception {
    Condition condition =
        new Condition().setPattern("{facet:products.properties.fbrand}").setAnchoring("contains");

    List<Edit> edits = Arrays.asList(new Edit("replace", "toto", "tata"));

    ConsequenceQueryObject consequenceQuery = new ConsequenceQueryObject().setEdits(edits);

    List<AutomaticFacetFilter> automaticFacetFilters =
        Arrays.asList(
            new AutomaticFacetFilter("products.properties.fbrand", true, 42),
            new AutomaticFacetFilter("products.properties.fbrand"));

    ConsequenceParams params =
        new ConsequenceParams().setAutomaticFacetFilters(automaticFacetFilters);

    params.setQuery(consequenceQuery);
    Consequence consequence = new Consequence().setParams(params);

    Rule rule1 =
        new Rule()
            .setObjectID("1528811588947")
            .setDescription("Boost Brands")
            .setCondition(condition)
            .setConsequence(consequence);

    Index<?> index = createIndex();

    waitForCompletion(index.saveRule(rule1.getObjectID(), rule1));

    Optional<Rule> queryRule1 = index.getRule(rule1.getObjectID());
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(rule1);
  }

  @Test
  public void optionalAutomaticFacetFilters() throws Exception {
    Condition condition =
        new Condition().setPattern("{facet:products.properties.fbrand}").setAnchoring("contains");

    List<Edit> edits = Arrays.asList(new Edit("replace", "toto", "tata"));

    ConsequenceQueryObject consequenceQuery = new ConsequenceQueryObject().setEdits(edits);

    List<AutomaticFacetFilter> optionalAutomaticFacetFilters =
        Arrays.asList(
            new AutomaticFacetFilter("products.properties.fbrand", true, 42),
            new AutomaticFacetFilter("products.properties.fbrand"));

    ConsequenceParams params =
        new ConsequenceParams().setAutomaticOptionalFacetFilters(optionalAutomaticFacetFilters);

    params.setQuery(consequenceQuery);
    Consequence consequence = new Consequence().setParams(params);

    Rule rule1 =
        new Rule()
            .setObjectID("1528811588947")
            .setDescription("Boost Brands")
            .setCondition(condition)
            .setConsequence(consequence);

    Index<?> index = createIndex();

    waitForCompletion(index.saveRule(rule1.getObjectID(), rule1));

    Optional<Rule> queryRule1 = index.getRule(rule1.getObjectID());
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(rule1);
  }
}
