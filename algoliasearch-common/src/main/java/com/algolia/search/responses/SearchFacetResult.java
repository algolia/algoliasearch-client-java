package com.algolia.search.responses;

import java.io.Serializable;
import java.util.List;

public class SearchFacetResult implements Serializable {

  private List<FacetHit> facetHits;
  private Boolean exhaustiveFacetsCount;
  private Long processingTimeMS;

  public List<FacetHit> getFacetHits() {
    return facetHits;
  }

  public SearchFacetResult setFacetHits(List<FacetHit> facetHits) {
    this.facetHits = facetHits;
    return this;
  }

  public Boolean getExhaustiveFacetsCount() {
    return exhaustiveFacetsCount;
  }

  public SearchFacetResult setExhaustiveFacetsCount(Boolean exhaustiveFacetsCount) {
    this.exhaustiveFacetsCount = exhaustiveFacetsCount;
    return this;
  }

  public Long getProcessingTimeMS() {
    return processingTimeMS;
  }

  public SearchFacetResult setProcessingTimeMS(Long processingTimeMS) {
    this.processingTimeMS = processingTimeMS;
    return this;
  }

  @Override
  public String toString() {
    return "SearchFacetResult{"
        + "facetHits="
        + facetHits
        + ", exhaustiveFacetsCount="
        + exhaustiveFacetsCount
        + ", processingTimeMS="
        + processingTimeMS
        + '}';
  }
}
