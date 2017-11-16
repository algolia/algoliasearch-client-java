package com.algolia.search.inputs.partial_update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IncrementValueOperation implements PartialUpdateOperation {

  private final Map<String, ?> map;
  private final String objectID;

  /**
   * Increment a value
   *
   * @param objectID the id of the object to update
   * @param attributeName the name of the attribute
   * @param increment the number to increment
   */
  public IncrementValueOperation(String objectID, String attributeName, int increment) {
    this.objectID = objectID;
    this.map =
        ImmutableMap.of(
            "objectID", objectID, attributeName, new NumericOperation(increment, "Increment"));
  }

  @Override
  public String getObjectID() {
    return objectID;
  }

  @Override
  public Map<String, ?> toSerialize() {
    return map;
  }
}
