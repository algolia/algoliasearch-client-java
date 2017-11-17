package com.algolia.search.responses;

import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class SearchResult<T> {

  private Long page;

  private Long nbHits;

  private Long nbPages;

  private Long hitsPerPage;

  private Long processingTimeMS;

  private Map<String, Map<String, Long>> facets;

  private Boolean exhaustiveFacetsCount;

  private String query;

  private String params;

  private List<T> hits;

  public List<T> getHits() {
    return hits;
  }

  public SearchResult setHits(List<T> hits) {
    this.hits = hits;
    return this;
  }

  public Long getPage() {
    return page;
  }

  public SearchResult setPage(Integer page) {
    return this.setPage(page.longValue());
  }

  @JsonSetter
  public SearchResult setPage(Long page) {
    this.page = page;
    return this;
  }

  public Long getNbHits() {
    return nbHits;
  }

  public SearchResult setNbHits(Integer nbHits) {
    return this.setNbHits(nbHits.longValue());
  }

  @JsonSetter
  public SearchResult setNbHits(Long nbHits) {
    this.nbHits = nbHits;
    return this;
  }

  public Long getNbPages() {
    return nbPages;
  }

  public SearchResult setNbPages(Integer nbPages) {
    return this.setNbPages(nbPages.longValue());
  }

  @JsonSetter
  public SearchResult setNbPages(Long nbPages) {
    this.nbPages = nbPages;
    return this;
  }

  public Long getHitsPerPage() {
    return hitsPerPage;
  }

  public SearchResult setHitsPerPage(Integer hitsPerPage) {
    return this.setHitsPerPage(hitsPerPage.longValue());
  }

  @JsonSetter
  public SearchResult setHitsPerPage(Long hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  public Long getProcessingTimeMS() {
    return processingTimeMS;
  }

  public SearchResult setProcessingTimeMS(Integer processingTimeMS) {
    return this.setProcessingTimeMS(processingTimeMS.longValue());
  }

  @JsonSetter
  public SearchResult setProcessingTimeMS(Long processingTimeMS) {
    this.processingTimeMS = processingTimeMS;
    return this;
  }

  public Map<String, Map<String, Long>> getFacets() {
    return facets;
  }

  public SearchResult setFacets(Map<String, Map<String, Long>> facets) {
    this.facets = facets;
    return this;
  }

  public Boolean getExhaustiveFacetsCount() {
    return exhaustiveFacetsCount;
  }

  public SearchResult setExhaustiveFacetsCount(Boolean exhaustiveFacetsCount) {
    this.exhaustiveFacetsCount = exhaustiveFacetsCount;
    return this;
  }

  public String getQuery() {
    return query;
  }

  public SearchResult setQuery(String query) {
    this.query = query;
    return this;
  }

  public String getParams() {
    return params;
  }

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
