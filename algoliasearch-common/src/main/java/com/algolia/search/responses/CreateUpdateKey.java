package com.algolia.search.responses;

import java.io.Serializable;

public class CreateUpdateKey implements Serializable {

  private String key;
  private String createdAt;

  /** Name of this key */
  public String getKey() {
    return key;
  }

  public CreateUpdateKey setKey(String key) {
    this.key = key;
    return this;
  }

  @SuppressWarnings("unused")
  public String getCreatedAt() {
    return createdAt;
  }

  @SuppressWarnings("unused")
  public CreateUpdateKey setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public String toString() {
    return "CreateUpdateKey{" + "key='" + key + '\'' + ", createdAt='" + createdAt + '\'' + '}';
  }
}
