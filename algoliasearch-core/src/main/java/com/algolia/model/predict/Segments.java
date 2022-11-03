// This file is generated, manual changes will be lost - read more on
// https://github.com/algolia/api-clients-automation.

package com.algolia.model.predict;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Segments that the user belongs to. */
public class Segments {

  @JsonProperty("computed")
  private List<String> computed = new ArrayList<>();

  @JsonProperty("custom")
  private List<String> custom = new ArrayList<>();

  public Segments setComputed(List<String> computed) {
    this.computed = computed;
    return this;
  }

  public Segments addComputed(String computedItem) {
    this.computed.add(computedItem);
    return this;
  }

  /**
   * List of computed segments IDs.
   *
   * @return computed
   */
  @javax.annotation.Nonnull
  public List<String> getComputed() {
    return computed;
  }

  public Segments setCustom(List<String> custom) {
    this.custom = custom;
    return this;
  }

  public Segments addCustom(String customItem) {
    this.custom.add(customItem);
    return this;
  }

  /**
   * List of custom segments IDs.
   *
   * @return custom
   */
  @javax.annotation.Nonnull
  public List<String> getCustom() {
    return custom;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Segments segments = (Segments) o;
    return Objects.equals(this.computed, segments.computed) && Objects.equals(this.custom, segments.custom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(computed, custom);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Segments {\n");
    sb.append("    computed: ").append(toIndentedString(computed)).append("\n");
    sb.append("    custom: ").append(toIndentedString(custom)).append("\n");
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