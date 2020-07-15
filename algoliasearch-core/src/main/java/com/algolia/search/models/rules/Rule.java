package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

/**
 * Query Rules allows performing pre- and post-processing on queries matching specific patterns.
 *
 * @see <a href="https://www.algolia.com/doc/api-client/methods/query-rules">Algolia.com</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rule implements Serializable {

  private String objectID;
  private Condition condition;
  private List<Condition> conditions;
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

  @Deprecated
  public Condition getCondition() {
    return condition;
  }

  @Deprecated
  public Rule setCondition(Condition condition) {
    this.condition = condition;
    return this;
  }

  public List<Condition> getConditions() {
    return conditions;
  }

  public Rule setConditions(List<Condition> conditions) {
    this.conditions = conditions;
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
