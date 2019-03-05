package com.algolia.search.models;

import java.time.OffsetDateTime;

public class IndicesResponse {
  public String getName() {
    return name;
  }

  public IndicesResponse setName(String name) {
    this.name = name;
    return this;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public IndicesResponse setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public IndicesResponse setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  public int getEntries() {
    return entries;
  }

  public IndicesResponse setEntries(int entries) {
    this.entries = entries;
    return this;
  }

  public int getDataSize() {
    return dataSize;
  }

  public IndicesResponse setDataSize(int dataSize) {
    this.dataSize = dataSize;
    return this;
  }

  public int getFileSize() {
    return fileSize;
  }

  public IndicesResponse setFileSize(int fileSize) {
    this.fileSize = fileSize;
    return this;
  }

  public int getLastBuildTimes() {
    return lastBuildTimes;
  }

  public IndicesResponse setLastBuildTimes(int lastBuildTimes) {
    this.lastBuildTimes = lastBuildTimes;
    return this;
  }

  public int getNumberOfPendingTasks() {
    return numberOfPendingTasks;
  }

  public IndicesResponse setNumberOfPendingTasks(int numberOfPendingTasks) {
    this.numberOfPendingTasks = numberOfPendingTasks;
    return this;
  }

  public boolean isPendingTask() {
    return pendingTask;
  }

  public IndicesResponse setPendingTask(boolean pendingTask) {
    this.pendingTask = pendingTask;
    return this;
  }

  private String name;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private int entries;
  private int dataSize;
  private int fileSize;
  private int lastBuildTimes;
  private int numberOfPendingTasks;
  private boolean pendingTask;
}
