package com.algolia.search.models.indexing;

import com.algolia.search.models.analytics.ABTest;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class IndicesResponse implements Serializable {
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

  public long getEntries() {
    return entries;
  }

  public IndicesResponse setEntries(long entries) {
    this.entries = entries;
    return this;
  }

  public long getDataSize() {
    return dataSize;
  }

  public IndicesResponse setDataSize(long dataSize) {
    this.dataSize = dataSize;
    return this;
  }

  public long getFileSize() {
    return fileSize;
  }

  public IndicesResponse setFileSize(long fileSize) {
    this.fileSize = fileSize;
    return this;
  }

  public long getLastBuildTimes() {
    return lastBuildTimes;
  }

  public IndicesResponse setLastBuildTimes(long lastBuildTimes) {
    this.lastBuildTimes = lastBuildTimes;
    return this;
  }

  public long getNumberOfPendingTasks() {
    return numberOfPendingTasks;
  }

  public IndicesResponse setNumberOfPendingTasks(long numberOfPendingTasks) {
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

  public List<String> getReplicas() {
    return replicas;
  }

  public IndicesResponse setReplicas(List<String> replicas) {
    this.replicas = replicas;
    return this;
  }

  public String getPrimary() {
    return primary;
  }

  public IndicesResponse setPrimary(String primary) {
    this.primary = primary;
    return this;
  }

  public String getSourceABTest() {
    return sourceABTest;
  }

  public IndicesResponse setSourceABTest(String sourceABTest) {
    this.sourceABTest = sourceABTest;
    return this;
  }

  public ABTest getAbTest() {
    return abTest;
  }

  public IndicesResponse setAbTest(ABTest abTest) {
    this.abTest = abTest;
    return this;
  }

  private String name;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private long entries;
  private long dataSize;
  private long fileSize;
  private long lastBuildTimes;
  private long numberOfPendingTasks;
  private boolean pendingTask;
  private List<String> replicas;
  private String primary;
  private String sourceABTest;
  private ABTest abTest;
}
