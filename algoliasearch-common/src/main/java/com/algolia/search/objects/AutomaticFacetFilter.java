package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutomaticFacetFilter {

  private String facet;
  private Boolean disjunctive;
  private Integer score;

  public AutomaticFacetFilter() {}

  public AutomaticFacetFilter(String facet, Boolean disjunctive, Integer score) {
    this.facet = facet;
    this.disjunctive = disjunctive;
    this.score = score;
  }

  public AutomaticFacetFilter(String facet) {
    this.facet = facet;
  }

  public AutomaticFacetFilter(String facet, Boolean disjunctive) {
    this.facet = facet;
    this.disjunctive = disjunctive;
  }

  public String getFacet() {
    return facet;
  }

  public AutomaticFacetFilter setFacet(String facet) {
    this.facet = facet;
    return this;
  }

  public Boolean getDisjunctive() {
    return disjunctive;
  }

  public AutomaticFacetFilter setDisjunctive(Boolean disjunctive) {
    this.disjunctive = disjunctive;
    return this;
  }

  public Integer getScore() {
    return score;
  }

  public AutomaticFacetFilter setScore(Integer score) {
    this.score = score;
    return this;
  }
}
