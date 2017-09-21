package com.algolia.search.inputs.query_rules;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rule {

  private String objectID;
  private Condition condition;
  private Consequence consequence;
  private String description;

  // For serialization
  public Rule() {}

  public String getObjectID() {
    return objectID;
  }

  public Rule setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  public Condition getCondition() {
    return condition;
  }

  public Rule setCondition(Condition condition) {
    this.condition = condition;
    return this;
  }

  public Consequence getConsequence() {
    return consequence;
  }

  public Rule setConsequence(Consequence consequence) {
    this.consequence = consequence;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Rule setDescription(String description) {
    this.description = description;
    return this;
  }
}
