package com.algolia.search.models.recommendation;

import java.util.Map;

/** User profile built from Personalization strategy. */
public class PersonalizationProfileResponse {

  /** The user token representing the user and associated data. */
  private String userToken;
  /** The last processed event timestamp using the ISO 8601 format. */
  private String lastEventAt;
  /**
   * The profile is structured by facet name used in the strategy. Each facet value is mapped to its
   * score. Each score represents the user affinity for a specific facet value given the {@link
   * #userToken} past events and the Personalization strategy defined. Scores are bounded to 20.
   */
  private Map<String, Object> scores;

  public String getUserToken() {
    return userToken;
  }

  public PersonalizationProfileResponse setUserToken(String userToken) {
    this.userToken = userToken;
    return this;
  }

  public String getLastEventAt() {
    return lastEventAt;
  }

  public PersonalizationProfileResponse setLastEventAt(String lastEventAt) {
    this.lastEventAt = lastEventAt;
    return this;
  }

  public Map<String, Object> getScores() {
    return scores;
  }

  public PersonalizationProfileResponse setScores(Map<String, Object> scores) {
    this.scores = scores;
    return this;
  }
}
