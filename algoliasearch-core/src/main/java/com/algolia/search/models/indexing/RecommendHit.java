package com.algolia.search.models.indexing;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;

/** Recommend hit, similar to a search hit but associated with a score. */
public interface RecommendHit {

  /** Confidence score of the recommended item, the closer it’s to 100, the more relevant. */
  @Nonnull
  @JsonProperty("_score")
  Float getScore();
}
