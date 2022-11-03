// This file is generated, manual changes will be lost - read more on
// https://github.com/algolia/api-clients-automation.

package com.algolia.model.search;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Api Key object. */
public class ApiKey {

  @JsonProperty("acl")
  private List<Acl> acl = new ArrayList<>();

  @JsonProperty("description")
  private String description;

  @JsonProperty("indexes")
  private List<String> indexes;

  @JsonProperty("maxHitsPerQuery")
  private Integer maxHitsPerQuery;

  @JsonProperty("maxQueriesPerIPPerHour")
  private Integer maxQueriesPerIPPerHour;

  @JsonProperty("queryParameters")
  private String queryParameters;

  @JsonProperty("referers")
  private List<String> referers;

  @JsonProperty("validity")
  private Integer validity;

  public ApiKey setAcl(List<Acl> acl) {
    this.acl = acl;
    return this;
  }

  public ApiKey addAcl(Acl aclItem) {
    this.acl.add(aclItem);
    return this;
  }

  /**
   * Set of permissions associated with the key.
   *
   * @return acl
   */
  @javax.annotation.Nonnull
  public List<Acl> getAcl() {
    return acl;
  }

  public ApiKey setDescription(String description) {
    this.description = description;
    return this;
  }

  /**
   * A comment used to identify a key more easily in the dashboard. It is not interpreted by the
   * API.
   *
   * @return description
   */
  @javax.annotation.Nullable
  public String getDescription() {
    return description;
  }

  public ApiKey setIndexes(List<String> indexes) {
    this.indexes = indexes;
    return this;
  }

  public ApiKey addIndexes(String indexesItem) {
    if (this.indexes == null) {
      this.indexes = new ArrayList<>();
    }
    this.indexes.add(indexesItem);
    return this;
  }

  /**
   * Restrict this new API key to a list of indices or index patterns. If the list is empty, all
   * indices are allowed.
   *
   * @return indexes
   */
  @javax.annotation.Nullable
  public List<String> getIndexes() {
    return indexes;
  }

  public ApiKey setMaxHitsPerQuery(Integer maxHitsPerQuery) {
    this.maxHitsPerQuery = maxHitsPerQuery;
    return this;
  }

  /**
   * Maximum number of hits this API key can retrieve in one query. If zero, no limit is enforced.
   *
   * @return maxHitsPerQuery
   */
  @javax.annotation.Nullable
  public Integer getMaxHitsPerQuery() {
    return maxHitsPerQuery;
  }

  public ApiKey setMaxQueriesPerIPPerHour(Integer maxQueriesPerIPPerHour) {
    this.maxQueriesPerIPPerHour = maxQueriesPerIPPerHour;
    return this;
  }

  /**
   * Maximum number of API calls per hour allowed from a given IP address or a user token.
   *
   * @return maxQueriesPerIPPerHour
   */
  @javax.annotation.Nullable
  public Integer getMaxQueriesPerIPPerHour() {
    return maxQueriesPerIPPerHour;
  }

  public ApiKey setQueryParameters(String queryParameters) {
    this.queryParameters = queryParameters;
    return this;
  }

  /**
   * URL-encoded query string. Force some query parameters to be applied for each query made with
   * this API key.
   *
   * @return queryParameters
   */
  @javax.annotation.Nullable
  public String getQueryParameters() {
    return queryParameters;
  }

  public ApiKey setReferers(List<String> referers) {
    this.referers = referers;
    return this;
  }

  public ApiKey addReferers(String referersItem) {
    if (this.referers == null) {
      this.referers = new ArrayList<>();
    }
    this.referers.add(referersItem);
    return this;
  }

  /**
   * Restrict this new API key to specific referers. If empty or blank, defaults to all referers.
   *
   * @return referers
   */
  @javax.annotation.Nullable
  public List<String> getReferers() {
    return referers;
  }

  public ApiKey setValidity(Integer validity) {
    this.validity = validity;
    return this;
  }

  /**
   * Validity limit for this key in seconds. The key will automatically be removed after this period
   * of time.
   *
   * @return validity
   */
  @javax.annotation.Nullable
  public Integer getValidity() {
    return validity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiKey apiKey = (ApiKey) o;
    return (
      Objects.equals(this.acl, apiKey.acl) &&
      Objects.equals(this.description, apiKey.description) &&
      Objects.equals(this.indexes, apiKey.indexes) &&
      Objects.equals(this.maxHitsPerQuery, apiKey.maxHitsPerQuery) &&
      Objects.equals(this.maxQueriesPerIPPerHour, apiKey.maxQueriesPerIPPerHour) &&
      Objects.equals(this.queryParameters, apiKey.queryParameters) &&
      Objects.equals(this.referers, apiKey.referers) &&
      Objects.equals(this.validity, apiKey.validity)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(acl, description, indexes, maxHitsPerQuery, maxQueriesPerIPPerHour, queryParameters, referers, validity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiKey {\n");
    sb.append("    acl: ").append(toIndentedString(acl)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    indexes: ").append(toIndentedString(indexes)).append("\n");
    sb.append("    maxHitsPerQuery: ").append(toIndentedString(maxHitsPerQuery)).append("\n");
    sb.append("    maxQueriesPerIPPerHour: ").append(toIndentedString(maxQueriesPerIPPerHour)).append("\n");
    sb.append("    queryParameters: ").append(toIndentedString(queryParameters)).append("\n");
    sb.append("    referers: ").append(toIndentedString(referers)).append("\n");
    sb.append("    validity: ").append(toIndentedString(validity)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}