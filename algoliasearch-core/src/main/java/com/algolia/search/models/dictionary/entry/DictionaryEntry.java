package com.algolia.search.models.dictionary.entry;

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

  String getObjectID();

  String getLanguage();
}
