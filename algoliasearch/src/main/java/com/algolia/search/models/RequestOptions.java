package com.algolia.search.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

public class RequestOptions implements Serializable {

  private final Map<String, String> headers = new HashMap<>();
  private final Map<String, String> queryParams = new HashMap<>();

  public void addExtraHeader(@Nonnull String key, @Nonnull String value) {
    headers.put(key, value);
  }

  public RequestOptions addExtraQueryParameters(@Nonnull String key, @Nonnull String value) {
    queryParams.put(key, value);
    return this;
  }

  public Map<String, String> getExtraHeaders() {
    return headers;
  }

  public Map<String, String> getExtraQueryParams() {
    return queryParams;
  }

  @Override
  public String toString() {
    return "RequestOptions{" + "headers=" + headers + ", queryParams=" + queryParams + '\'' + '}';
  }
}
