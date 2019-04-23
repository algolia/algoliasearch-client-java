package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Hide parameter. More information:
 *
 * @see <a href="https://www.algolia.com/doc/api-client/methods/query-rules">Algolia.com</a>
 */
@SuppressWarnings("WeakerAccess")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hide {

  private String objectID;

  public Hide(String objectID) {
    this.objectID = objectID;
  }

  public Hide() {}

  public String getObjectID() {
    return objectID;
  }

  public void setObjectID(String objectID) {
    this.objectID = objectID;
  }

  @Override
  public String toString() {
    return "Hide{" + "objectID='" + objectID + '\'' + '}';
  }
}
