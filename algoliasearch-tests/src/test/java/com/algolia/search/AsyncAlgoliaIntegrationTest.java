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
  protected String ALGOLIA_APPLICATION_ID = System.getenv("ALGOLIA_APPLICATION_ID");
  protected String ALGOLIA_API_KEY = System.getenv("ALGOLIA_API_KEY");

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
    String uniqueIndexName = "test-" + UUID.randomUUID().toString();
    indexNameToDeleteAfterTheTests.add(uniqueIndexName);
    return client.initIndex(uniqueIndexName, klass);
  }

  protected static AsyncAnalytics createAnalytics() {
    return client.initAnalytics();
  }

  protected static AsyncIndex<Object> createIndex() {
    return createIndex(Object.class);
  }

  @Before
  public void checkEnvVariables() throws Exception {
    if (ALGOLIA_APPLICATION_ID == null || ALGOLIA_APPLICATION_ID.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID is not defined or empty");
    }
    if (ALGOLIA_API_KEY == null || ALGOLIA_API_KEY.isEmpty()) {
      throw new Exception("ALGOLIA_API_KEY is not defined or empty");
    }
    client = createInstance(ALGOLIA_APPLICATION_ID, ALGOLIA_API_KEY);
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
