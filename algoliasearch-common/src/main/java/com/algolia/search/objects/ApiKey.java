package com.algolia.search.objects;

import java.util.List;

public class ApiKey {

  private Integer validity;

  private Integer maxQueriesPerIPPerHour;

  private Integer maxHitsPerQuery;

  private List<String> acl;

  private List<String> indexes;

  private List<String> referers;

  private String queryParameters;

  private String description;

  @SuppressWarnings("unused")
  public Integer getValidity() {
    return validity;
  }

  @SuppressWarnings("unused")
  public ApiKey setValidity(Integer validity) {
    this.validity = validity;
    return this;
  }

  @SuppressWarnings("unused")
  public Integer getMaxQueriesPerIPPerHour() {
    return maxQueriesPerIPPerHour;
  }

  @SuppressWarnings("unused")
  public ApiKey setMaxQueriesPerIPPerHour(Integer maxQueriesPerIPPerHour) {
    this.maxQueriesPerIPPerHour = maxQueriesPerIPPerHour;
    return this;
  }

  @SuppressWarnings("unused")
  public Integer getMaxHitsPerQuery() {
    return maxHitsPerQuery;
  }

  @SuppressWarnings("unused")
  public ApiKey setMaxHitsPerQuery(Integer maxHitsPerQuery) {
    this.maxHitsPerQuery = maxHitsPerQuery;
    return this;
  }

  @SuppressWarnings("unused")
  public List<String> getAcl() {
    return acl;
  }

  @SuppressWarnings("unused")
  public ApiKey setAcl(List<String> acl) {
    this.acl = acl;
    return this;
  }

  @SuppressWarnings("unused")
  public List<String> getIndexes() {
    return indexes;
  }

  public ApiKey setIndexes(List<String> indexes) {
    this.indexes = indexes;
    return this;
  }

  @SuppressWarnings("unused")
  public List<String> getReferers() {
    return referers;
  }

  @SuppressWarnings("unused")
  public ApiKey setReferers(List<String> referers) {
    this.referers = referers;
    return this;
  }

  @SuppressWarnings("unused")
  public String getQueryParameters() {
    return queryParameters;
  }

  @SuppressWarnings("unused")
  public ApiKey setQueryParameters(String queryParameters) {
    this.queryParameters = queryParameters;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public ApiKey setDescription(String description) {
    this.description = description;
    return this;
  }
}
