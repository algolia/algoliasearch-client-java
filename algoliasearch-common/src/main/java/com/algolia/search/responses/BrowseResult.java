package com.algolia.search.responses;

import com.fasterxml.jackson.annotation.JsonSetter;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class BrowseResult<T> implements Serializable {

  private String cursor;
  private Long processingTimeMS;
  private String query;
  private String params;
  private List<T> hits;

  public static <T> BrowseResult<T> empty() {
    return new BrowseResult<T>().setCursor(null).setHits(Collections.emptyList());
  }

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

  public Long getProcessingTimeMS() {
    return processingTimeMS;
  }

  @JsonSetter
  public BrowseResult<T> setProcessingTimeMS(Long processingTimeMS) {
    this.processingTimeMS = processingTimeMS;
    return this;
  }

  public BrowseResult<T> setProcessingTimeMS(Integer processingTimeMS) {
    return this.setProcessingTimeMS(processingTimeMS.longValue());
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

  @Override
  public String toString() {
    return "BrowseResult{"
        + "cursor='"
        + cursor
        + '\''
        + ", processingTimeMS="
        + processingTimeMS
        + ", query='"
        + query
        + '\''
        + ", params='"
        + params
        + '\''
        + ", hits="
        + hits
        + '}';
  }
}
