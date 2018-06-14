package com.algolia.search.integration.common.sync;

import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RuleQuery;
import com.algolia.search.responses.SearchResult;
import com.algolia.search.responses.SearchRuleResult;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    assertThatThrownBy(
        () -> index.saveRule("", generateRule(""))
    ).hasMessageContaining("Cannot save rule with empty queryRuleID");
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
}
