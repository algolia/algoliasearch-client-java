package com.algolia.search.objects;

import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import java.util.ArrayList;
import java.util.List;

public class IndexContent<T> {

  private IndexSettings settings;
  private List<Rule> rules;
  private List<AbstractSynonym> synonyms;
  private Iterable<T> objects;

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

  public List<String> getScopes() {
    List<String> scopes = new ArrayList<>();

    if (getRules() == null) {
      scopes.add("rules");
    }

    if (getSettings() == null) {
      scopes.add("settings");
    }

    if (getSynonyms() == null) {
      scopes.add("synonyms");
    }

    return scopes;
  }
}
