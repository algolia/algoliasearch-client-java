package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.inputs.query_rules.Condition;
import com.algolia.search.inputs.query_rules.Consequence;
import com.algolia.search.inputs.query_rules.ConsequenceParams;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.objects.tasks.sync.GenericTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.AfterClass;
import org.junit.Before;

public abstract class SyncAlgoliaIntegrationTest {

  protected static APIClient client;
  private static List<String> indexNameToDeleteAfterTheTests = new ArrayList<>();
  protected String APPLICATION_ID = System.getenv("ALGOLIA_APPLICATION_ID");
  protected String API_KEY = System.getenv("ALGOLIA_API_KEY");
  protected static final long WAIT_TIME_IN_SECONDS = 60 * 5; // 5 minutes

  @AfterClass
  public static void after() {
    //    delete all the indices used in this test
    List<BatchOperation> clean =
        indexNameToDeleteAfterTheTests
            .stream()
            .map(BatchDeleteIndexOperation::new)
            .collect(Collectors.toList());
    try {
      client.batch(clean);
    } catch (Exception ignored) {
    }
  }

  protected static <T> Index<T> createIndex(Class<T> klass) {
    String uniqueIndexName = "test-" + UUID.randomUUID().toString();
    indexNameToDeleteAfterTheTests.add(uniqueIndexName);
    return client.initIndex(uniqueIndexName, klass);
  }

  protected static Analytics createAnalytics() {
    return client.initAnalytics();
  }

  protected static Index<?> createIndex() {
    return createIndex(Object.class);
  }

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

  public <T> void waitForCompletion(GenericTask<T> task) throws AlgoliaException {
    client.waitTask(task, WAIT_TIME_IN_SECONDS);
  }
}
