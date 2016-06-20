package com.algolia.search;

import com.google.api.client.http.HttpMethods;

enum HttpMethod {
  GET(HttpMethods.GET),
  POST(HttpMethods.POST),
  PUT(HttpMethods.PUT),
  DELETE(HttpMethods.DELETE);

  final String name;

  HttpMethod(String name) {
    this.name = name;
  }
}
