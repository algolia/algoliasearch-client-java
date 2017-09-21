package com.algolia.search.responses;

public class DeleteKey {

  private String deletedAt;

  @SuppressWarnings("unused")
  public String getDeletedAt() {
    return deletedAt;
  }

  @SuppressWarnings("unused")
  public DeleteKey setDeletedAt(String deletedAt) {
    this.deletedAt = deletedAt;
    return this;
  }

  @Override
  public String toString() {
    return "DeleteKey{" + "deletedAt='" + deletedAt + '\'' + '}';
  }
}
