package com.algolia.search.objects.tasks.async;

import java.util.List;

public class AsyncTaskSingleIndex extends AsyncTask {

  private List<String> objectIDs;

  @SuppressWarnings("unused")
  public List<String> getObjectIDs() {
    return objectIDs;
  }

  @SuppressWarnings("unused")
  public AsyncTaskSingleIndex setObjectIDs(List<String> objectIDs) {
    this.objectIDs = objectIDs;
    return this;
  }

  @Override
  public AsyncTaskSingleIndex setIndex(String indexName) {
    super.setIndex(indexName);
    return this;
  }
}
