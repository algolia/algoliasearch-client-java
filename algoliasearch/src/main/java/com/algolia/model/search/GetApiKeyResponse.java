// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.search;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** GetApiKeyResponse */
public class GetApiKeyResponse {

  @JsonProperty("value")
  private String value;

  @JsonProperty("createdAt")
  private Long createdAt;

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

  public GetApiKeyResponse setValue(String value) {
    this.value = value;
    return this;
  }

  /** API key. */
  @javax.annotation.Nonnull
  public String getValue() {
    return value;
  }

  public GetApiKeyResponse setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /** Timestamp when the object was created, in milliseconds since the Unix epoch. */
  @javax.annotation.Nonnull
  public Long getCreatedAt() {
    return createdAt;
  }

  public GetApiKeyResponse setAcl(List<Acl> acl) {
    this.acl = acl;
    return this;
  }

  public GetApiKeyResponse addAcl(Acl aclItem) {
    this.acl.add(aclItem);
    return this;
  }

  /**
   * Permissions that determine the type of API requests this key can make. The required ACL is
   * listed in each endpoint's reference. For more information, see [access control
   * list](https://www.algolia.com/doc/guides/security/api-keys/#access-control-list-acl).
   */
  @javax.annotation.Nonnull
  public List<Acl> getAcl() {
    return acl;
  }

  public GetApiKeyResponse setDescription(String description) {
    this.description = description;
    return this;
  }

  /** Description of an API key to help you identify this API key. */
  @javax.annotation.Nullable
  public String getDescription() {
    return description;
  }

  public GetApiKeyResponse setIndexes(List<String> indexes) {
    this.indexes = indexes;
    return this;
  }

  public GetApiKeyResponse addIndexes(String indexesItem) {
    if (this.indexes == null) {
      this.indexes = new ArrayList<>();
    }
    this.indexes.add(indexesItem);
    return this;
  }

  /**
   * Index names or patterns that this API key can access. By default, an API key can access all
   * indices in the same application. You can use leading and trailing wildcard characters (`*`): -
   * `dev_*` matches all indices starting with \"dev_\". - `*_dev` matches all indices ending with
   * \"_dev\". - `*_products_*` matches all indices containing \"_products_\".
   */
  @javax.annotation.Nullable
  public List<String> getIndexes() {
    return indexes;
  }

  public GetApiKeyResponse setMaxHitsPerQuery(Integer maxHitsPerQuery) {
    this.maxHitsPerQuery = maxHitsPerQuery;
    return this;
  }

  /**
   * Maximum number of results this API key can retrieve in one query. By default, there's no limit.
   */
  @javax.annotation.Nullable
  public Integer getMaxHitsPerQuery() {
    return maxHitsPerQuery;
  }

  public GetApiKeyResponse setMaxQueriesPerIPPerHour(Integer maxQueriesPerIPPerHour) {
    this.maxQueriesPerIPPerHour = maxQueriesPerIPPerHour;
    return this;
  }

  /**
   * Maximum number of API requests allowed per IP address or [user
   * token](https://www.algolia.com/doc/guides/sending-events/concepts/usertoken/) per hour. If this
   * limit is reached, the API returns an error with status code `429`. By default, there's no
   * limit.
   */
  @javax.annotation.Nullable
  public Integer getMaxQueriesPerIPPerHour() {
    return maxQueriesPerIPPerHour;
  }

  public GetApiKeyResponse setQueryParameters(String queryParameters) {
    this.queryParameters = queryParameters;
    return this;
  }

  /**
   * Query parameters to add when making API requests with this API key. To restrict this API key to
   * specific IP addresses, add the `restrictSources` parameter. You can only add a single source,
   * but you can provide a range of IP addresses. Creating an API key fails if the request is made
   * from an IP address outside the restricted range.
   */
  @javax.annotation.Nullable
  public String getQueryParameters() {
    return queryParameters;
  }

  public GetApiKeyResponse setReferers(List<String> referers) {
    this.referers = referers;
    return this;
  }

  public GetApiKeyResponse addReferers(String referersItem) {
    if (this.referers == null) {
      this.referers = new ArrayList<>();
    }
    this.referers.add(referersItem);
    return this;
  }

  /**
   * Allowed HTTP referrers for this API key. By default, all referrers are allowed. You can use
   * leading and trailing wildcard characters (`*`): - `https://algolia.com/_*` allows all referrers
   * starting with \"https://algolia.com/\" - `*.algolia.com` allows all referrers ending with
   * \".algolia.com\" - `*algolia.com*` allows all referrers in the domain \"algolia.com\". Like all
   * HTTP headers, referrers can be spoofed. Don't rely on them to secure your data. For more
   * information, see [HTTP referrer
   * restrictions](https://www.algolia.com/doc/guides/security/security-best-practices/#http-referrers-restrictions).
   */
  @javax.annotation.Nullable
  public List<String> getReferers() {
    return referers;
  }

  public GetApiKeyResponse setValidity(Integer validity) {
    this.validity = validity;
    return this;
  }

  /** Duration (in seconds) after which the API key expires. By default, API keys don't expire. */
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
    GetApiKeyResponse getApiKeyResponse = (GetApiKeyResponse) o;
    return (
      Objects.equals(this.value, getApiKeyResponse.value) &&
      Objects.equals(this.createdAt, getApiKeyResponse.createdAt) &&
      Objects.equals(this.acl, getApiKeyResponse.acl) &&
      Objects.equals(this.description, getApiKeyResponse.description) &&
      Objects.equals(this.indexes, getApiKeyResponse.indexes) &&
      Objects.equals(this.maxHitsPerQuery, getApiKeyResponse.maxHitsPerQuery) &&
      Objects.equals(this.maxQueriesPerIPPerHour, getApiKeyResponse.maxQueriesPerIPPerHour) &&
      Objects.equals(this.queryParameters, getApiKeyResponse.queryParameters) &&
      Objects.equals(this.referers, getApiKeyResponse.referers) &&
      Objects.equals(this.validity, getApiKeyResponse.validity)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      value,
      createdAt,
      acl,
      description,
      indexes,
      maxHitsPerQuery,
      maxQueriesPerIPPerHour,
      queryParameters,
      referers,
      validity
    );
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetApiKeyResponse {\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
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
