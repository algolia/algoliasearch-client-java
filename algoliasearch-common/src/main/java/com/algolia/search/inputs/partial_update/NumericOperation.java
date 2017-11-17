package com.algolia.search.inputs.partial_update;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class NumericOperation {

  private final Long value;
  private final String _operation;

  NumericOperation(Integer value, String operation) {
    this(value.longValue(), operation);
  }

  NumericOperation(Long value, String operation) {
    this.value = value;
    _operation = operation;
  }

  public Long getValue() {
    return value;
  }

  public String get_operation() {
    return _operation;
  }
}
