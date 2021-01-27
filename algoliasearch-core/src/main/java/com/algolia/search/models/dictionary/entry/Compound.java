package com.algolia.search.models.dictionary.entry;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Compound implements DictionaryEntry, Serializable {

  private String objectID;
  private String language;
  private String words;
  private List<String> decomposition;

  public Compound(String objectID, String language, String words, List<String> decomposition) {
    this.objectID = objectID;
    this.language = language;
    this.words = words;
    this.decomposition = decomposition;
  }

  @Override
  public String getObjectID() {
    return objectID;
  }

  public Compound setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  @Override
  public String getLanguage() {
    return language;
  }

  public Compound setLanguage(String language) {
    this.language = language;
    return this;
  }

  public String getWords() {
    return words;
  }

  public Compound setWords(String words) {
    this.words = words;
    return this;
  }

  public List<String> getDecomposition() {
    return decomposition;
  }

  public Compound setDecomposition(List<String> decomposition) {
    this.decomposition = decomposition;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Compound)) return false;
    Compound compound = (Compound) o;
    return Objects.equals(objectID, compound.objectID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectID);
  }

  @Override
  public String toString() {
    return "Compound{"
        + "objectID='"
        + objectID
        + '\''
        + ", language='"
        + language
        + '\''
        + ", words='"
        + words
        + '\''
        + ", decomposition="
        + decomposition
        + '}';
  }
}
