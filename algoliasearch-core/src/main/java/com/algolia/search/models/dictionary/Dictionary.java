package com.algolia.search.models.dictionary;

/** Represents a linguistic resources provided by Algolia. */
public enum Dictionary {
  PLURALS("plurals", Plural.class),
  STOPWORDS("stopwords", Stopword.class),
  COMPOUNDS("compounds", Compound.class);

  private final String name;
  private final Class<? extends DictionaryEntry> entry;

  Dictionary(String name, Class<? extends DictionaryEntry> entryType) {
    this.name = name;
    this.entry = entryType;
  }

  @Override
  public String toString() {
    return name;
  }

  public Class<?> getEntry() {
    return entry;
  }
}
