package com.algolia.search.models.dictionary;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/** Represents an entry for Plurals dictionary. */
public class Plural implements DictionaryEntry, Serializable {

  private String objectID;
  private String language;
  /**
   * List of word declensions. The entry overrides existing entries when any of these words are
   * defined in the standard dictionary provided by Algolia.
   */
  private List<String> words;

  // dummy constructor for deserialization
  public Plural() {}

  public Plural(String objectID, String language, List<String> words) {
    this.objectID = objectID;
    this.language = language;
    this.words = words;
  }

  @Override
  public String getObjectID() {
    return objectID;
  }

  public Plural setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  @Override
  public String getLanguage() {
    return language;
  }

  public Plural setLanguage(String language) {
    this.language = language;
    return this;
  }

  public List<String> getWords() {
    return words;
  }

  public Plural setWords(List<String> words) {
    this.words = words;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Plural)) return false;
    Plural plural = (Plural) o;
    return Objects.equals(objectID, plural.objectID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectID);
  }

  @Override
  public String toString() {
    return "Plural{"
        + "objectID='"
        + objectID
        + '\''
        + ", language='"
        + language
        + '\''
        + ", words="
        + words
        + '}';
  }
}
