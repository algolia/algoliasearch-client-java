package com.algolia.search.models.indexing;

public class RecommendationsResult<T> extends SearchResult<T> {

  private Integer score;

  public Integer getScore() {
    return score;
  }

  public RecommendationsResult<T> setScore(Integer score) {
    this.score = score;
    return this;
  }
}
