package com.algolia.search.models;

public enum HttpMethod {
  GET("GET"),
  POST("POST"),
  PUT("PUT"),
  DELETE("DELETE");

  public final String name;

  HttpMethod(String name) {
    this.name = name;
  }
}
