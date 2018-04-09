package com.algolia.search.integration.common.async;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.query_rules.Condition;
import com.algolia.search.inputs.query_rules.Consequence;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.objects.RuleQuery;
import com.algolia.search.objects.tasks.async.AsyncTask;
import com.algolia.search.responses.SearchRuleResult;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
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

    assertThatThrownBy(
        () -> index.saveRule("", generateRule("")).get()
    ).hasMessageContaining("Cannot save rule with empty queryRuleID");
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
}
