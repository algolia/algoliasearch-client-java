// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.search;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** GetObjectsResponse */
public class GetObjectsResponse<T> {

  @JsonProperty("message")
  private String message;

  @JsonProperty("results")
  private List<T> results = new ArrayList<>();

  public GetObjectsResponse<T> setMessage(String message) {
    this.message = message;
    return this;
  }

  /** An optional status message. */
  @javax.annotation.Nullable
  public String getMessage() {
    return message;
  }

  public GetObjectsResponse<T> setResults(List<T> results) {
    this.results = results;
    return this;
  }

  public GetObjectsResponse<T> addResults(T resultsItem) {
    this.results.add(resultsItem);
    return this;
  }

  /** Retrieved records. */
  @javax.annotation.Nonnull
  public List<T> getResults() {
    return results;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetObjectsResponse<?> getObjectsResponse = (GetObjectsResponse<?>) o;
    return Objects.equals(this.message, getObjectsResponse.message) && Objects.equals(this.results, getObjectsResponse.results);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, results);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetObjectsResponse {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    results: ").append(toIndentedString(results)).append("\n");
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
