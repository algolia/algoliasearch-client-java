package com.algolia.search.models.apikeys;

import com.algolia.search.models.indexing.FlatListSerializer;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.QuerySerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SecuredApiKeyRestriction {

  public Query getQuery() {
    return query;
  }

  public SecuredApiKeyRestriction setQuery(Query query) {
    this.query = query;
    return this;
  }

  public Long getValidUntil() {
    return validUntil;
  }

  public SecuredApiKeyRestriction setValidUntil(Long validUntil) {
    this.validUntil = validUntil;
    return this;
  }

  public List<String> getRestrictIndices() {
    return restrictIndices;
  }

  public SecuredApiKeyRestriction setRestrictIndices(List<String> restrictIndices) {
    this.restrictIndices = restrictIndices;
    return this;
  }

  public List<String> getRestrictSources() {
    return restrictSources;
  }

  public SecuredApiKeyRestriction setRestrictSources(List<String> restrictSources) {
    this.restrictSources = restrictSources;
    return this;
  }

  public String getUserToken() {
    return userToken;
  }

  public SecuredApiKeyRestriction setUserToken(String userToken) {
    this.userToken = userToken;
    return this;
  }

  @JsonSerialize(using = QuerySerializer.class)
  private Query query;

  private Long validUntil;

  @JsonSerialize(using = FlatListSerializer.class)
  private List<String> restrictIndices;

  @JsonSerialize(using = FlatListSerializer.class)
  private List<String> restrictSources;

  private String userToken;
}
