package com.algolia.search.integration.models;

import java.io.Serializable;

public class Employee implements Serializable {

  public Employee() {}

  public Employee(String company, String name) {
    this.company = company;
    this.name = name;
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

  private String company;
  private String name;
  private String queryID;
}
