package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Synonym {
  public String getObjectID() {
    return objectID;
  }

  public Synonym setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  public String getType() {
    return type;
  }

  public Synonym setType(String type) {
    this.type = type;
    return this;
  }

  public List<String> getSynonyms() {
    return synonyms;
  }

  public Synonym setSynonyms(List<String> synonyms) {
    this.synonyms = synonyms;
    return this;
  }

  public String getInput() {
    return input;
  }

  public Synonym setInput(String input) {
    this.input = input;
    return this;
  }

  public String getWord() {
    return word;
  }

  public Synonym setWord(String word) {
    this.word = word;
    return this;
  }

  public List<String> getCorrections() {
    return corrections;
  }

  public Synonym setCorrections(List<String> corrections) {
    this.corrections = corrections;
    return this;
  }

  public String getPlaceholder() {
    return placeholder;
  }

  public Synonym setPlaceholder(String placeholder) {
    this.placeholder = placeholder;
    return this;
  }

  public List<String> getReplacements() {
    return replacements;
  }

  public Synonym setReplacements(List<String> replacements) {
    this.replacements = replacements;
    return this;
  }

  private String objectID;
  private String type;
  private List<String> synonyms;
  private String input;
  private String word;
  private List<String> corrections;
  private String placeholder;
  private List<String> replacements;
}
