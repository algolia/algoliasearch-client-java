package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.inputs.query_rules.Condition;
import com.algolia.search.inputs.query_rules.Consequence;
import com.algolia.search.inputs.query_rules.ConsequenceParams;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.objects.RuleQuery;
import com.algolia.search.responses.SearchRuleResult;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
public abstract class SyncRulesTest extends SyncAlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList("index1", "index2", "index3", "index4");

  private Rule generateRule(String objectID) {
    Condition ruleCondition = new Condition().setPattern("my pattern").setAnchoring("is");
    Consequence ruleConsequence =
        new Consequence()
            .setUserData(ImmutableMap.of("a", "b"))
            .setParams(new ConsequenceParams().setFacets("a=1"));

    return new Rule()
        .setObjectID(objectID)
        .setCondition(ruleCondition)
        .setConsequence(ruleConsequence);
  }

  @Before
  @After
  public void cleanUp() throws AlgoliaException {
    List<BatchOperation> clean =
        indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).waitForCompletion();
  }

  @Test
  public void saveAndGetRule() throws AlgoliaException {
    String ruleId = "queryRule1";

    Index<?> index = client.initIndex("index1");

    index.saveRule(ruleId, generateRule(ruleId)).waitForCompletion();

    Optional<Rule> queryRule1 = index.getRule(ruleId);
    assertThat(queryRule1.get())
        .isInstanceOf(Rule.class)
        .isEqualToComparingFieldByFieldRecursively(generateRule(ruleId));
  }

  @Test
  public void deleteRule() throws AlgoliaException {
    String queryRuleID = "queryRule2";

    Index<?> index = client.initIndex("index2");

    index.saveRule(queryRuleID, generateRule(queryRuleID)).waitForCompletion();
    index.deleteRule(queryRuleID).waitForCompletion();

    SearchRuleResult searchResult = index.searchRules(new RuleQuery(""));
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void clearRules() throws AlgoliaException {
    String queryRuleID = "queryRule3";
    Index<?> index = client.initIndex("index3");

    index.saveRule(queryRuleID, generateRule(queryRuleID)).waitForCompletion();
    index.clearRules().waitForCompletion();

    SearchRuleResult searchResult = index.searchRules(new RuleQuery(""));
    assertThat(searchResult.getHits()).hasSize(0);
  }

  @Test
  public void batchRules() throws AlgoliaException {
    Rule queryRule1 = generateRule("queryRule4");
    Rule queryRule2 = generateRule("queryRule5");

    Index<?> index = client.initIndex("index4");

    index.batchRules(Arrays.asList(queryRule1, queryRule2)).waitForCompletion();

    SearchRuleResult searchResult = index.searchRules(new RuleQuery(""));
    assertThat(searchResult.getHits()).hasSize(2);
  }
}
