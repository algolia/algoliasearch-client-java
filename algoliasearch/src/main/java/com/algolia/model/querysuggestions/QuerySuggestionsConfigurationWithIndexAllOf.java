// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.querysuggestions;

import com.fasterxml.jackson.annotation.*;
import java.util.Objects;

/** QuerySuggestionsConfigurationWithIndexAllOf */
public class QuerySuggestionsConfigurationWithIndexAllOf {

  @JsonProperty("indexName")
  private String indexName;

  public QuerySuggestionsConfigurationWithIndexAllOf setIndexName(String indexName) {
    this.indexName = indexName;
    return this;
  }

  /** Query Suggestions index name. */
  @javax.annotation.Nonnull
  public String getIndexName() {
    return indexName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QuerySuggestionsConfigurationWithIndexAllOf querySuggestionsConfigurationWithIndexAllOf =
      (QuerySuggestionsConfigurationWithIndexAllOf) o;
    return Objects.equals(this.indexName, querySuggestionsConfigurationWithIndexAllOf.indexName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(indexName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QuerySuggestionsConfigurationWithIndexAllOf {\n");
    sb.append("    indexName: ").append(toIndentedString(indexName)).append("\n");
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
