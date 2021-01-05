package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacetMerchandising implements Serializable {
  private List<String> order;

  public FacetMerchandising() {}

  public FacetMerchandising(List<String> order) {
    this.order = order;
  }

  public List<String> getOrder() {
    return order;
  }

  public FacetMerchandising setOrder(List<String> order) {
    this.order = order;
    return this;
  }
}
