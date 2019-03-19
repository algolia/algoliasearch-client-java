package com.algolia.search.models.personalization;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventScoring {
  public long getScore() {
    return score;
  }

  public EventScoring setScore(long score) {
    this.score = score;
    return this;
  }

  public String getType() {
    return type;
  }

  public EventScoring setType(String type) {
    this.type = type;
    return this;
  }

  private long score;
  private String type;
}
