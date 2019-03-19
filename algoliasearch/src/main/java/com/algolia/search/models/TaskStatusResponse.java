package com.algolia.search.models;

import java.io.Serializable;

public class TaskStatusResponse implements Serializable {

  public String getStatus() {
    return status;
  }

  public TaskStatusResponse setStatus(String status) {
    this.status = status;
    return this;
  }

  public boolean isPendingTask() {
    return pendingTask;
  }

  public TaskStatusResponse setPendingTask(boolean pendingTask) {
    this.pendingTask = pendingTask;
    return this;
  }

  private String status;
  private boolean pendingTask;
}
