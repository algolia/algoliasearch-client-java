package com.algolia.search.models.indexing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MultipleGetObjectsResponse<T> implements Serializable {

  public List<T> getResults() {
    return results;
  }

  public MultipleGetObjectsResponse<T> setResults(List<T> results) {
    this.results = results;
    return this;
  }

  private List<T> results;
}
