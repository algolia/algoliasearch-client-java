package com.algolia.search;

public interface AbstractIndex<T> {

  String getName();

  Class<T> getKlass();

  @SuppressWarnings("unused")
  class Attributes {
    private String name;
    private String createdAt;
    private String updatedAt;
    private Integer entries;
    private Integer dataSize;
    private Integer fileSize;
    private Integer lastBuildTimeS;
    private Integer numberOfPendingTask;
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

    public Integer getEntries() {
      return entries;
    }

    public Integer getDataSize() {
      return dataSize;
    }

    public Integer getFileSize() {
      return fileSize;
    }

    public Integer getLastBuildTimeS() {
      return lastBuildTimeS;
    }

    public Integer getNumberOfPendingTask() {
      return numberOfPendingTask;
    }

    public Boolean getPendingTask() {
      return pendingTask;
    }
  }

}
