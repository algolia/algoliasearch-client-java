package com.algolia.search.objects.tasks.sync;

import com.algolia.search.APIClient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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

  @Override
  public String toString() {
    return "TaskSingleIndex{"
        + "objectIDs="
        + objectIDs
        + ", indexName='"
        + indexName
        + '\''
        + ", taskID="
        + taskID
        + '}';
  }
}
