package com.algolia.search.models.dictionary.entry;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Plural extends DictionaryEntry implements Serializable {

  private final List<String> words;

  public Plural(String objectID, String language, List<String> words) {
    super(objectID, language);
    this.words = words;
  }

  public List<String> getWords() {
    return words;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Plural)) return false;
    if (!super.equals(o)) return false;
    Plural plural = (Plural) o;
    return Objects.equals(words, plural.words);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), words);
  }
}
