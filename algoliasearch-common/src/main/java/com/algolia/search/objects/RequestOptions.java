package com.algolia.search.objects;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class RequestOptions {

  public static final RequestOptions empty = new RequestOptions();

  private final Map<String, String> headers = new HashMap<>();
  private final Map<String, String> queryParams = new HashMap<>();
  private String forwardedFor;

  public String getForwardedFor() {
    return forwardedFor;
  }

  public RequestOptions setForwardedFor(@Nonnull String forwardedFor) {
    this.forwardedFor = forwardedFor;
    return this;
  }

  public RequestOptions setCustomHeader(@Nonnull String key, @Nonnull String value) {
    headers.put(key, value);
    return this;
  }

  public RequestOptions setCustomQueryParameters(@Nonnull String key, @Nonnull String value) {
    queryParams.put(key, value);
    return this;
  }

  public Map<String, String> generateHeaders() {
    if(forwardedFor != null) {
      headers.put("X-Forwarded-For", forwardedFor);
    }
    return headers;
  }

  public Map<String, String> generateQueryParams() {
    return queryParams;
  }

}
