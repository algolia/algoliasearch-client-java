// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.ingestion;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.Objects;

/** Payload to partially update an Authentication. */
public class AuthenticationUpdate {

  @JsonProperty("type")
  private AuthenticationType type;

  @JsonProperty("name")
  private String name;

  @JsonProperty("platform")
  private Platform platform;

  @JsonProperty("input")
  private AuthInputPartial input;

  public AuthenticationUpdate setType(AuthenticationType type) {
    this.type = type;
    return this;
  }

  /** Get type */
  @javax.annotation.Nullable
  public AuthenticationType getType() {
    return type;
  }

  public AuthenticationUpdate setName(String name) {
    this.name = name;
    return this;
  }

  /** An human readable name describing the object. */
  @javax.annotation.Nullable
  public String getName() {
    return name;
  }

  public AuthenticationUpdate setPlatform(Platform platform) {
    this.platform = platform;
    return this;
  }

  /** Get platform */
  @javax.annotation.Nullable
  public Platform getPlatform() {
    return platform;
  }

  public AuthenticationUpdate setInput(AuthInputPartial input) {
    this.input = input;
    return this;
  }

  /** Get input */
  @javax.annotation.Nullable
  public AuthInputPartial getInput() {
    return input;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthenticationUpdate authenticationUpdate = (AuthenticationUpdate) o;
    return (
      Objects.equals(this.type, authenticationUpdate.type) &&
      Objects.equals(this.name, authenticationUpdate.name) &&
      Objects.equals(this.platform, authenticationUpdate.platform) &&
      Objects.equals(this.input, authenticationUpdate.input)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, name, platform, input);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthenticationUpdate {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    platform: ").append(toIndentedString(platform)).append("\n");
    sb.append("    input: ").append(toIndentedString(input)).append("\n");
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
