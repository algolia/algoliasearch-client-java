package com.algolia.search.objects.tasks.sync;

import com.algolia.search.APIClient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskIndexing extends Task {

  private String objectID;

  @Override
  public TaskIndexing setAPIClient(APIClient apiClient) {
    super.setAPIClient(apiClient);
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

  @Override
  public TaskIndexing setIndex(String indexName) {
    super.setIndex(indexName);
    return this;
  }

  @Override
  public String toString() {
    return "TaskIndexing{"
        + "objectID='"
        + objectID
        + '\''
        + ", indexName='"
        + indexName
        + '\''
        + ", taskID="
        + taskID
        + '}';
  }
}
