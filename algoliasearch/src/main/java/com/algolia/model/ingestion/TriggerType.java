// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.ingestion;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;

/**
 * The type of the task reflect how it can be used: - onDemand: a task that runs manually -
 * schedule: a task that runs regularly, following a given cron expression - subscription: a task
 * that runs after a subscription event is received from an integration (e.g. Webhook).
 */
public enum TriggerType {
  ON_DEMAND("onDemand"),

  SCHEDULE("schedule"),

  SUBSCRIPTION("subscription");

  private final String value;

  TriggerType(String value) {
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
  public static TriggerType fromValue(String value) {
    for (TriggerType b : TriggerType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}