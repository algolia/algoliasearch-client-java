package com.algolia.search.inputs.query_rules;

import com.algolia.search.objects.Edit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsequenceQueryObject extends ConsequenceQuery {

  private List<String> remove;
  private List<Edit> edits;

  public ConsequenceQueryObject() {}

  @Deprecated
  public List<String> getRemove() {
    return remove;
  }

  /** Old features use edits instead */
  @Deprecated
  public ConsequenceQueryObject setRemove(List<String> remove) {
    this.remove = remove;
    return this;
  }

  public List<Edit> getEdits() {
    return edits;
  }

  public ConsequenceQueryObject setEdits(List<Edit> edits) {
    this.edits = edits;
    return this;
  }
}
