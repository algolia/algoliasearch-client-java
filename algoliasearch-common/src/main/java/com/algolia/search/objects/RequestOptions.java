package com.algolia.search.objects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

public class RequestOptions implements Serializable {

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

  public RequestOptions addExtraHeader(@Nonnull String key, @Nonnull String value) {
    headers.put(key, value);
    return this;
  }

  public RequestOptions addExtraQueryParameters(@Nonnull String key, @Nonnull String value) {
    queryParams.put(key, value);
    return this;
  }

  public Map<String, String> generateExtraHeaders() {
    if (forwardedFor != null) {
      headers.put("X-Forwarded-For", forwardedFor);
    }
    return headers;
  }

  public Map<String, String> generateExtraQueryParams() {
    return queryParams;
  }

  @Override
  public String toString() {
    return "RequestOptions{"
        + "headers="
        + headers
        + ", queryParams="
        + queryParams
        + ", forwardedFor='"
        + forwardedFor
        + '\''
        + '}';
  }
}
