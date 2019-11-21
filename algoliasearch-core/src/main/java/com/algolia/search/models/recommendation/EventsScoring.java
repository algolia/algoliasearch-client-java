package com.algolia.search.models.recommendation;

import java.io.Serializable;

@SuppressWarnings("unused")
public class EventsScoring implements Serializable {

  public EventsScoring() {}

  public EventsScoring(String eventName, String eventType, Integer score) {
    this.eventName = eventName;
    this.eventType = eventType;
    this.score = score;
  }

  public String getEventName() {
    return eventName;
  }

  public EventsScoring setEventName(String eventName) {
    this.eventName = eventName;
    return this;
  }

  public String getEventType() {
    return eventType;
  }

  public EventsScoring setEventType(String eventType) {
    this.eventType = eventType;
    return this;
  }

  public int getScore() {
    return score;
  }

  public EventsScoring setScore(int score) {
    this.score = score;
    return this;
  }

  private String eventName;
  private String eventType;
  private int score;
}
