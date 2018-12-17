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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.AfterClass;
import org.junit.Before;

public abstract class SyncAlgoliaIntegrationTest {

  protected static APIClient client;
  private static List<String> indexNameToDeleteAfterTheTests = new ArrayList<>();
  protected String ALGOLIA_APPLICATION_ID_1 = System.getenv("ALGOLIA_APPLICATION_ID_1");
  protected String ALGOLIA_API_KEY_1 = System.getenv("ALGOLIA_ADMIN_KEY_1");
  protected String ALGOLIA_APPLICATION_ID_2 = System.getenv("ALGOLIA_APPLICATION_ID_2");
  protected String ALGOLIA_API_KEY_2 = System.getenv("ALGOLIA_ADMIN_KEY_2");
  private static final long WAIT_TIME_IN_SECONDS = 60 * 5; // 5 minutes

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
    String uniqueIndexName = "java_" + UUID.randomUUID().toString();
    return createIndex(uniqueIndexName, klass);
  }

  protected static <T> Index<T> createIndex(String indexName, Class<T> klass) {
    indexNameToDeleteAfterTheTests.add(indexName);
    return client.initIndex(indexName, klass);
  }

  protected static Analytics createAnalytics() {
    return client.initAnalytics();
  }

  protected static Index<?> createIndex() {
    return createIndex(Object.class);
  }

  @Before
  public void checkEnvVariables() throws Exception {
    if (ALGOLIA_APPLICATION_ID_1 == null || ALGOLIA_APPLICATION_ID_1.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID is not defined or empty");
    }
    if (ALGOLIA_API_KEY_1 == null || ALGOLIA_API_KEY_1.isEmpty()) {
      throw new Exception("ALGOLIA_API_KEY is not defined or empty");
    }

    if (ALGOLIA_APPLICATION_ID_2 == null || ALGOLIA_APPLICATION_ID_2.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID_2 is not defined or empty");
    }
    if (ALGOLIA_API_KEY_2 == null || ALGOLIA_API_KEY_2.isEmpty()) {
      throw new Exception("ALGOLIA_API_KEY_2 is not defined or empty");
    }

    client = createInstance(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1);
  }

  public abstract APIClient createInstance(String appId, String apiKey);

  public abstract APIClient createInstance(String appId, String apiKey, ObjectMapper objectMapper);

  protected Rule generateRule(String objectID) {
    Condition ruleCondition = new Condition().setPattern("my pattern").setAnchoring("is");
    Consequence ruleConsequence =
        new Consequence()
            .setUserData(ImmutableMap.of("a", "b"))
            .setParams(new ConsequenceParams<>().setFacets(Arrays.asList("a=1")));

    return new Rule()
        .setObjectID(objectID)
        .setCondition(ruleCondition)
        .setConsequence(ruleConsequence);
  }

  public <T> void waitForCompletion(GenericTask<T> task) throws AlgoliaException {
    client.waitTask(task, WAIT_TIME_IN_SECONDS);
  }
}
