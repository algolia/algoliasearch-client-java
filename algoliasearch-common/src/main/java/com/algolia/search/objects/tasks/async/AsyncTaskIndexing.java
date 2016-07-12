package com.algolia.search.objects.tasks.async;

public class AsyncTaskIndexing extends AsyncTask {

  private String objectID;

  @SuppressWarnings("unused")
  public String getObjectID() {
    return objectID;
  }

  @SuppressWarnings("unused")
  public AsyncTaskIndexing setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  @Override
  public AsyncTaskIndexing setIndex(String indexName) {
    super.setIndex(indexName);
    return this;
  }
}
