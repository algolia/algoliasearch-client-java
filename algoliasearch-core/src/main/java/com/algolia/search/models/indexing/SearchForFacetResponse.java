package com.algolia.search.models.indexing;

import java.io.Serializable;
import java.util.List;

public class SearchForFacetResponse implements Serializable {

  public List<FacetHit> getFacetHits() {
    return facetHits;
  }

  public SearchForFacetResponse setFacetHits(List<FacetHit> facetHits) {
    this.facetHits = facetHits;
    return this;
  }

  public Boolean getExhaustiveFacetsCount() {
    return ExhaustiveFacetsCount;
  }

  public SearchForFacetResponse setExhaustiveFacetsCount(Boolean exhaustiveFacetsCount) {
    ExhaustiveFacetsCount = exhaustiveFacetsCount;
    return this;
  }

  public Integer getProcessingTimeMS() {
    return processingTimeMS;
  }

  public SearchForFacetResponse setProcessingTimeMS(Integer processingTimeMS) {
    this.processingTimeMS = processingTimeMS;
    return this;
  }

  @Override
  public String toString() {
    return "SearchForFacetResponse{"
        + "facetHits="
        + facetHits
        + ", ExhaustiveFacetsCount="
        + ExhaustiveFacetsCount
        + ", processingTimeMS="
        + processingTimeMS
        + '}';
  }

  private List<FacetHit> facetHits;
  private Boolean ExhaustiveFacetsCount;
  private Integer processingTimeMS;
}
