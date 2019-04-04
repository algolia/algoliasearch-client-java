package com.algolia.search.models.synonyms;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

/**
 * Synonyms
 *
 * <p>* @see <a
 * href="https://www.algolia.com/doc/guides/managing-results/optimize-search-results/adding-synonyms/">Algolia.com</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("WeakerAccess")
public class Synonym implements Serializable {

  public Synonym() {}

  public static Synonym createSynonym(String objectID, List<String> synonyms) {
    return new Synonym().setObjectID(objectID).setSynonyms(synonyms).setType(SynonymType.SYNONYM);
  }

  public static Synonym createOneWaySynonym(String objectID, String input, List<String> synonyms) {
    return new Synonym()
        .setObjectID(objectID)
        .setInput(input)
        .setSynonyms(synonyms)
        .setType(SynonymType.ONE_WAY_SYNONYM);
  }

  public static Synonym createPlaceHolder(
      String objectID, String placeholder, List<String> replacements) {
    return new Synonym()
        .setObjectID(objectID)
        .setReplacements(replacements)
        .setPlaceholder(placeholder)
        .setType(SynonymType.PLACEHOLDER);
  }

  public static Synonym createAltCorrection1(
      String objectID, String word, List<String> corrections) {
    return new Synonym()
        .setObjectID(objectID)
        .setWord(word)
        .setCorrections(corrections)
        .setType(SynonymType.ALT_CORRECTION_1);
  }

  public static Synonym createAltCorrection2(
      String objectID, String word, List<String> corrections) {
    return new Synonym()
        .setObjectID(objectID)
        .setWord(word)
        .setCorrections(corrections)
        .setType(SynonymType.ALT_CORRECTION_2);
  }

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
