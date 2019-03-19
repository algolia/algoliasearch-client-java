package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacetScoring {

  public long getScore() {
    return score;
  }

  public FacetScoring setScore(long score) {
    this.score = score;
    return this;
  }

  private long score;
}
