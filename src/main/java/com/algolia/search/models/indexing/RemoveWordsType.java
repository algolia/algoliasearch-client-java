package com.algolia.search.models.indexing;

public enum RemoveWordsType {
  // when a query does not return any result, the final word will be
  // removed until there is results. This option is useful on
  // e-commerce websites
  REMOVE_LAST_WORDS("LastWords"),
  // when a query does not return any result, the first word will be
  // removed until there is results. This option is useful on address
  // search.
  REMOVE_FIRST_WORDS("FirstWords"),
  // No specific processing is done when a query does not return any
  // result.
  REMOVE_NONE("none"),
  // When a query does not return any result, a second trial will be
  // made with all words as optional (which is equivalent to transforming
  // the AND operand between query terms in a OR operand)
  REMOVE_ALLOPTIONAL("allOptional");

  private final String name;

  RemoveWordsType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
