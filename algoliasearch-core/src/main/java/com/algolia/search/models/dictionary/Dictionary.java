package com.algolia.search.models.dictionary;

/** Represents a linguistic resources provided by Algolia. */
public enum Dictionary {
  PLURALS("plurals"),
  STOPWORDS("stopwords"),
  COMPOUNDS("compounds");

  private final String raw;

  Dictionary(String raw) {
    this.raw = raw;
  }

  @Override
  public String toString() {
    return raw;
  }
}
