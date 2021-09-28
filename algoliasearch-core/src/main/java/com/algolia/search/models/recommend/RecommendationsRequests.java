package com.algolia.search.models.recommend;

import java.util.List;

public class RecommendationsRequests<T extends RecommendationsOptions> {

  private final List<T> requests;

  public RecommendationsRequests(List<T> requests) {
    this.requests = requests;
  }

  public List<T> getRequests() {
    return requests;
  }
}
