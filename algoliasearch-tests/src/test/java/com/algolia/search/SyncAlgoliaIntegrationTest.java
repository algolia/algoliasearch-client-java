package com.algolia.search;

import com.algolia.search.inputs.query_rules.Condition;
import com.algolia.search.inputs.query_rules.Consequence;
import com.algolia.search.inputs.query_rules.ConsequenceParams;
import com.algolia.search.inputs.query_rules.Rule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;

public abstract class SyncAlgoliaIntegrationTest {

  public APIClient client;
  protected String APPLICATION_ID = System.getenv("APPLICATION_ID");
  protected String API_KEY = System.getenv("API_KEY");

  @Before
  public void checkEnvVariables() throws Exception {
    if (APPLICATION_ID == null || APPLICATION_ID.isEmpty()) {
      throw new Exception("APPLICATION_ID is not defined or empty");
    }
    if (API_KEY == null || API_KEY.isEmpty()) {
      throw new Exception("API_KEY is not defined or empty");
    }
    client = createInstance(APPLICATION_ID, API_KEY);
  }

  public abstract APIClient createInstance(String appId, String apiKey);

  public abstract APIClient createInstance(String appId, String apiKey, ObjectMapper objectMapper);

  protected Rule generateRule(String objectID) {
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
}
