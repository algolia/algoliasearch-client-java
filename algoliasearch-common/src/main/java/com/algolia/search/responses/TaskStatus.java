package com.algolia.search.responses;

import java.io.Serializable;

public class TaskStatus implements Serializable {

  private String status;

  private Boolean pendingTask;

  public String getStatus() {
    return status;
  }

  @SuppressWarnings("unused")
  public TaskStatus setStatus(String status) {
    this.status = status;
    return this;
  }

  @SuppressWarnings("unused")
  public Boolean getPendingTask() {
    return pendingTask;
  }

  @SuppressWarnings("unused")
  public TaskStatus setPendingTask(Boolean pendingTask) {
    this.pendingTask = pendingTask;
    return this;
  }

  @Override
  public String toString() {
    return "TaskStatus{" + "status='" + status + '\'' + ", pendingTask=" + pendingTask + '}';
  }
}
