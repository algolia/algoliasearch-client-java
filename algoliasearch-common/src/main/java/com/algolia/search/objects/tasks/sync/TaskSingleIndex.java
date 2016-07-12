package com.algolia.search.objects.tasks.sync;

import com.algolia.search.APIClient;

import java.util.List;

public class TaskSingleIndex extends Task {

  private List<String> objectIDs;

  @Override
  public TaskSingleIndex setAPIClient(APIClient apiClient) {
    super.setAPIClient(apiClient);
    return this;
  }

  @SuppressWarnings("unused")
  public List<String> getObjectIDs() {
    return objectIDs;
  }

  @SuppressWarnings("unused")
  public TaskSingleIndex setObjectIDs(List<String> objectIDs) {
    this.objectIDs = objectIDs;
    return this;
  }

  @Override
  public TaskSingleIndex setIndex(String indexName) {
    super.setIndex(indexName);
    return this;
  }
}
