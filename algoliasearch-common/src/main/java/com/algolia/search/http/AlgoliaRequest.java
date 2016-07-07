package com.algolia.search.http;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class AlgoliaRequest<T> {

  private final HttpMethod method;
  private final boolean isSearch;
  private final List<String> path;

  private Map<String, String> parameters = Maps.newHashMap();
  private Object data = null;
  private Class<T> resultClass;
  private Class<T> collectionClass;
  private Class<?> elementClass;

  public AlgoliaRequest(HttpMethod method, boolean isSearch, List<String> path, Class<T> resultClass) {
    this.method = method;
    this.isSearch = isSearch;
    this.path = path;
    this.resultClass = resultClass;
  }

  public AlgoliaRequest(HttpMethod method, boolean isSearch, List<String> path, Class<T> collectionClass, Class<?> elementClass) {
    this.method = method;
    this.isSearch = isSearch;
    this.path = path;
    this.collectionClass = collectionClass;
    this.elementClass = elementClass;
  }

  JavaType getJavaType(TypeFactory factory) {
    if (resultClass != null) {
      return factory.constructType(resultClass);
    } else {
      return factory.constructParametricType(collectionClass, elementClass);
    }
  }


  HttpMethod getMethod() {
    return method;
  }

  public boolean isSearch() {
    return isSearch;
  }

  List<String> getPath() {
    return path;
  }

  boolean hasData() {
    return data != null;
  }

  Object getData() {
    return data;
  }

  public AlgoliaRequest<T> setData(Object data) {
    this.data = data;
    return this;
  }

  Map<String, String> getParameters() {
    return parameters;
  }

  public AlgoliaRequest<T> setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
    return this;
  }
}
