// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.analytics;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;

/**
 * Method for ordering results. `clickThroughRate`, `conversionRate` and `averageClickPosition` are
 * only available if the `clickAnalytics` parameter is `true`.
 */
public enum OrderBy {
  SEARCH_COUNT("searchCount"),

  CLICK_THROUGH_RATE("clickThroughRate"),

  CONVERSION_RATE("conversionRate"),

  AVERAGE_CLICK_POSITION("averageClickPosition");

  private final String value;

  OrderBy(String value) {
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
  public static OrderBy fromValue(String value) {
    for (OrderBy b : OrderBy.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}