package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.objects.RuleQuery;
import com.algolia.search.responses.SearchRuleResult;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;

@SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
public abstract class SyncRulesTest extends SyncAlgoliaIntegrationTest {

  @Test
  public void saveAndGetRule() throws AlgoliaException {
    String ruleId = "queryRule1";

    Index<?> index = createIndex();

    index.saveRule(ruleId, generateRule(ruleId)).waitForCompletion();

    Optional<Rule> queryRule1 = index.getRule(ruleId);
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule(ruleId));
  }

  @Test
  public void deleteRule() throws AlgoliaException {
    String queryRuleID = "queryRule2";

    Index<?> index = createIndex();

    index.saveRule(queryRuleID, generateRule(queryRuleID)).waitForCompletion();
    index.deleteRule(queryRuleID).waitForCompletion();

    SearchRuleResult searchResult = index.searchRules(new RuleQuery(""));
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void clearRules() throws AlgoliaException {
    String queryRuleID = "queryRule3";
    Index<?> index = createIndex();

    index.saveRule(queryRuleID, generateRule(queryRuleID)).waitForCompletion();
    index.clearRules().waitForCompletion();

    SearchRuleResult searchResult = index.searchRules(new RuleQuery(""));
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void batchRules() throws AlgoliaException {
    Rule queryRule1 = generateRule("queryRule4");
    Rule queryRule2 = generateRule("queryRule5");

    Index<?> index = createIndex();

    index.batchRules(Arrays.asList(queryRule1, queryRule2)).waitForCompletion();

    SearchRuleResult searchResult = index.searchRules(new RuleQuery(""));
    assertThat(searchResult.getHits()).hasSize(2);
  }
}
