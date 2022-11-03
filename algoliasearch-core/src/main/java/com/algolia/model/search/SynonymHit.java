// This file is generated, manual changes will be lost - read more on
// https://github.com/algolia/api-clients-automation.

package com.algolia.model.search;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Synonym object. */
public class SynonymHit {

  @JsonProperty("objectID")
  private String objectID;

  @JsonProperty("type")
  private SynonymType type;

  @JsonProperty("synonyms")
  private List<String> synonyms;

  @JsonProperty("input")
  private String input;

  @JsonProperty("word")
  private String word;

  @JsonProperty("corrections")
  private List<String> corrections;

  @JsonProperty("placeholder")
  private String placeholder;

  @JsonProperty("replacements")
  private List<String> replacements;

  public SynonymHit setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  /**
   * Unique identifier of the synonym object to be created or updated.
   *
   * @return objectID
   */
  @javax.annotation.Nonnull
  public String getObjectID() {
    return objectID;
  }

  public SynonymHit setType(SynonymType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type
   */
  @javax.annotation.Nonnull
  public SynonymType getType() {
    return type;
  }

  public SynonymHit setSynonyms(List<String> synonyms) {
    this.synonyms = synonyms;
    return this;
  }

  public SynonymHit addSynonyms(String synonymsItem) {
    if (this.synonyms == null) {
      this.synonyms = new ArrayList<>();
    }
    this.synonyms.add(synonymsItem);
    return this;
  }

  /**
   * Words or phrases to be considered equivalent.
   *
   * @return synonyms
   */
  @javax.annotation.Nullable
  public List<String> getSynonyms() {
    return synonyms;
  }

  public SynonymHit setInput(String input) {
    this.input = input;
    return this;
  }

  /**
   * Word or phrase to appear in query strings (for onewaysynonym).
   *
   * @return input
   */
  @javax.annotation.Nullable
  public String getInput() {
    return input;
  }

  public SynonymHit setWord(String word) {
    this.word = word;
    return this;
  }

  /**
   * Word or phrase to appear in query strings (for altcorrection1 and altcorrection2).
   *
   * @return word
   */
  @javax.annotation.Nullable
  public String getWord() {
    return word;
  }

  public SynonymHit setCorrections(List<String> corrections) {
    this.corrections = corrections;
    return this;
  }

  public SynonymHit addCorrections(String correctionsItem) {
    if (this.corrections == null) {
      this.corrections = new ArrayList<>();
    }
    this.corrections.add(correctionsItem);
    return this;
  }

  /**
   * Words to be matched in records.
   *
   * @return corrections
   */
  @javax.annotation.Nullable
  public List<String> getCorrections() {
    return corrections;
  }

  public SynonymHit setPlaceholder(String placeholder) {
    this.placeholder = placeholder;
    return this;
  }

  /**
   * Token to be put inside records.
   *
   * @return placeholder
   */
  @javax.annotation.Nullable
  public String getPlaceholder() {
    return placeholder;
  }

  public SynonymHit setReplacements(List<String> replacements) {
    this.replacements = replacements;
    return this;
  }

  public SynonymHit addReplacements(String replacementsItem) {
    if (this.replacements == null) {
      this.replacements = new ArrayList<>();
    }
    this.replacements.add(replacementsItem);
    return this;
  }

  /**
   * List of query words that will match the token.
   *
   * @return replacements
   */
  @javax.annotation.Nullable
  public List<String> getReplacements() {
    return replacements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SynonymHit synonymHit = (SynonymHit) o;
    return (
      Objects.equals(this.objectID, synonymHit.objectID) &&
      Objects.equals(this.type, synonymHit.type) &&
      Objects.equals(this.synonyms, synonymHit.synonyms) &&
      Objects.equals(this.input, synonymHit.input) &&
      Objects.equals(this.word, synonymHit.word) &&
      Objects.equals(this.corrections, synonymHit.corrections) &&
      Objects.equals(this.placeholder, synonymHit.placeholder) &&
      Objects.equals(this.replacements, synonymHit.replacements)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectID, type, synonyms, input, word, corrections, placeholder, replacements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SynonymHit {\n");
    sb.append("    objectID: ").append(toIndentedString(objectID)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    synonyms: ").append(toIndentedString(synonyms)).append("\n");
    sb.append("    input: ").append(toIndentedString(input)).append("\n");
    sb.append("    word: ").append(toIndentedString(word)).append("\n");
    sb.append("    corrections: ").append(toIndentedString(corrections)).append("\n");
    sb.append("    placeholder: ").append(toIndentedString(placeholder)).append("\n");
    sb.append("    replacements: ").append(toIndentedString(replacements)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}