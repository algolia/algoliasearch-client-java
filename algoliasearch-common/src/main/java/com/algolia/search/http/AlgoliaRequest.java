package com.algolia.search.http;

import com.algolia.search.objects.RequestOptions;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class AlgoliaRequest<T> {

  private final HttpMethod method;
  private final AlgoliaRequestKind kind;
  private final List<String> path;
  private final RequestOptions options;

  private Map<String, String> parameters = Maps.newHashMap();
  private Object data = null;
  private Class<T> resultClass;
  private Class<T> collectionClass;
  private Class<?> elementClass;

  public AlgoliaRequest(
      HttpMethod method,
      AlgoliaRequestKind kind,
      List<String> path,
      RequestOptions options,
      Class<T> resultClass) {
    this.method = method;
    this.kind = kind;
    this.path = path;
    this.options = options;
    this.resultClass = resultClass;
  }

  public AlgoliaRequest(
      HttpMethod method,
      AlgoliaRequestKind kind,
      List<String> path,
      RequestOptions options,
      Class<T> collectionClass,
      Class<?> elementClass) {
    this.method = method;
    this.kind = kind;
    this.path = path;
    this.options = options;
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
    return this.kind == AlgoliaRequestKind.SEARCH_API_READ;
  }

  public AlgoliaRequestKind getKind() {
    return kind;
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
    return ImmutableMap.<String, String>builder()
        .putAll(parameters)
        .putAll(options.generateExtraQueryParams())
        .build();
  }

  public AlgoliaRequest<T> setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
    return this;
  }

  Map<String, String> getHeaders() {
    return options.generateExtraHeaders();
  }

  public String toString(String host) {
    StringBuilder result = new StringBuilder();

    result.append(getMethod().name);
    result.append(" https://");
    result.append(host);

    for (String s : getPath()) {
      result.append("/");
      result.append(s);
    }

    if (!getParameters().entrySet().isEmpty()) {
      result.append("?");
    }

    for (Map.Entry<String, String> e : getParameters().entrySet()) {
      result.append(e.getKey()).append("=").append(e.getValue()).append("&");
    }

    return result.toString();
  }
}
