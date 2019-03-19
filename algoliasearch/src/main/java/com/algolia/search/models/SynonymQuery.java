package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SynonymQuery implements Serializable {

  private String query;
  private List<String> type;
  private Long page;
  private Long hitsPerPage;

  public SynonymQuery(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

  public SynonymQuery setQuery(String query) {
    this.query = query;
    return this;
  }

  public List<String> getType() {
    return type;
  }

  public SynonymQuery setType(List<String> type) {
    this.type = type;
    return this;
  }

  public Long getPage() {
    return page;
  }

  @JsonSetter
  public SynonymQuery setPage(Long page) {
    this.page = page;
    return this;
  }

  public SynonymQuery setPage(Integer page) {
    return this.setPage(page.longValue());
  }

  public Long getHitsPerPage() {
    return hitsPerPage;
  }

  @JsonSetter
  public SynonymQuery setHitsPerPage(Long hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  public SynonymQuery setHitsPerPage(Integer hitsPerPage) {
    return this.setHitsPerPage(hitsPerPage.longValue());
  }

  @Override
  public String toString() {
    return "SynonymQuery{"
        + "query='"
        + query
        + '\''
        + ", type="
        + type
        + ", page="
        + page
        + ", hitsPerPage="
        + hitsPerPage
        + '}';
  }
}
