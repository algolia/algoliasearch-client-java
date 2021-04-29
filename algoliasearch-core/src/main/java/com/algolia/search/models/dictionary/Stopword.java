package com.algolia.search.models.dictionary;

import java.io.Serializable;
import java.util.Objects;

/** Represents an entry for Stopword dictionary. */
public class Stopword implements DictionaryEntry, Serializable {

  private String objectID;
  private String language;
  /**
   * The stop word being added or modified. When word already exists in the standard dictionary
   * provided by Algolia, the entry can be overridden by the one provided by the user.
   */
  private String word;
  /** The state of the entry. Can be either "enabled" or "disabled". */
  private String state;

  // dummy constructor for deserialization
  public Stopword() {}

  public Stopword(String objectID, String language, String word, String state) {
    this.objectID = objectID;
    this.language = language;
    this.word = word;
    this.state = state;
  }

  @Override
  public String getObjectID() {
    return objectID;
  }

  public Stopword setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  @Override
  public String getLanguage() {
    return language;
  }

  public Stopword setLanguage(String language) {
    this.language = language;
    return this;
  }

  public String getWord() {
    return word;
  }

  public Stopword setWord(String word) {
    this.word = word;
    return this;
  }

  public String getState() {
    return state;
  }

  public Stopword setState(String state) {
    this.state = state;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Stopword)) return false;
    Stopword stopword = (Stopword) o;
    return Objects.equals(objectID, stopword.objectID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectID);
  }

  @Override
  public String toString() {
    return "Stopword{"
        + "objectID='"
        + objectID
        + '\''
        + ", language='"
        + language
        + '\''
        + ", word='"
        + word
        + '\''
        + ", state='"
        + state
        + '\''
        + '}';
  }
}
