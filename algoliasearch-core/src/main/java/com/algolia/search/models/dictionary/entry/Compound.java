package com.algolia.search.models.dictionary.entry;

import java.io.Serializable;
import java.util.List;

public class Compound extends DictionaryEntry implements Serializable {

  private String words;
  private List<String> decomposition;

  public Compound(String objectID, String language, String words, List<String> decomposition) {
    super(objectID, language);
    this.words = words;
    this.decomposition = decomposition;
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
}
