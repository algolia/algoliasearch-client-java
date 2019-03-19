package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiKey implements Serializable {

  private Long validity;

  private Integer maxQueriesPerIPPerHour;

  private Long maxHitsPerQuery;

  private String queryParameters;

  private String description;

  private String restrictSources;

  private List<String> acl;

  private List<String> indexes;

  private List<String> referers;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Long getValidity() {
    return validity;
  }

  public ApiKey setValidity(Long validity) {
    this.validity = validity;
    return this;
  }

  public Integer getMaxQueriesPerIPPerHour() {
    return maxQueriesPerIPPerHour;
  }

  public ApiKey setMaxQueriesPerIPPerHour(Integer maxQueriesPerIPPerHour) {
    this.maxQueriesPerIPPerHour = maxQueriesPerIPPerHour;
    return this;
  }

  public Long getMaxHitsPerQuery() {
    return maxHitsPerQuery;
  }

  public ApiKey setMaxHitsPerQuery(Integer maxHitsPerQuery) {
    return this.setMaxHitsPerQuery(maxHitsPerQuery.longValue());
  }

  @JsonSetter
  public ApiKey setMaxHitsPerQuery(Long maxHitsPerQuery) {
    this.maxHitsPerQuery = maxHitsPerQuery;
    return this;
  }

  public List<String> getAcl() {
    return acl;
  }

  public ApiKey setAcl(List<String> acl) {
    this.acl = acl;
    return this;
  }

  public List<String> getIndexes() {
    return indexes;
  }

  public ApiKey setIndexes(List<String> indexes) {
    this.indexes = indexes;
    return this;
  }

  public List<String> getReferers() {
    return referers;
  }

  public ApiKey setReferers(List<String> referers) {
    this.referers = referers;
    return this;
  }

  public String getQueryParameters() {
    return queryParameters;
  }

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

  public String getRestrictSources() {
    return restrictSources;
  }

  public ApiKey setRestrictSources(String restrictSources) {
    this.restrictSources = restrictSources;
    return this;
  }

  @Override
  public String toString() {
    return "ApiKey{"
        + "value="
        + value
        + ", maxQueriesPerIPPerHour="
        + maxQueriesPerIPPerHour
        + ", maxHitsPerQuery="
        + maxHitsPerQuery
        + ", queryParameters='"
        + queryParameters
        + '\''
        + ", description='"
        + description
        + '\''
        + ", restrictSources='"
        + restrictSources
        + '\''
        + ", acl="
        + String.join(",", acl)
        + ", indexes="
        + String.join(",", indexes)
        + ", referers="
        + String.join(",", referers)
        + '}';
  }
}
