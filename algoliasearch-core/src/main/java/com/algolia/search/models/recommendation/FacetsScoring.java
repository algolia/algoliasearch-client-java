package com.algolia.search.models.recommendation;

import java.io.Serializable;

@SuppressWarnings("unused")
public class FacetsScoring implements Serializable {

  public FacetsScoring() {}

  public FacetsScoring(String facetName, Integer score) {
    this.facetName = facetName;
    this.score = score;
  }

  public String getFacetName() {
    return facetName;
  }

  public FacetsScoring setFacetName(String facetName) {
    this.facetName = facetName;
    return this;
  }

  public int getScore() {
    return score;
  }

  public FacetsScoring setScore(int score) {
    this.score = score;
    return this;
  }

  private String facetName;
  private int score;
}
