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

  public Integer getHitsPerpage() {
    return hitsPerpage;
  }

  public SearchUserIdsRequest setHitsPerpage(Integer hitsPerpage) {
    this.hitsPerpage = hitsPerpage;
    return this;
  }

  private String query;
  private String cluster;
  private Integer page;
  private Integer hitsPerpage;
}
