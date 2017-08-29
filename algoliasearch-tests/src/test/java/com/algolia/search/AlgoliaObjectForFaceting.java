package com.algolia.search;

public class AlgoliaObjectForFaceting {

  private String name;
  private int age;
  private String series;

  public AlgoliaObjectForFaceting() {}

  public AlgoliaObjectForFaceting(String name, int age, String series) {
    this.name = name;
    this.age = age;
    this.series = series;
  }

  public String getName() {
    return name;
  }

  public AlgoliaObjectForFaceting setName(String name) {
    this.name = name;
    return this;
  }

  public int getAge() {
    return age;
  }

  public AlgoliaObjectForFaceting setAge(int age) {
    this.age = age;
    return this;
  }

  public String getSeries() {
    return series;
  }

  public AlgoliaObjectForFaceting setSeries(String series) {
    this.series = series;
    return this;
  }
}
