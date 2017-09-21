package com.algolia.search.objects;

public enum MultiQueriesStrategy {
  NONE("none"),
  STOP_IF_ENOUGH_MATCHES("stopIfEnoughMatches");

  private final String name;

  MultiQueriesStrategy(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
