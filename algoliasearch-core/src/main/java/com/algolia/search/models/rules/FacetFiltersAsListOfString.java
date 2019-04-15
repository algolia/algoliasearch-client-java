package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(as = FacetFiltersAsListOfString.class)
public class FacetFiltersAsListOfString extends FacetFilters {

  private final List<String> filters;

  FacetFiltersAsListOfString() {
    this.filters = new ArrayList<>();
  }

  FacetFiltersAsListOfString(List<String> filters) {
    this.filters = filters;
  }

  @Override
  @JsonIgnore
  public Object getInsideValue() {
    return this.filters;
  }

  List<String> getFilters() {
    return this.filters;
  }
}
