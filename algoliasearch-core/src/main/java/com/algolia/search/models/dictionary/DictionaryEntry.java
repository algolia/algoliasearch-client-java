package com.algolia.search.models.dictionary;

import java.util.List;

public interface DictionaryEntry {

  static Stopword stopword(String objectID, String language, String word, String state) {
    return new Stopword(objectID, language, word, state);
  }

  static Plural plural(String objectID, String language, List<String> words) {
    return new Plural(objectID, language, words);
  }

  static Compound compound(
      String objectID, String language, String words, List<String> decomposition) {
    return new Compound(objectID, language, words, decomposition);
  }

  /** Unique identifier of the entry to add or override. */
  String getObjectID();

  /** Language ISO code supported by the dictionary. */
  String getLanguage();
}
