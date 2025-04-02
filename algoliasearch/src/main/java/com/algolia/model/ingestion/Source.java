// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.ingestion;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.Objects;

/** Source */
public class Source {

  @JsonProperty("sourceID")
  private String sourceID;

  @JsonProperty("type")
  private SourceType type;

  @JsonProperty("name")
  private String name;

  @JsonProperty("owner")
  private String owner;

  @JsonProperty("input")
  private SourceInput input;

  @JsonProperty("authenticationID")
  private String authenticationID;

  @JsonProperty("createdAt")
  private String createdAt;

  @JsonProperty("updatedAt")
  private String updatedAt;

  public Source setSourceID(String sourceID) {
    this.sourceID = sourceID;
    return this;
  }

  /** Universally uniqud identifier (UUID) of a source. */
  @javax.annotation.Nonnull
  public String getSourceID() {
    return sourceID;
  }

  public Source setType(SourceType type) {
    this.type = type;
    return this;
  }

  /** Get type */
  @javax.annotation.Nonnull
  public SourceType getType() {
    return type;
  }

  public Source setName(String name) {
    this.name = name;
    return this;
  }

  /** Get name */
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }

  public Source setOwner(String owner) {
    this.owner = owner;
    return this;
  }

  /** Owner of the resource. */
  @javax.annotation.Nullable
  public String getOwner() {
    return owner;
  }

  public Source setInput(SourceInput input) {
    this.input = input;
    return this;
  }

  /** Get input */
  @javax.annotation.Nullable
  public SourceInput getInput() {
    return input;
  }

  public Source setAuthenticationID(String authenticationID) {
    this.authenticationID = authenticationID;
    return this;
  }

  /** Universally unique identifier (UUID) of an authentication resource. */
  @javax.annotation.Nullable
  public String getAuthenticationID() {
    return authenticationID;
  }

  public Source setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /** Date of creation in RFC 3339 format. */
  @javax.annotation.Nonnull
  public String getCreatedAt() {
    return createdAt;
  }

  public Source setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /** Date of last update in RFC 3339 format. */
  @javax.annotation.Nonnull
  public String getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Source source = (Source) o;
    return (
      Objects.equals(this.sourceID, source.sourceID) &&
      Objects.equals(this.type, source.type) &&
      Objects.equals(this.name, source.name) &&
      Objects.equals(this.owner, source.owner) &&
      Objects.equals(this.input, source.input) &&
      Objects.equals(this.authenticationID, source.authenticationID) &&
      Objects.equals(this.createdAt, source.createdAt) &&
      Objects.equals(this.updatedAt, source.updatedAt)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceID, type, name, owner, input, authenticationID, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Source {\n");
    sb.append("    sourceID: ").append(toIndentedString(sourceID)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    input: ").append(toIndentedString(input)).append("\n");
    sb.append("    authenticationID: ").append(toIndentedString(authenticationID)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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
