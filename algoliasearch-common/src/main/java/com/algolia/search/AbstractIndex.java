package com.algolia.search;

abstract public class AbstractIndex<T> {

  abstract public String getName();

  abstract public Class<T> getKlass();

  @SuppressWarnings("unused")
  public static class Attributes {
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
