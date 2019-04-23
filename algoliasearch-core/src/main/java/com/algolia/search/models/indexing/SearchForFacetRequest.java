package com.algolia.search.models.indexing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchForFacetRequest implements Serializable {

  public String getFacetName() {
    return facetName;
  }

  public SearchForFacetRequest setFacetName(String facetName) {
    this.facetName = facetName;
    return this;
  }

  public String getFacetQuery() {
    return facetQuery;
  }

  public SearchForFacetRequest setFacetQuery(String facetQuery) {
    this.facetQuery = facetQuery;
    return this;
  }

  public Query getSearchParameters() {
    return searchParameters;
  }

  public SearchForFacetRequest setSearchParameters(Query searchParameters) {
    this.searchParameters = searchParameters;
    return this;
  }

  @Override
  public String toString() {
    return "SearchForFacetRequest{"
        + "facetName='"
        + facetName
        + '\''
        + ", facetQuery='"
        + facetQuery
        + '\''
        + ", searchParameters="
        + searchParameters
        + '}';
  }

  @JsonIgnore private String facetName;
  private String facetQuery;

  @JsonProperty("params")
  @JsonSerialize(using = QuerySerializer.class)
  private Query searchParameters;
}
