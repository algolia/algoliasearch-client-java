package com.algolia.search.models.personalization;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacetScoring implements Serializable {

  public FacetScoring() {
  }

  public FacetScoring(long score) {
    this.score = score;
  }

  public long getScore() {
    return score;
  }

  public FacetScoring setScore(long score) {
    this.score = score;
    return this;
  }

  private long score;
}
