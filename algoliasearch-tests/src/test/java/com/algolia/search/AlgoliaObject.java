package com.algolia.search;

public class AlgoliaObject {

  private String name;
  private int age;

  public AlgoliaObject() {}

  public AlgoliaObject(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public AlgoliaObject setName(String name) {
    this.name = name;
    return this;
  }

  public int getAge() {
    return age;
  }

  public AlgoliaObject setAge(int age) {
    this.age = age;
    return this;
  }

  @Override
  public String toString() {
    return "AlgoliaObject{" + "name='" + name + '\'' + ", age=" + age + '}';
  }
}
