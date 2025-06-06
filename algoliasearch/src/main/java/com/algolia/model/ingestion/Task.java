// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.ingestion;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.Objects;

/** Task */
public class Task {

  @JsonProperty("taskID")
  private String taskID;

  @JsonProperty("sourceID")
  private String sourceID;

  @JsonProperty("destinationID")
  private String destinationID;

  @JsonProperty("cron")
  private String cron;

  @JsonProperty("lastRun")
  private String lastRun;

  @JsonProperty("nextRun")
  private String nextRun;

  @JsonProperty("owner")
  private String owner;

  @JsonProperty("input")
  private TaskInput input;

  @JsonProperty("enabled")
  private Boolean enabled;

  @JsonProperty("failureThreshold")
  private Integer failureThreshold;

  @JsonProperty("action")
  private ActionType action;

  @JsonProperty("subscriptionAction")
  private ActionType subscriptionAction;

  @JsonProperty("cursor")
  private String cursor;

  @JsonProperty("notifications")
  private Notifications notifications;

  @JsonProperty("policies")
  private Policies policies;

  @JsonProperty("createdAt")
  private String createdAt;

  @JsonProperty("updatedAt")
  private String updatedAt;

  public Task setTaskID(String taskID) {
    this.taskID = taskID;
    return this;
  }

  /** Universally unique identifier (UUID) of a task. */
  @javax.annotation.Nonnull
  public String getTaskID() {
    return taskID;
  }

  public Task setSourceID(String sourceID) {
    this.sourceID = sourceID;
    return this;
  }

  /** Universally uniqud identifier (UUID) of a source. */
  @javax.annotation.Nonnull
  public String getSourceID() {
    return sourceID;
  }

  public Task setDestinationID(String destinationID) {
    this.destinationID = destinationID;
    return this;
  }

  /** Universally unique identifier (UUID) of a destination resource. */
  @javax.annotation.Nonnull
  public String getDestinationID() {
    return destinationID;
  }

  public Task setCron(String cron) {
    this.cron = cron;
    return this;
  }

  /** Cron expression for the task's schedule. */
  @javax.annotation.Nullable
  public String getCron() {
    return cron;
  }

  public Task setLastRun(String lastRun) {
    this.lastRun = lastRun;
    return this;
  }

  /** The last time the scheduled task ran in RFC 3339 format. */
  @javax.annotation.Nullable
  public String getLastRun() {
    return lastRun;
  }

  public Task setNextRun(String nextRun) {
    this.nextRun = nextRun;
    return this;
  }

  /** The next scheduled run of the task in RFC 3339 format. */
  @javax.annotation.Nullable
  public String getNextRun() {
    return nextRun;
  }

  public Task setOwner(String owner) {
    this.owner = owner;
    return this;
  }

  /** Owner of the resource. */
  @javax.annotation.Nullable
  public String getOwner() {
    return owner;
  }

  public Task setInput(TaskInput input) {
    this.input = input;
    return this;
  }

  /** Get input */
  @javax.annotation.Nullable
  public TaskInput getInput() {
    return input;
  }

  public Task setEnabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /** Whether the task is enabled. */
  @javax.annotation.Nonnull
  public Boolean getEnabled() {
    return enabled;
  }

  public Task setFailureThreshold(Integer failureThreshold) {
    this.failureThreshold = failureThreshold;
    return this;
  }

  /**
   * Maximum accepted percentage of failures for a task run to finish successfully. minimum: 0
   * maximum: 100
   */
  @javax.annotation.Nullable
  public Integer getFailureThreshold() {
    return failureThreshold;
  }

  public Task setAction(ActionType action) {
    this.action = action;
    return this;
  }

  /** Get action */
  @javax.annotation.Nullable
  public ActionType getAction() {
    return action;
  }

  public Task setSubscriptionAction(ActionType subscriptionAction) {
    this.subscriptionAction = subscriptionAction;
    return this;
  }

  /** Get subscriptionAction */
  @javax.annotation.Nullable
  public ActionType getSubscriptionAction() {
    return subscriptionAction;
  }

  public Task setCursor(String cursor) {
    this.cursor = cursor;
    return this;
  }

  /** Date of the last cursor in RFC 3339 format. */
  @javax.annotation.Nullable
  public String getCursor() {
    return cursor;
  }

  public Task setNotifications(Notifications notifications) {
    this.notifications = notifications;
    return this;
  }

  /** Get notifications */
  @javax.annotation.Nullable
  public Notifications getNotifications() {
    return notifications;
  }

  public Task setPolicies(Policies policies) {
    this.policies = policies;
    return this;
  }

  /** Get policies */
  @javax.annotation.Nullable
  public Policies getPolicies() {
    return policies;
  }

  public Task setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /** Date of creation in RFC 3339 format. */
  @javax.annotation.Nonnull
  public String getCreatedAt() {
    return createdAt;
  }

  public Task setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /** Date of last update in RFC 3339 format. */
  @javax.annotation.Nonnull
  public String getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Task task = (Task) o;
    return (
      Objects.equals(this.taskID, task.taskID) &&
      Objects.equals(this.sourceID, task.sourceID) &&
      Objects.equals(this.destinationID, task.destinationID) &&
      Objects.equals(this.cron, task.cron) &&
      Objects.equals(this.lastRun, task.lastRun) &&
      Objects.equals(this.nextRun, task.nextRun) &&
      Objects.equals(this.owner, task.owner) &&
      Objects.equals(this.input, task.input) &&
      Objects.equals(this.enabled, task.enabled) &&
      Objects.equals(this.failureThreshold, task.failureThreshold) &&
      Objects.equals(this.action, task.action) &&
      Objects.equals(this.subscriptionAction, task.subscriptionAction) &&
      Objects.equals(this.cursor, task.cursor) &&
      Objects.equals(this.notifications, task.notifications) &&
      Objects.equals(this.policies, task.policies) &&
      Objects.equals(this.createdAt, task.createdAt) &&
      Objects.equals(this.updatedAt, task.updatedAt)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      taskID,
      sourceID,
      destinationID,
      cron,
      lastRun,
      nextRun,
      owner,
      input,
      enabled,
      failureThreshold,
      action,
      subscriptionAction,
      cursor,
      notifications,
      policies,
      createdAt,
      updatedAt
    );
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Task {\n");
    sb.append("    taskID: ").append(toIndentedString(taskID)).append("\n");
    sb.append("    sourceID: ").append(toIndentedString(sourceID)).append("\n");
    sb.append("    destinationID: ").append(toIndentedString(destinationID)).append("\n");
    sb.append("    cron: ").append(toIndentedString(cron)).append("\n");
    sb.append("    lastRun: ").append(toIndentedString(lastRun)).append("\n");
    sb.append("    nextRun: ").append(toIndentedString(nextRun)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    input: ").append(toIndentedString(input)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
    sb.append("    failureThreshold: ").append(toIndentedString(failureThreshold)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    subscriptionAction: ").append(toIndentedString(subscriptionAction)).append("\n");
    sb.append("    cursor: ").append(toIndentedString(cursor)).append("\n");
    sb.append("    notifications: ").append(toIndentedString(notifications)).append("\n");
    sb.append("    policies: ").append(toIndentedString(policies)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
