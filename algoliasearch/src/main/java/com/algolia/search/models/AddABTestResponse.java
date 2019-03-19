package com.algolia.search.models;

import java.io.Serializable;

public class AddABTestResponse implements Serializable {
  public long getAbTestID() {
    return abTestID;
  }

  public AddABTestResponse setAbTestID(long abTestID) {
    this.abTestID = abTestID;
    return this;
  }

  public long getTaskID() {
    return taskID;
  }

  public AddABTestResponse setTaskID(long taskID) {
    this.taskID = taskID;
    return this;
  }

  public String getIndex() {
    return index;
  }

  public AddABTestResponse setIndex(String index) {
    this.index = index;
    return this;
  }

  private long abTestID;
  private long taskID;
  private String index;
}
