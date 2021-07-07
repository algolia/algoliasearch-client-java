package com.algolia.search.models.recommendation;

/** Delete the user profile response. */
public class DeletePersonalizationProfileResponse {

  /** The user token representing the user and associated data. */
  private String userToken;
  /** Date until which the data can safely be considered as deleted for the given use. */
  private String deletedUntil;

  public String getUserToken() {
    return userToken;
  }

  public DeletePersonalizationProfileResponse setUserToken(String userToken) {
    this.userToken = userToken;
    return this;
  }

  public String getDeletedUntil() {
    return deletedUntil;
  }

  public DeletePersonalizationProfileResponse setDeletedUntil(String deletedUntil) {
    this.deletedUntil = deletedUntil;
    return this;
  }
}
