package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/** Define or override the way facet attributes are displayed. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacetsOrder implements Serializable {
  private List<String> order;

  public FacetsOrder() {}

  public FacetsOrder(List<String> order) {
    this.order = order;
  }

  public List<String> getOrder() {
    return order;
  }

  public FacetsOrder setOrder(List<String> order) {
    this.order = order;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FacetsOrder)) return false;
    FacetsOrder that = (FacetsOrder) o;
    return Objects.equals(order, that.order);
  }

  @Override
  public int hashCode() {
    return Objects.hash(order);
  }

  @Override
  public String toString() {
    return "FacetsOrder{" + "order=" + order + '}';
  }
}
