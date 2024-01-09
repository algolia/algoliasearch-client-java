// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.search;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Rule object. */
public class Rule {

  @JsonProperty("objectID")
  private String objectID;

  @JsonProperty("conditions")
  private List<Condition> conditions;

  @JsonProperty("consequence")
  private Consequence consequence;

  @JsonProperty("description")
  private String description;

  @JsonProperty("enabled")
  private Boolean enabled;

  @JsonProperty("validity")
  private List<TimeRange> validity;

  public Rule setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  /** Unique identifier for a rule object. */
  @javax.annotation.Nonnull
  public String getObjectID() {
    return objectID;
  }

  public Rule setConditions(List<Condition> conditions) {
    this.conditions = conditions;
    return this;
  }

  public Rule addConditions(Condition conditionsItem) {
    if (this.conditions == null) {
      this.conditions = new ArrayList<>();
    }
    this.conditions.add(conditionsItem);
    return this;
  }

  /**
   * [Conditions](https://www.algolia.com/doc/guides/managing-results/rules/rules-overview/#conditions)
   * required to activate a rule. You can use up to 25 conditions per rule.
   */
  @javax.annotation.Nullable
  public List<Condition> getConditions() {
    return conditions;
  }

  public Rule setConsequence(Consequence consequence) {
    this.consequence = consequence;
    return this;
  }

  /** Get consequence */
  @javax.annotation.Nullable
  public Consequence getConsequence() {
    return consequence;
  }

  public Rule setDescription(String description) {
    this.description = description;
    return this;
  }

  /**
   * Description of the rule's purpose. This can be helpful for display in the Algolia dashboard.
   */
  @javax.annotation.Nullable
  public String getDescription() {
    return description;
  }

  public Rule setEnabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /** Indicates whether to enable the rule. If it isn't enabled, it isn't applied at query time. */
  @javax.annotation.Nullable
  public Boolean getEnabled() {
    return enabled;
  }

  public Rule setValidity(List<TimeRange> validity) {
    this.validity = validity;
    return this;
  }

  public Rule addValidity(TimeRange validityItem) {
    if (this.validity == null) {
      this.validity = new ArrayList<>();
    }
    this.validity.add(validityItem);
    return this;
  }

  /**
   * If you specify a validity period, the rule _only_ applies only during that period. If
   * specified, the array must not be empty.
   */
  @javax.annotation.Nullable
  public List<TimeRange> getValidity() {
    return validity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rule rule = (Rule) o;
    return (
      Objects.equals(this.objectID, rule.objectID) &&
      Objects.equals(this.conditions, rule.conditions) &&
      Objects.equals(this.consequence, rule.consequence) &&
      Objects.equals(this.description, rule.description) &&
      Objects.equals(this.enabled, rule.enabled) &&
      Objects.equals(this.validity, rule.validity)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectID, conditions, consequence, description, enabled, validity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Rule {\n");
    sb.append("    objectID: ").append(toIndentedString(objectID)).append("\n");
    sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
    sb.append("    consequence: ").append(toIndentedString(consequence)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
    sb.append("    validity: ").append(toIndentedString(validity)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}