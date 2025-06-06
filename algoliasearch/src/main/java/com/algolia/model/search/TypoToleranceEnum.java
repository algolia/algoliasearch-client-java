// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.search;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;

/**
 * - `min`. Return matches with the lowest number of typos. For example, if you have matches without
 * typos, only include those. But if there are no matches without typos (with 1 typo), include
 * matches with 1 typo (2 typos). - `strict`. Return matches with the two lowest numbers of typos.
 * With `strict`, the Typo ranking criterion is applied first in the `ranking` setting.
 */
@JsonDeserialize(as = TypoToleranceEnum.class)
public enum TypoToleranceEnum implements TypoTolerance {
  MIN("min"),

  STRICT("strict"),

  TRUE("true"),

  FALSE("false");

  private final String value;

  TypoToleranceEnum(String value) {
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
  public static TypoToleranceEnum fromValue(String value) {
    for (TypoToleranceEnum b : TypoToleranceEnum.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
