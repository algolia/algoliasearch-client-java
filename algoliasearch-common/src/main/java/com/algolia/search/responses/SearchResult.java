package com.algolia.search.responses;

import java.util.List;
import java.util.Map;

public class SearchResult<T> {

  private Integer page;

  private Integer nbHits;

  private Integer nbPages;

  private Integer hitsPerPage;

  private Integer processingTimeMS;

  private Map<String, Map<String, Integer>> facets;

  private Boolean exhaustiveFacetsCount;

  private String query;

  private String params;

  private List<T> hits;

  public List<T> getHits() {
    return hits;
  }

  @SuppressWarnings("unused")
  public SearchResult setHits(List<T> hits) {
    this.hits = hits;
    return this;
  }

  @SuppressWarnings("unused")
  public Integer getPage() {
    return page;
  }

  @SuppressWarnings("unused")
  public SearchResult setPage(Integer page) {
    this.page = page;
    return this;
  }

  @SuppressWarnings("unused")
  public Integer getNbHits() {
    return nbHits;
  }

  @SuppressWarnings("unused")
  public SearchResult setNbHits(Integer nbHits) {
    this.nbHits = nbHits;
    return this;
  }

  @SuppressWarnings("unused")
  public Integer getNbPages() {
    return nbPages;
  }

  @SuppressWarnings("unused")
  public SearchResult setNbPages(Integer nbPages) {
    this.nbPages = nbPages;
    return this;
  }

  @SuppressWarnings("unused")
  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  @SuppressWarnings("unused")
  public SearchResult setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  @SuppressWarnings("unused")
  public Integer getProcessingTimeMS() {
    return processingTimeMS;
  }

  @SuppressWarnings("unused")
  public SearchResult setProcessingTimeMS(Integer processingTimeMS) {
    this.processingTimeMS = processingTimeMS;
    return this;
  }

  @SuppressWarnings("unused")
  public Map<String, Map<String, Integer>> getFacets() {
    return facets;
  }

  @SuppressWarnings("unused")
  public SearchResult setFacets(Map<String, Map<String, Integer>> facets) {
    this.facets = facets;
    return this;
  }

  @SuppressWarnings("unused")
  public Boolean getExhaustiveFacetsCount() {
    return exhaustiveFacetsCount;
  }

  @SuppressWarnings("unused")
  public SearchResult setExhaustiveFacetsCount(Boolean exhaustiveFacetsCount) {
    this.exhaustiveFacetsCount = exhaustiveFacetsCount;
    return this;
  }

  @SuppressWarnings("unused")
  public String getQuery() {
    return query;
  }

  @SuppressWarnings("unused")
  public SearchResult setQuery(String query) {
    this.query = query;
    return this;
  }

  @SuppressWarnings("unused")
  public String getParams() {
    return params;
  }

  @SuppressWarnings("unused")
  public SearchResult setParams(String params) {
    this.params = params;
    return this;
  }

  @Override
  public String toString() {
    return "SearchResult{"
        + "page="
        + page
        + ", nbHits="
        + nbHits
        + ", nbPages="
        + nbPages
        + ", hitsPerPage="
        + hitsPerPage
        + ", processingTimeMS="
        + processingTimeMS
        + ", facets="
        + facets
        + ", exhaustiveFacetsCount="
        + exhaustiveFacetsCount
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
