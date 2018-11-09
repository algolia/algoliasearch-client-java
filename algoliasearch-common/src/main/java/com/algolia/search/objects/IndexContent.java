package com.algolia.search.objects;

public class IndexContent<T> {

  private boolean settings;
  private boolean rules;
  private boolean synonyms;
  private Iterable<T> objects;

  public boolean isSafeOperation() {
    return safeOperation;
  }

  public void setSafeOperation(boolean safeOperation) {
    this.safeOperation = safeOperation;
  }

  private boolean safeOperation;

  public boolean isSettings() {
    return settings;
  }

  public void setSettings(boolean settings) {
    this.settings = settings;
  }

  public boolean isRules() {
    return rules;
  }

  public void setRules(boolean rules) {
    this.rules = rules;
  }

  public boolean isSynonyms() {
    return synonyms;
  }

  public void setSynonyms(boolean synonyms) {
    this.synonyms = synonyms;
  }

  public Iterable<T> getObjects() {
    return objects;
  }

  public void setObjects(Iterable<T> objects) {
    this.objects = objects;
  }
}
