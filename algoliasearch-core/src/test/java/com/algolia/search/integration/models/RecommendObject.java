package com.algolia.search.integration.models;

import com.algolia.search.models.indexing.RecommendHit;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.annotation.Nonnull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecommendObject implements RecommendHit {

  private String objectID;
  private Float score;

  public RecommendObject() {}

  public RecommendObject(String objectID, Float score) {
    this.objectID = objectID;
    this.score = score;
  }

  public String getObjectID() {
    return objectID;
  }

  public RecommendObject setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  @Nonnull
  @Override
  public Float getScore() {
    return score;
  }
}
