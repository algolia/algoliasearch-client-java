package com.algolia.search.objects.tasks.async;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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

  @Override
  public String toString() {
    return "AsyncTaskIndexing{"
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
