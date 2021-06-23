package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/** Facets and facets values ordering rules container. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacetOrdering implements Serializable {
  private FacetsOrder facets;
  private Map<String, FacetValuesOrder> values;

  public FacetOrdering() {}

  public FacetOrdering(FacetsOrder facets, Map<String, FacetValuesOrder> values) {
    this.facets = facets;
    this.values = values;
  }

  public FacetsOrder getFacets() {
    return facets;
  }

  public FacetOrdering setFacets(FacetsOrder facets) {
    this.facets = facets;
    return this;
  }

  public Map<String, FacetValuesOrder> getValues() {
    return values;
  }

  public FacetOrdering setValues(Map<String, FacetValuesOrder> values) {
    this.values = values;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FacetOrdering)) return false;
    FacetOrdering that = (FacetOrdering) o;
    return Objects.equals(facets, that.facets) && Objects.equals(values, that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(facets, values);
  }

  @Override
  public String toString() {
    return "FacetOrdering{" + "facets=" + facets + ", values=" + values + '}';
  }
}
