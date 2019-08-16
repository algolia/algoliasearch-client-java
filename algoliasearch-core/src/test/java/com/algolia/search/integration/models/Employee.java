package com.algolia.search.integration.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee implements Serializable {

  public Employee() {}

  public Employee(String company, String name) {
    this.company = company;
    this.name = name;
  }

  public Employee(String company, String name, String objectID) {
    this.company = company;
    this.name = name;
    this.objectID = objectID;
  }

  public String getCompany() {
    return company;
  }

  public Employee setCompany(String company) {
    this.company = company;
    return this;
  }

  public String getName() {
    return name;
  }

  public Employee setName(String name) {
    this.name = name;
    return this;
  }

  public String getQueryID() {
    return queryID;
  }

  public Employee setQueryID(String queryID) {
    this.queryID = queryID;
    return this;
  }

  public String getObjectID() {
    return objectID;
  }

  public Employee setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  private String company;
  private String name;
  private String queryID;
  private String objectID;
}
