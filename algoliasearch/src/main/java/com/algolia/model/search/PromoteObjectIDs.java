// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.search;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Records to promote. */
@JsonDeserialize(as = PromoteObjectIDs.class)
public class PromoteObjectIDs implements Promote {

  @JsonProperty("objectIDs")
  private List<String> objectIDs = new ArrayList<>();

  @JsonProperty("position")
  private Integer position;

  public PromoteObjectIDs setObjectIDs(List<String> objectIDs) {
    this.objectIDs = objectIDs;
    return this;
  }

  public PromoteObjectIDs addObjectIDs(String objectIDsItem) {
    this.objectIDs.add(objectIDsItem);
    return this;
  }

  /** Unique identifiers of the records to promote. */
  @javax.annotation.Nonnull
  public List<String> getObjectIDs() {
    return objectIDs;
  }

  public PromoteObjectIDs setPosition(Integer position) {
    this.position = position;
    return this;
  }

  /**
   * The position to promote the records to. If you pass objectIDs, the records are placed at this
   * position as a group. For example, if you pronmote four objectIDs to position 0, the records
   * take the first four positions.
   */
  @javax.annotation.Nonnull
  public Integer getPosition() {
    return position;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PromoteObjectIDs promoteObjectIDs = (PromoteObjectIDs) o;
    return Objects.equals(this.objectIDs, promoteObjectIDs.objectIDs) && Objects.equals(this.position, promoteObjectIDs.position);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectIDs, position);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PromoteObjectIDs {\n");
    sb.append("    objectIDs: ").append(toIndentedString(objectIDs)).append("\n");
    sb.append("    position: ").append(toIndentedString(position)).append("\n");
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
