package com.algolia.search.models.dictionary.entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class DictionaryEntry implements Serializable {

  public static Stopword stopword(
      String objectID, String language, String word, String state) {
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
}
