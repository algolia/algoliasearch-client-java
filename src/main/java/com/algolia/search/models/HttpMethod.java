package com.algolia.search.models;

@SuppressWarnings("WeakerAccess")
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
