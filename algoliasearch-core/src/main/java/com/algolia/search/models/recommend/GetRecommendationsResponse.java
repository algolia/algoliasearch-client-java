package com.algolia.search.models.recommend;

import com.algolia.search.models.indexing.RecommendHit;
import com.algolia.search.models.indexing.RecommendationsResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

/** Response from Recommend API. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetRecommendationsResponse<T extends RecommendHit> implements Serializable {

  /** List of results in the order they were submitted, one per request. */
  private List<RecommendationsResult<T>> results;

  public List<RecommendationsResult<T>> getResults() {
    return results;
  }

  public GetRecommendationsResponse<T> setResults(List<RecommendationsResult<T>> results) {
    this.results = results;
    return this;
  }
}
