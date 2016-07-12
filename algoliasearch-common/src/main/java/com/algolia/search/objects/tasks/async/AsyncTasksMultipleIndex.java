package com.algolia.search.objects.tasks.async;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AsyncTasksMultipleIndex extends AsyncGenericTask<Map<String, Long>> {

  private List<String> objectIDs;
  private String createdAt;

  @SuppressWarnings("unused")
  public List<String> getObjectIDs() {
    return objectIDs;
  }

  @SuppressWarnings("unused")
  public AsyncTasksMultipleIndex setObjectIDs(List<String> objectIDs) {
    this.objectIDs = objectIDs;
    return this;
  }

  @SuppressWarnings("unused")
  public String getCreatedAt() {
    return createdAt;
  }

  @SuppressWarnings("unused")
  public AsyncTasksMultipleIndex setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Override
  public Long getTaskIDToWaitFor() {
    return getTaskID().values().stream().max(Comparator.comparingLong(Long::longValue)).get();
  }

  public AsyncTasksMultipleIndex computeIndex() {
    String name = null;
    long objectID = 0;
    for (Map.Entry<String, Long> entry : getTaskID().entrySet()) {
      if (entry.getValue() > objectID) {
        objectID = entry.getValue();
        name = entry.getKey();
      }
    }

    super.setIndex(name);
    return this;
  }
}
