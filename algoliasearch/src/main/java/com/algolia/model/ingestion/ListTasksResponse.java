// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.ingestion;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** A list of tasks with pagination details. */
public class ListTasksResponse {

  @JsonProperty("tasks")
  private List<Task> tasks = new ArrayList<>();

  @JsonProperty("pagination")
  private Pagination pagination;

  public ListTasksResponse setTasks(List<Task> tasks) {
    this.tasks = tasks;
    return this;
  }

  public ListTasksResponse addTasks(Task tasksItem) {
    this.tasks.add(tasksItem);
    return this;
  }

  /** Get tasks */
  @javax.annotation.Nonnull
  public List<Task> getTasks() {
    return tasks;
  }

  public ListTasksResponse setPagination(Pagination pagination) {
    this.pagination = pagination;
    return this;
  }

  /** Get pagination */
  @javax.annotation.Nonnull
  public Pagination getPagination() {
    return pagination;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListTasksResponse listTasksResponse = (ListTasksResponse) o;
    return Objects.equals(this.tasks, listTasksResponse.tasks) && Objects.equals(this.pagination, listTasksResponse.pagination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tasks, pagination);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListTasksResponse {\n");
    sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
    sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
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