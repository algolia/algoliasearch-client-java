package com.algolia.search.objects;

import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import java.util.List;

public class IndexContent<T> {

  private IndexSettings settings;
  private List<Rule> rules;
  private List<AbstractSynonym> synonyms;
  private Iterable<T> objects;
  private boolean safeOperation;

  public IndexSettings getSettings() {
    return settings;
  }

  public void setSettings(IndexSettings settings) {
    this.settings = settings;
  }

  public List<Rule> getRules() {
    return rules;
  }

  public void setRules(List<Rule> rules) {
    this.rules = rules;
  }

  public List<AbstractSynonym> getSynonyms() {
    return synonyms;
  }

  public void setSynonyms(List<AbstractSynonym> synonyms) {
    this.synonyms = synonyms;
  }

  public Iterable<T> getObjects() {
    return objects;
  }

  public void setObjects(Iterable<T> objects) {
    this.objects = objects;
  }

  public boolean isSafeOperation() {
    return safeOperation;
  }

  public void setSafeOperation(boolean safeOperation) {
    this.safeOperation = safeOperation;
  }
}
