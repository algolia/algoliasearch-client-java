package com.algolia.search.objects.tasks.async;

public class AsyncTask extends AsyncGenericTask<Long> {

  @Override
  public Long getTaskIDToWaitFor() {
    return getTaskID();
  }

  @Override
  public AsyncTask setIndex(String indexName) {
    super.setIndex(indexName);
    return this;
  }
}
