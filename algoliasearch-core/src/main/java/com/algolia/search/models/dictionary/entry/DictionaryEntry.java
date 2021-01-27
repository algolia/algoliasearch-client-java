package com.algolia.search.models.dictionary.entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class DictionaryEntry implements Serializable {

  public static Stopword stopword(String objectID, String language, String word, String state) {
    return new Stopword(objectID, language, word, state);
  }

  public static Plural plural(String objectID, String language, List<String> words) {
    return new Plural(objectID, language, words);
  }

  public static Compound compound(
      String objectID, String language, String words, List<String> decomposition) {
    return new Compound(objectID, language, words, decomposition);
  }

  private String objectID;
  private String language;

  // dummy constructor for deserialization
  protected DictionaryEntry() {}

  protected DictionaryEntry(String objectID, String language) {
    this.objectID = objectID;
    this.language = language;
  }

  public String getObjectID() {
    return objectID;
  }

  public DictionaryEntry setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  public String getLanguage() {
    return language;
  }

  public DictionaryEntry setLanguage(String language) {
    this.language = language;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DictionaryEntry)) {
      return false;
    }
    DictionaryEntry that = (DictionaryEntry) o;
    return Objects.equals(objectID, that.objectID) && Objects.equals(language, that.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectID, language);
  }
}
