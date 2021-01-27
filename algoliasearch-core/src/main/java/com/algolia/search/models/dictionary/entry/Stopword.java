package com.algolia.search.models.dictionary.entry;

import java.io.Serializable;
import java.util.Objects;

public class Stopword extends DictionaryEntry implements Serializable {

  private String word;
  private String state;

  // dummy constructor for deserialization
  protected Stopword() {}

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Stopword)) return false;
    if (!super.equals(o)) return false;
    Stopword stopword = (Stopword) o;
    return Objects.equals(word, stopword.word) && Objects.equals(state, stopword.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), word, state);
  }
}
