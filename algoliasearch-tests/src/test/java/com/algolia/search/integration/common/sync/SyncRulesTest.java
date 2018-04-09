package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.objects.RuleQuery;
import com.algolia.search.objects.tasks.sync.Task;
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

    waitForCompletion(index.saveRule(ruleId, generateRule(ruleId)));

    Optional<Rule> queryRule1 = index.getRule(ruleId);
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule(ruleId));
  }

  @Test
  public void trySaveRuleWithEmptyObjectID() throws Exception {
    Index<?> index = createIndex();

    assertThatThrownBy(
        () -> index.saveRule("", generateRule(""))
    ).hasMessageContaining("Cannot save rule with empty queryRuleID");
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
}
