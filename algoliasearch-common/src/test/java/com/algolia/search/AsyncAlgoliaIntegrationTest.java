package com.algolia.search;

import com.algolia.search.objects.tasks.async.AsyncGenericTask;
import org.assertj.core.api.AbstractListAssert;
import org.junit.Before;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AsyncAlgoliaIntegrationTest {

  protected static final long WAIT_TIME_IN_SECONDS = 10;
  public AsyncAPIClient client;
  private String APPLICATION_ID = System.getenv("APPLICATION_ID");
  private String API_KEY = System.getenv("API_KEY");

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

  public abstract AsyncAPIClient createInstance(String appId, String apiKey);

  protected <T> AbstractListAssert<?, ? extends List<? extends T>, T> futureAssertThat(CompletableFuture<List<T>> future) throws Exception {
    return assertThat(future.get(WAIT_TIME_IN_SECONDS, SECONDS));
  }

  public <T> void waitForCompletion(CompletableFuture<? extends AsyncGenericTask<T>> future) throws Exception {
    client.waitTask(future.get(WAIT_TIME_IN_SECONDS, SECONDS), WAIT_TIME_IN_SECONDS);
  }

}
