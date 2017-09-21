package com.algolia.search.inputs.query_rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsequenceQueryObject extends ConsequenceQuery {

  private List<String> remove;

  public ConsequenceQueryObject() {}

  public List<String> getRemove() {
    return remove;
  }

  public ConsequenceQueryObject setRemove(List<String> remove) {
    this.remove = remove;
    return this;
  }
}
