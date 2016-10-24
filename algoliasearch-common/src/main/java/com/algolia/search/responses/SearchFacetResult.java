package com.algolia.search.responses;

import java.util.List;

public class SearchFacetResult {

  private List<FacetHit> facetHits;

  public List<FacetHit> getFacetHits() {
    return facetHits;
  }

  public SearchFacetResult setFacetHits(List<FacetHit> facetHits) {
    this.facetHits = facetHits;
    return this;
  }
}
