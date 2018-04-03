package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface CompoundType {

  @JsonIgnore
  Object getInsideValue();
}
