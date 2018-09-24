package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

@JsonDeserialize(as = FacetFiltersAsListOfList.class)
public class FacetFiltersAsListOfList extends FacetFilters {

  private List<List<String>> filters;

  FacetFiltersAsListOfList(List<List<String>> filters) {
    this.filters = filters;
  }

  @Override
  @JsonIgnore
  public Object getInsideValue() {
    return this.filters;
  }

  List<List<String>> getFilters() {
    return this.filters;
  }
}
