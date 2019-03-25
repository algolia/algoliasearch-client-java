package com.algolia.search.integration.models;

import java.io.Serializable;

public class CopyIndexTestObject implements Serializable {

  public CopyIndexTestObject() {}

  public CopyIndexTestObject(String objectID, String company) {
    this.objectID = objectID;
    this.company = company;
  }

  public String getObjectID() {
    return objectID;
  }

  public CopyIndexTestObject setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  public String getCompany() {
    return company;
  }

  public CopyIndexTestObject setCompany(String company) {
    this.company = company;
    return this;
  }

  private String objectID;
  private String company;
}
