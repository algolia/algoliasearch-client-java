package com.algolia.search.models.dictionary;

import com.algolia.search.models.dictionary.entry.Compound;
import com.algolia.search.models.dictionary.entry.Plural;
import com.algolia.search.models.dictionary.entry.Stopword;

/** Represents a linguistic resources provided by Algolia. */
public enum Dictionary {
  PLURALS("plurals", Plural.class),
  STOPWORDS("stopwords", Stopword.class),
  COMPOUNDS("compounds", Compound.class);

  private final String raw;
  private final Class<?> entryType;

  Dictionary(String raw, Class<?> entryType) {
    this.raw = raw;
    this.entryType = entryType;
  }

  @Override
  public String toString() {
    return raw;
  }

  public Class<?> getEntryType() {
    return entryType;
  }
}
