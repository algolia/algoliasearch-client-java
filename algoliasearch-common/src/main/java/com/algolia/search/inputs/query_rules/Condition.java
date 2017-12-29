package com.algolia.search.inputs.query_rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Condition implements Serializable {
  private String pattern;
  private String anchoring;
  private String context;

  public Condition() {}

  public String getPattern() {
    return pattern;
  }

  public Condition setPattern(String pattern) {
    this.pattern = pattern;
    return this;
  }

  public String getAnchoring() {
    return anchoring;
  }

  public Condition setAnchoring(String anchoring) {
    this.anchoring = anchoring;
    return this;
  }

  public String getContext() {
    return context;
  }

  public Condition setContext(String context) {
    this.context = context;
    return this;
  }
}
