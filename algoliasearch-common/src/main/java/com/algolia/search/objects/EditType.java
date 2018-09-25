package com.algolia.search.objects;

public enum EditType {
  REMOVE("remove"),
  REPLACE("replace");

  private final String name;

  EditType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
