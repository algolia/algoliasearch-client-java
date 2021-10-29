package com.algolia.search.models.indexing;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/** Default Recommend hit implementation */
public class DefaultRecommendHit implements RecommendHit {

  private Map<String, Object> fields = new HashMap<>();
  private Float score;

  @Nonnull
  @Override
  public Float getScore() {
    return score;
  }

  public DefaultRecommendHit setScore(Float score) {
    this.score = score;
    fields.put("_score", score);
    return this;
  }

  public Map<String, Object> getFields() {
    return fields;
  }

  public DefaultRecommendHit setFields(Map<String, Object> fields) {
    this.fields = fields;
    return this;
  }

  @JsonAnySetter
  public DefaultRecommendHit setField(String key, Object value) {
    this.fields.put(key, value);
    return this;
  }
}
