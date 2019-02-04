package com.algolia.search.responses;

import java.io.Serializable;

public class RestoreApiKey implements Serializable {
  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  private String createdAt;
}
