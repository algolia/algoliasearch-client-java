package com.algolia.search.models;

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

  private Long taskID;
  private String index;
}
