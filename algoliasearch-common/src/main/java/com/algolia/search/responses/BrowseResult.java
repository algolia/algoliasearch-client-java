package com.algolia.search.responses;

import java.util.Collections;
import java.util.List;

public class BrowseResult<T> {

  private String cursor;
  private Integer processingTimeMS;
  private String query;
  private String params;
  private List<T> hits;

  public String getCursor() {
    return cursor;
  }

  public BrowseResult<T> setCursor(String cursor) {
    this.cursor = cursor;
    return this;
  }

  public List<T> getHits() {
    return hits;
  }

  public BrowseResult<T> setHits(List<T> hits) {
    this.hits = hits;
    return this;
  }

  public Integer getProcessingTimeMS() {
    return processingTimeMS;
  }

  public BrowseResult<T> setProcessingTimeMS(Integer processingTimeMS) {
    this.processingTimeMS = processingTimeMS;
    return this;
  }

  public String getQuery() {
    return query;
  }

  public BrowseResult<T> setQuery(String query) {
    this.query = query;
    return this;
  }

  public String getParams() {
    return params;
  }

  public BrowseResult<T> setParams(String params) {
    this.params = params;
    return this;
  }

  public static <T> BrowseResult<T> empty() {
    return new BrowseResult<T>().setCursor(null).setHits(Collections.emptyList());
  }

  @Override
  public String toString() {
    return "BrowseResult{" +
      "cursor='" + cursor + '\'' +
      ", processingTimeMS=" + processingTimeMS +
      ", query='" + query + '\'' +
      ", params='" + params + '\'' +
      ", hits=" + hits +
      '}';
  }
}
