package com.algolia.search.models.dictionary.entry;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Plural implements DictionaryEntry, Serializable {

  private String objectID;
  private String language;
  private final List<String> words;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Plural)) return false;
    if (!super.equals(o)) return false;
    Plural plural = (Plural) o;
    return Objects.equals(objectID, plural.objectID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), objectID);
  }
}
