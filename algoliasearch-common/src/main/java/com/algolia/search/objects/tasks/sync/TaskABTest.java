package com.algolia.search.objects.tasks.sync;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskABTest extends GenericTask<Long> {

  public int abTestID;
  public String index;

  public TaskABTest setIndex(String index) {
    this.index = index;
    super.setIndex(index);
    return this;
  }

  @Override
  public Long getTaskIDToWaitFor() {
    return getTaskID();
  }
}
