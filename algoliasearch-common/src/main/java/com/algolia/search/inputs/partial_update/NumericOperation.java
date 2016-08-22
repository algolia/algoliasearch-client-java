package com.algolia.search.inputs.partial_update;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class NumericOperation {

  private final Integer value;
  private final String _operation;

  NumericOperation(Integer value, String operation) {
    this.value = value;
    _operation = operation;
  }

  public Integer getValue() {
    return value;
  }

  public String get_operation() {
    return _operation;
  }
}
