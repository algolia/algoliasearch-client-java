package com.algolia.search.inputs.query_rules;

import com.algolia.search.objects.TimeRange;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rule implements Serializable {

  private String objectID;
  private Condition condition;
  private Consequence consequence;
  private String description;
  private Boolean enabled;
  private List<TimeRange> validity;

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

  public Boolean getEnabled() {
    return enabled;
  }

  public Rule setEnabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  public List<TimeRange> getValidity() {
    return validity;
  }

  public Rule setValidity(List<TimeRange> validity) {
    this.validity = validity;
    return this;
  }
}
