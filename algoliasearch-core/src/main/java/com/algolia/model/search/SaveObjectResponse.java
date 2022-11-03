// This file is generated, manual changes will be lost - read more on
// https://github.com/algolia/api-clients-automation.

package com.algolia.model.search;

import com.fasterxml.jackson.annotation.*;
import java.util.Objects;

/** SaveObjectResponse */
public class SaveObjectResponse {

  @JsonProperty("createdAt")
  private String createdAt;

  @JsonProperty("taskID")
  private Long taskID;

  @JsonProperty("objectID")
  private String objectID;

  public SaveObjectResponse setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   *
   * @return createdAt
   */
  @javax.annotation.Nonnull
  public String getCreatedAt() {
    return createdAt;
  }

  public SaveObjectResponse setTaskID(Long taskID) {
    this.taskID = taskID;
    return this;
  }

  /**
   * taskID of the task to wait for.
   *
   * @return taskID
   */
  @javax.annotation.Nonnull
  public Long getTaskID() {
    return taskID;
  }

  public SaveObjectResponse setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  /**
   * Unique identifier of the object.
   *
   * @return objectID
   */
  @javax.annotation.Nullable
  public String getObjectID() {
    return objectID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SaveObjectResponse saveObjectResponse = (SaveObjectResponse) o;
    return (
      Objects.equals(this.createdAt, saveObjectResponse.createdAt) &&
      Objects.equals(this.taskID, saveObjectResponse.taskID) &&
      Objects.equals(this.objectID, saveObjectResponse.objectID)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(createdAt, taskID, objectID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SaveObjectResponse {\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    taskID: ").append(toIndentedString(taskID)).append("\n");
    sb.append("    objectID: ").append(toIndentedString(objectID)).append("\n");
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