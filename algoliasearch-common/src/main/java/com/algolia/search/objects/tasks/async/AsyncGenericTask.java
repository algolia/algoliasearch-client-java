package com.algolia.search.objects.tasks.async;

import com.algolia.search.objects.tasks.AbstractTask;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AsyncGenericTask<T> extends AbstractTask<T> {

  @Override
  public AsyncGenericTask<T> setTaskID(T taskID) {
    super.setTaskID(taskID);
    return this;
  }

  @Override
  public AsyncGenericTask<T> setIndex(String indexName) {
    super.setIndex(indexName);
    return this;
  }
}
