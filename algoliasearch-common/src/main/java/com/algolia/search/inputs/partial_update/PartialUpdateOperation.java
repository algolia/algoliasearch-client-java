package com.algolia.search.inputs.partial_update;

import java.io.Serializable;
import java.util.Map;

public interface PartialUpdateOperation extends Serializable {

  String getObjectID();

  Map<String, ?> toSerialize();
}
