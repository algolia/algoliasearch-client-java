package com.algolia.search.models.dictionary;

import com.algolia.search.models.dictionary.entry.Compound;
import com.algolia.search.models.dictionary.entry.DictionaryEntry;
import com.algolia.search.models.dictionary.entry.Plural;
import com.algolia.search.models.dictionary.entry.Stopword;

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
