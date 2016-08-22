package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SynonymQuery {

  private String query;
  private List<String> types;
  private Integer page;
  private Integer hitsPerPage;

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

  public List<String> getTypes() {
    return types;
  }

  public SynonymQuery setTypes(List<String> types) {
    this.types = types;
    return this;
  }

  public Integer getPage() {
    return page;
  }

  public SynonymQuery setPage(Integer page) {
    this.page = page;
    return this;
  }

  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public SynonymQuery setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }
}
