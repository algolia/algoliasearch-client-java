package com.algolia.search.objects.tasks.async;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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

  @Override
  public AsyncTask setTaskID(Long taskID) {
    super.setTaskID(taskID);
    return this;
  }

  @Override
  public String toString() {
    return "AsyncTask{" + "indexName='" + indexName + '\'' + ", taskID=" + taskID + '}';
  }
}
