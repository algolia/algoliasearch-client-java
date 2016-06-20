package com.algolia.search.inputs.partial_update;

class TagOperation {

  private final String value;
  private final String _operation;

  TagOperation(String value, String operation) {
    this.value = value;
    _operation = operation;
  }

  public String getValue() {
    return value;
  }

  public String get_operation() {
    return _operation;
  }
}
