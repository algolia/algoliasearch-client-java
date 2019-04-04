package com.algolia.search.integration.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlgoliaMultipleOpObject implements Serializable {
  // dummy constructor for serialization
  public AlgoliaMultipleOpObject() {}

  public AlgoliaMultipleOpObject(String firstName) {
    this.firstName = firstName;
  }

  public String getObjectID() {
    return objectID;
  }

  public void setObjectID(String objectID) {
    this.objectID = objectID;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  private String objectID;
  private String firstName;
}
