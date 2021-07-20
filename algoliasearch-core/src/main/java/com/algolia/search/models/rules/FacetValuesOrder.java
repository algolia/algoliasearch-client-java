package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/** Facet values ordering rule container. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacetValuesOrder implements Serializable {
  private List<String> order;
  private String sortRemainingBy;

  public FacetValuesOrder() {}

  public FacetValuesOrder(List<String> order, String sortRemainingBy) {
    this.order = order;
    this.sortRemainingBy = sortRemainingBy;
  }

  public List<String> getOrder() {
    return order;
  }

  public FacetValuesOrder setOrder(List<String> order) {
    this.order = order;
    return this;
  }

  public String getSortRemainingBy() {
    return sortRemainingBy;
  }

  public FacetValuesOrder setSortRemainingBy(String sortRemainingBy) {
    this.sortRemainingBy = sortRemainingBy;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FacetValuesOrder)) return false;
    FacetValuesOrder that = (FacetValuesOrder) o;
    return Objects.equals(order, that.order)
        && Objects.equals(sortRemainingBy, that.sortRemainingBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(order, sortRemainingBy);
  }

  @Override
  public String toString() {
    return "FacetValuesOrder{"
        + "order="
        + order
        + ", sortRemainingBy='"
        + sortRemainingBy
        + '\''
        + '}';
  }
}
