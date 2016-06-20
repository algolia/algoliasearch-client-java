package com.algolia.search.objects;

public enum LogType {

  // all query logs
  LOG_QUERY("query"),

  // all build logs
  LOG_BUILD("build"),

  // all error logs
  LOG_ERROR("error"),

  // all logs
  LOG_ALL("all");

  private final String name;

  LogType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
