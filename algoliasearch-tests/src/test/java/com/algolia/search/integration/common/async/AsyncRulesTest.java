package com.algolia.search.integration.common.async;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.query_rules.*;
import com.algolia.search.objects.*;
import com.algolia.search.responses.SearchRuleResult;
import com.google.common.collect.ImmutableMap;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

@SuppressWarnings("ConstantConditions")
public abstract class AsyncRulesTest extends AsyncAlgoliaIntegrationTest {

  private Rule generateRule(String objectID) {
    Condition condition = new Condition().setPattern("my pattern").setAnchoring("is");
    Consequence consequence = new Consequence().setUserData(ImmutableMap.of("a", "b"));

    return new Rule().setObjectID(objectID).setCondition(condition).setConsequence(consequence);
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  public void saveAndGetRule() throws Exception {
    String ruleID = "queryRule1";
    AsyncIndex<?> index = createIndex();

    waitForCompletion(index.saveRule("queryRule1", generateRule(ruleID)));

    Optional<Rule> queryRule1 = index.getRule("queryRule1").get();
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule(ruleID));
  }

  @Test
  public void trySaveRuleWithEmptyObjectID() throws Exception {
    AsyncIndex<?> index = createIndex();

    assertThatThrownBy(() -> index.saveRule("", generateRule("")).get())
        .hasMessageContaining("Cannot save rule with empty queryRuleID");
  }

  @Test
  public void deleteQueryRule() throws Exception {
    String queryRuleID = "queryRule2";
    AsyncIndex<?> index = createIndex();

    waitForCompletion(index.saveRule(queryRuleID, generateRule(queryRuleID)));
    waitForCompletion(index.deleteRule(queryRuleID));

    SearchRuleResult searchResult = index.searchRules(new RuleQuery("")).get();
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void clearQueryRule() throws Exception {
    String queryRuleID = "queryRule3";
    AsyncIndex<?> index = createIndex();

    waitForCompletion(index.saveRule(queryRuleID, generateRule(queryRuleID)));
    waitForCompletion(index.clearRules());

    SearchRuleResult searchResult = index.searchRules(new RuleQuery("")).get();
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void batchSaveQueryRules() throws Exception {
    Rule queryRule1 = generateRule("queryRule4");
    Rule queryRule2 = generateRule("queryRule5");

    AsyncIndex<?> index = createIndex();

    waitForCompletion(index.batchRules(Arrays.asList(queryRule1, queryRule2)));

    SearchRuleResult searchResult = index.searchRules(new RuleQuery("")).get();
    assertThat(searchResult.getHits()).hasSize(2);
  }

  /** Test if enabled flag is saved */
  @Test
  public void enabledFlag() throws Exception {
    Rule queryRule = generateRule("queryRule").setEnabled(false);

    AsyncIndex<?> index = createIndex();

    waitForCompletion(index.saveRule(queryRule.getObjectID(), queryRule));

    Optional<Rule> queryRule1 = index.getRule(queryRule.getObjectID()).get();
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

    AsyncIndex<?> index = createIndex();

    waitForCompletion(index.batchRules(Arrays.asList(queryRule1, queryRule2, queryRule3)));

    SearchRuleResult searchResult = index.searchRules(new RuleQuery("")).get();
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

    AsyncIndex<?> index = createIndex();

    waitForCompletion(index.saveRule(queryRule.getObjectID(), queryRule));

    Optional<Rule> queryRule1 = index.getRule(queryRule.getObjectID()).get();
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

    AsyncIndex<?> index = createIndex();

    assertThatThrownBy(() -> index.saveRule(queryRule.getObjectID(), queryRule).get())
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

    AsyncIndex<?> index = createIndex();

    waitForCompletion(index.saveRule(queryRule.getObjectID(), queryRule));

    Optional<Rule> queryRule1 = index.getRule(queryRule.getObjectID()).get();
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

    AsyncIndex<?> index = createIndex();

    waitForCompletion(index.saveRule(rule1.getObjectID(), rule1));

    Optional<Rule> queryRule1 = index.getRule(rule1.getObjectID()).get();
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

    params.setQuery(consequenceQuery);
    Consequence consequence = new Consequence().setParams(params);

    Rule rule1 =
        new Rule()
            .setObjectID("1528811588947")
            .setDescription("Boost Brands")
            .setCondition(condition)
            .setConsequence(consequence);

    AsyncIndex<?> index = createIndex();

    waitForCompletion(index.saveRule(rule1.getObjectID(), rule1));

    Optional<Rule> queryRule1 = index.getRule(rule1.getObjectID()).get();
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

    AsyncIndex<?> index = createIndex();

    waitForCompletion(index.saveRule(rule1.getObjectID(), rule1));

    Optional<Rule> queryRule1 = index.getRule(rule1.getObjectID()).get();
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

    AsyncIndex<?> index = createIndex();

    waitForCompletion(index.saveRule(rule1.getObjectID(), rule1));

    Optional<Rule> queryRule1 = index.getRule(rule1.getObjectID()).get();
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(rule1);
  }
}
