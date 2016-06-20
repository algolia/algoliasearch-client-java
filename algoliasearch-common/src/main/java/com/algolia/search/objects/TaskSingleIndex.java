package com.algolia.search.objects;

import com.algolia.search.APIClient;

import java.util.List;

public class TaskSingleIndex extends Task {

  private List<String> objectIDs;

  @Override
  public TaskSingleIndex setAttributes(String indexName, APIClient apiClient) {
    super.setAttributes(indexName, apiClient);
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
}
