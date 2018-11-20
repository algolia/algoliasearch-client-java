package com.algolia.search;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.objects.tasks.async.AsyncGenericTask;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.ListAssert;
import org.junit.AfterClass;
import org.junit.Before;

public abstract class AsyncAlgoliaIntegrationTest {

  protected static final long WAIT_TIME_IN_SECONDS = 60 * 5; // 5 minutes
  protected static AsyncAPIClient client;
  protected String ALGOLIA_APPLICATION_ID_1 = System.getenv("ALGOLIA_APPLICATION_ID_1");
  protected String ALGOLIA_API_KEY_1 = System.getenv("ALGOLIA_ADMIN_KEY_1");
  protected String ALGOLIA_APPLICATION_ID_2 = System.getenv("ALGOLIA_APPLICATION_ID_2");
  protected String ALGOLIA_API_KEY_2 = System.getenv("ALGOLIA_ADMIN_KEY_2");

  private static List<String> indexNameToDeleteAfterTheTests = new ArrayList<>();

  @AfterClass
  public static void cleanUpIndices() {
    //    delete all the indices used in this test
    List<BatchOperation> clean =
        indexNameToDeleteAfterTheTests
            .stream()
            .map(BatchDeleteIndexOperation::new)
            .collect(Collectors.toList());
    client.batch(clean);
  }

  protected static <T> AsyncIndex<T> createIndex(Class<T> klass) {
    String uniqueIndexName = "java_" + UUID.randomUUID().toString();
    return createIndex(uniqueIndexName, klass);
  }

  protected static <T> AsyncIndex<T> createIndex(String indexName, Class<T> klass) {
    indexNameToDeleteAfterTheTests.add(indexName);
    return client.initIndex(indexName, klass);
  }

  protected static AsyncAnalytics createAnalytics() {
    return client.initAnalytics();
  }

  protected static AsyncIndex<Object> createIndex() {
    return createIndex(Object.class);
  }

  @Before
  public void checkEnvVariables() throws Exception {
    if (ALGOLIA_APPLICATION_ID_1 == null || ALGOLIA_APPLICATION_ID_1.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID_1 is not defined or empty");
    }
    if (ALGOLIA_API_KEY_1 == null || ALGOLIA_API_KEY_1.isEmpty()) {
      throw new Exception("ALGOLIA_API_KEY_1 is not defined or empty");
    }

    if (ALGOLIA_APPLICATION_ID_2 == null || ALGOLIA_APPLICATION_ID_2.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID_2 is not defined or empty");
    }
    if (ALGOLIA_API_KEY_2 == null || ALGOLIA_API_KEY_2.isEmpty()) {
      throw new Exception("ALGOLIA_API_KEY_2 is not defined or empty");
    }

    client = createInstance(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1);
  }

  public abstract AsyncAPIClient createInstance(String appId, String apiKey);

  protected <T> ListAssert<T> futureAssertThat(CompletableFuture<List<T>> future) throws Exception {
    return assertThat(future.get(WAIT_TIME_IN_SECONDS, SECONDS));
  }

  protected <T> AbstractObjectAssert<?, T> futureObjectAssertThat(CompletableFuture<T> future)
      throws Exception {
    return assertThat(future.get(WAIT_TIME_IN_SECONDS, SECONDS));
  }

  public <T> void waitForCompletion(CompletableFuture<? extends AsyncGenericTask<T>> future)
      throws Exception {
    client.waitTask(future.get(WAIT_TIME_IN_SECONDS, SECONDS), WAIT_TIME_IN_SECONDS);
  }
}
