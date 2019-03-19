package com.algolia.search.models.analytics;

import java.io.Serializable;

public class StopAbTestResponse implements Serializable {
  public Long getTaskID() {
    return taskID;
  }

  public StopAbTestResponse setTaskID(Long taskID) {
    this.taskID = taskID;
    return this;
  }

  public String getIndex() {
    return index;
  }

  public StopAbTestResponse setIndex(String index) {
    this.index = index;
    return this;
  }

  private Long taskID;
  private String index;
}
