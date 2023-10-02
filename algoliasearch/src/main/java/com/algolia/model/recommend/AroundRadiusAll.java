// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.recommend;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;

/** Gets or Sets aroundRadiusAll */
@JsonDeserialize(as = AroundRadiusAll.class)
public enum AroundRadiusAll implements AroundRadius {
  ALL("all");

  private final String value;

  AroundRadiusAll(String value) {
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
  public static AroundRadiusAll fromValue(String value) {
    for (AroundRadiusAll b : AroundRadiusAll.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}