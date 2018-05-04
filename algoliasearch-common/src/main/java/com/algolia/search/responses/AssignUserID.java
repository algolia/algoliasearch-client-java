package com.algolia.search.responses;

import java.io.Serializable;

public class AssignUserID implements Serializable {

  private String createdAt;

  @SuppressWarnings("unused")
  public String getCreatedAt() {
    return createdAt;
  }

  @SuppressWarnings("unused")
  public AssignUserID setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public String toString() {
    return "AssignUserID{createdAt='" + createdAt + "'}";
  }
}
