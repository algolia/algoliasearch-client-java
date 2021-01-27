package com.algolia.search.models.dictionary;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * Map of language ISO code supported by the dictionary (e.g., “en” for English) to a boolean value.
 * When set to true, the standard entries for the language are disabled. Changes are set for the
 * given languages only. To re-enable standard entries, set the language to false. To reset settings
 * to default values, set dictionary to `null`.
 */
public class DisableStandardEntries implements Serializable {

  /** Settings for the stop word dictionary. */
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
