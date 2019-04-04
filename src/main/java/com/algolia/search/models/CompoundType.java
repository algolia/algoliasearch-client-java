package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface CompoundType {

  @JsonIgnore
  Object getInsideValue();
}
