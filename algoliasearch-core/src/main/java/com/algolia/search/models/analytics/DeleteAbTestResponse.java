package com.algolia.search.models.analytics;

import java.io.Serializable;

public class DeleteAbTestResponse implements Serializable {

  public Long getTaskID() {
    return taskID;
  }

  public DeleteAbTestResponse setTaskID(Long taskID) {
    this.taskID = taskID;
    return this;
  }

  public String getIndex() {
    return index;
  }

  public DeleteAbTestResponse setIndex(String index) {
    this.index = index;
    return this;
  }

  @Override
  public String toString() {
    return "DeleteAbTestResponse{" + "taskID=" + taskID + ", index='" + index + '\'' + '}';
  }

  private Long taskID;
  private String index;
}
