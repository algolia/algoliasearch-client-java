package com.algolia.search.responses;

import java.io.Serializable;

public class DeleteUserID implements Serializable {

  private String deletedAt;

  @SuppressWarnings("unused")
  public String getDeletedAt() {
    return deletedAt;
  }

  @SuppressWarnings("unused")
  public DeleteUserID setDeletedAt(String deletedAt) {
    this.deletedAt = deletedAt;
    return this;
  }

  @Override
  public String toString() {
    return "DeleteUserID{" + "deletedAt='" + deletedAt + '\'' + '}';
  }
}
