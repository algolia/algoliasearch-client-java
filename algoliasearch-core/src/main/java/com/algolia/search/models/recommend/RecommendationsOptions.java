package com.algolia.search.models.recommend;

import com.algolia.search.models.indexing.Query;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class RecommendationsOptions {

  /** name of the index to target */
  String indexName;

  /** the recommendation model to use */
  String model;

  /** the [ObjectID] to get recommendations for */
  String objectID;

  /**
   * The threshold to use when filtering recommendations by their score,
   * default 0, between 0 and 100
   */
  Integer threshold;

  /** The maximum number of recommendations to retrieve */
  Integer maxRecommendations;

  /** search parameters to filter the recommendations,  */
  Query queryParameters;

  /** search parameters to use as fallback when there are no recommendations */
  Query fallbackParameters;
}
