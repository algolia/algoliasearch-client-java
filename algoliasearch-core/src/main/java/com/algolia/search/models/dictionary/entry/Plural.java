package com.algolia.search.models.dictionary.entry;

import com.algolia.search.models.dictionary.entry.DictionaryEntry;
import java.io.Serializable;
import java.util.List;

public class Plural extends DictionaryEntry implements Serializable {

  private final List<String> words;

  public Plural(String objectID, String language, List<String> words) {
    super(objectID, language);
    this.words = words;
  }

  public List<String> getWords() {
    return words;
  }
}
