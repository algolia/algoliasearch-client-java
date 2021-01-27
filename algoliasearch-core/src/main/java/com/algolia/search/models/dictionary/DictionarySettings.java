package com.algolia.search.models.dictionary;

import java.io.Serializable;
import java.util.Objects;

public class DictionarySettings implements Serializable {

  private DisableStandardEntries disableStandardEntries;

  public DictionarySettings() {}

  public DictionarySettings(DisableStandardEntries disableStandardEntries) {
    this.disableStandardEntries = disableStandardEntries;
  }

  public DisableStandardEntries getDisableStandardEntries() {
    return disableStandardEntries;
  }

  public DictionarySettings setDisableStandardEntries(
      DisableStandardEntries disableStandardEntries) {
    this.disableStandardEntries = disableStandardEntries;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DictionarySettings)) return false;
    DictionarySettings that = (DictionarySettings) o;
    return Objects.equals(disableStandardEntries, that.disableStandardEntries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(disableStandardEntries);
  }

  @Override
  public String toString() {
    return "DictionarySettings{" + "disableStandardEntries=" + disableStandardEntries + '}';
  }
}
