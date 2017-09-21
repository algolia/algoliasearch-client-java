package com.algolia.search.inputs.partial_update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoveValueOperation implements PartialUpdateOperation {

  private final Map<String, ?> map;
  private final String objectID;

  /**
   * Remove a value to an existing array, only if the value is not yet present
   *
   * @param objectID the id of the object to update
   * @param attributeToRemoveIn the name of the attribute to remove the value
   * @param valueToRemove the value to remove
   */
  public RemoveValueOperation(String objectID, String attributeToRemoveIn, String valueToRemove) {
    this.objectID = objectID;
    this.map =
        ImmutableMap.of(
            "objectID", objectID, attributeToRemoveIn, new TagOperation(valueToRemove, "Remove"));
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
