package com.algolia.search.inputs.partial_update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddValueOperation implements PartialUpdateOperation {

  private final Map<String, ?> map;
  private final String objectID;

  /**
   * Add a value to an existing array
   *
   * @param objectID the id of the object to update
   * @param attributeToAddIn the name of the attribute to add the value
   * @param valueToAdd the value to append
   */
  public AddValueOperation(String objectID, String attributeToAddIn, String valueToAdd) {
    this.objectID = objectID;
    this.map =
        ImmutableMap.of(
            "objectID", objectID, attributeToAddIn, new TagOperation(valueToAdd, "Add"));
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
