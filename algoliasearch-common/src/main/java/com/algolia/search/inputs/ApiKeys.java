package com.algolia.search.inputs;

import com.algolia.search.objects.ApiKey;

import java.util.List;

public class ApiKeys {

  private List<ApiKey> keys;

  public List<ApiKey> getKeys() {
    return keys;
  }

  @SuppressWarnings("unused")
  public ApiKeys setKeys(List<ApiKey> keys) {
    this.keys = keys;
    return this;
  }
}
