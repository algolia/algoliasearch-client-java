package com.algolia.search.inputs;

import com.algolia.search.objects.ApiKey;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiKeys implements Serializable {

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
