package com.algolia.search.objects;

import com.algolia.search.APIClient;

public class TaskIndexing extends Task {

  private String objectID;

  @Override
  public TaskIndexing setAttributes(String indexName, APIClient apiClient) {
    super.setAttributes(indexName, apiClient);

    return this;
  }

  @SuppressWarnings("unused")
  public String getObjectID() {
    return objectID;
  }

  @SuppressWarnings("unused")
  public TaskIndexing setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }
}
