package com.algolia.search.inputs.partial_update;

import java.util.Map;

public interface PartialUpdateOperation {

  String getObjectID();
  Map<String, ?> toSerialize();

}
