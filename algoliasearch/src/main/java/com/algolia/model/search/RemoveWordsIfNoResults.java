// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.search;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;

/**
 * Strategy to [remove
 * words](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/empty-or-insufficient-results/in-depth/why-use-remove-words-if-no-results/)
 * from the query when it doesn't match any hits.
 */
public enum RemoveWordsIfNoResults {
  NONE("none"),

  LAST_WORDS("lastWords"),

  FIRST_WORDS("firstWords"),

  ALL_OPTIONAL("allOptional");

  private final String value;

  RemoveWordsIfNoResults(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static RemoveWordsIfNoResults fromValue(String value) {
    for (RemoveWordsIfNoResults b : RemoveWordsIfNoResults.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
