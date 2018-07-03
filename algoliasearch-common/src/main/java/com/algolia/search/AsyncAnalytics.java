package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.analytics.ABTest;
import com.algolia.search.objects.tasks.async.AsyncTaskABTest;
import com.algolia.search.responses.ABTests;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

public class AsyncAnalytics implements Serializable {

  private AsyncAPIClient client;

  AsyncAnalytics(AsyncAPIClient client) {
    this.client = client;
  }

  public CompletableFuture<AsyncTaskABTest> addABTest(ABTest abtest) {
    return client.addABTest(abtest);
  }

  public CompletableFuture<AsyncTaskABTest> stopABTest(long id) {
    return client.stopABTest(id);
  }

  public CompletableFuture<AsyncTaskABTest> deleteABTest(long id) {
    return client.deleteABTest(id);
  }

  public CompletableFuture<ABTest> getABTest(long id) {
    return client.getABTest(id);
  }

  public CompletableFuture<ABTests> getABTests(int offset, int limit) {
    return client.getABTests(offset, limit);
  }

  public void waitTask(AsyncTaskABTest task) throws AlgoliaException {
    this.client.waitTask(task);
  }
}
