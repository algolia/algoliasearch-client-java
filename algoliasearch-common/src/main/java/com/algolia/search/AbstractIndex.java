package com.algolia.search;

public interface AbstractIndex<T> {

  String getName();

  Class<T> getKlass();

  @SuppressWarnings("unused")
  class Attributes {
    private String name;
    private String createdAt;
    private String updatedAt;
    private Long entries;
    private Long dataSize;
    private Long fileSize;
    private Long lastBuildTimeS;
    private Long numberOfPendingTask;
    private Boolean pendingTask;

    public String getName() {
      return name;
    }

    public String getCreatedAt() {
      return createdAt;
    }

    public String getUpdatedAt() {
      return updatedAt;
    }

    public Long getEntries() {
      return entries;
    }

    public Long getDataSize() {
      return dataSize;
    }

    public Long getFileSize() {
      return fileSize;
    }

    public Long getLastBuildTimeS() {
      return lastBuildTimeS;
    }

    public Long getNumberOfPendingTask() {
      return numberOfPendingTask;
    }

    public Boolean getPendingTask() {
      return pendingTask;
    }
  }
}
