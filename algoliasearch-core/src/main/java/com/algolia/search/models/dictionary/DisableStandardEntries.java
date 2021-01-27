package com.algolia.search.models.dictionary;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class DisableStandardEntries implements Serializable {

  private Map<String, Boolean> stopwords;

  public DisableStandardEntries() {}

  public DisableStandardEntries(Map<String, Boolean> stopwords) {
    this.stopwords = stopwords;
  }

  public Map<String, Boolean> getStopwords() {
    return stopwords;
  }

  public DisableStandardEntries setStopwords(Map<String, Boolean> stopwords) {
    this.stopwords = stopwords;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DisableStandardEntries)) return false;
    DisableStandardEntries that = (DisableStandardEntries) o;
    return Objects.equals(stopwords, that.stopwords);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stopwords);
  }

  @Override
  public String toString() {
    return "DisableStandardEntries{" + "stopwords=" + stopwords + '}';
  }
}
