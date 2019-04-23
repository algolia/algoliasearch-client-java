package com.algolia.search.models.mcm;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchUserIdsRequest implements Serializable {
  public String getQuery() {
    return query;
  }

  public SearchUserIdsRequest setQuery(String query) {
    this.query = query;
    return this;
  }

  public String getCluster() {
    return cluster;
  }

  public SearchUserIdsRequest setCluster(String cluster) {
    this.cluster = cluster;
    return this;
  }

  public Integer getPage() {
    return page;
  }

  public SearchUserIdsRequest setPage(Integer page) {
    this.page = page;
    return this;
  }

  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public SearchUserIdsRequest setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  @Override
  public String toString() {
    return "SearchUserIdsRequest{"
        + "query='"
        + query
        + '\''
        + ", cluster='"
        + cluster
        + '\''
        + ", page="
        + page
        + ", hitsPerPage="
        + hitsPerPage
        + '}';
  }

  private String query;
  private String cluster;
  private Integer page;
  private Integer hitsPerPage;
}
