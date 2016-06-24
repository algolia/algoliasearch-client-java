package com.algolia.search.http;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class AlgoliaRequest<T> {

  private final HttpMethod method;
  private final boolean isSearch;
  private final List<String> path;

  private Map<String, String> parameters;
  private Object data;
  private TypeToken<T> resultTypeToken;
  private Class<T> resultClass;

  public AlgoliaRequest(HttpMethod method, boolean isSearch, List<String> path, Class<T> resultClass) {
    this.method = method;
    this.isSearch = isSearch;
    this.path = path;
    this.resultClass = resultClass;
  }

  public AlgoliaRequest(HttpMethod method, boolean isSearch, List<String> path, TypeToken<T> resultTypeToken) {
    this.method = method;
    this.isSearch = isSearch;
    this.path = path;
    this.resultTypeToken = resultTypeToken;
  }

  public Type getResultType() {
    return resultTypeToken == null ? resultClass : resultTypeToken.getType();
  }


  public HttpMethod getMethod() {
    return method;
  }

  public boolean isSearch() {
    return isSearch;
  }

  public List<String> getPath() {
    return path;
  }

  public boolean hasData() {
    return data != null;
  }

  public Object getData() {
    return data;
  }

  public AlgoliaRequest<T> setData(Object data) {
    this.data = data;
    return this;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  public AlgoliaRequest<T> setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
    return this;
  }
}
