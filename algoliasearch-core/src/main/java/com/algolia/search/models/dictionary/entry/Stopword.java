package com.algolia.search.models.dictionary.entry;

import com.algolia.search.models.dictionary.entry.DictionaryEntry;
import java.io.Serializable;

public class Stopword extends DictionaryEntry implements Serializable {

  private final String word;
  private final String state;

  public Stopword(String objectID, String language, String word, String state) {
    super(objectID, language);
    this.word = word;
    this.state = state;
  }

  public String getWord() {
    return word;
  }

  public String getState() {
    return state;
  }
}
