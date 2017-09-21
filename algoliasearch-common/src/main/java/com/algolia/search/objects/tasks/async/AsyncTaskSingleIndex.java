package com.algolia.search.objects.tasks.async;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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

  @Override
  public String toString() {
    return "AsyncTaskSingleIndex{"
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
