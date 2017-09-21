package com.algolia.search.inputs.partial_update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddValueUniqueOperation implements PartialUpdateOperation {

  private final Map<String, ?> map;
  private final String objectID;

  /**
   * Add a value to an existing array, only if the value is not yet present
   *
   * @param objectID the id of the object to update
   * @param attributeToAddIn the name of the attribute to add the value
   * @param valueToAddIfDoesntExists the value to append
   */
  public AddValueUniqueOperation(
      String objectID, String attributeToAddIn, String valueToAddIfDoesntExists) {
    this.objectID = objectID;
    this.map =
        ImmutableMap.of(
            "objectID",
            objectID,
            attributeToAddIn,
            new TagOperation(valueToAddIfDoesntExists, "AddUnique"));
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
