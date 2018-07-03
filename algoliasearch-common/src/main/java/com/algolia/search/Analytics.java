package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.analytics.ABTest;
import com.algolia.search.objects.tasks.sync.TaskABTest;
import com.algolia.search.responses.ABTests;
import java.io.Serializable;

public class Analytics implements Serializable {

  private APIClient client;

  Analytics(APIClient client) {
    this.client = client;
  }

  public TaskABTest addABTest(ABTest abtest) throws AlgoliaException {
    return client.addABTest(abtest);
  }

  public TaskABTest stopABTest(long id) throws AlgoliaException {
    return client.stopABTest(id);
  }

  public TaskABTest deleteABTest(long id) throws AlgoliaException {
    return client.deleteABTest(id);
  }

  public ABTest getABTest(long id) throws AlgoliaException {
    return client.getABTest(id);
  }

  public ABTests getABTests(int offset, int limit) throws AlgoliaException {
    return client.getABTests(offset, limit);
  }

  public void waitTask(TaskABTest task) throws AlgoliaException {
    this.client.waitTask(task);
  }
}
