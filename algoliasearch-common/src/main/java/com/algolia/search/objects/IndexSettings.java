package com.algolia.search.objects;

import com.algolia.search.Index;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndexSettings {

  /**
   * Deprecated: Use searchableAttributes
   */
  @Deprecated
  private List<String> attributesToIndex;
  private List<String> searchableAttributes;
  private List<String> numericAttributesToIndex;
  private List<String> attributesForFaceting;
  private String attributeForDistinct;
  private List<String> ranking;
  private List<String> customRanking;
  private String separatorsToIndex;
  private List<String> slaves;
  private List<String> replicas;
  private List<String> unretrievableAttributes;
  private Boolean allowCompressionOfIntegerArray;

  /* QUERY EXPANSION */
  private List<String> disableTypoToleranceOnWords;
  private List<String> disableTypoToleranceOnAttributes;

  /* DEFAULT QUERY PARAMETERS (CAN BE OVERRIDDEN AT QUERY-TIME) */
  private Integer minWordSizefor1Typo;
  private Integer minWordSizefor2Typos;
  private Integer hitsPerPage;
  private List<String> attributesToRetrieve;
  private List<String> attributesToHighlight;
  private List<String> attributesToSnippet;
  private String queryType;
  private Integer minProximity;
  private String highlightPreTag;
  private String highlightPostTag;
  private List<String> optionalWords;
  private Boolean allowTyposOnNumericTokens;
  private IgnorePlurals ignorePlurals;
  private Boolean advancedSyntax;
  private String removeWordsIfNoResults;
  private Boolean replaceSynonymsInHighlight;
  private Integer maxValuesPerFacet;
  private Distinct distinct;
  private String typoTolerance;
  private RemoveStopWords removeStopWords;
  private String snippetEllipsisText;

  /**
   * Deprecated: Use getSearchableAttributes
   */
  @Deprecated
  public List<String> getAttributesToIndex() {
    return attributesToIndex;
  }

  public List<String> getNumericAttributesToIndex() {
    return numericAttributesToIndex;
  }

  public List<String> getAttributesForFaceting() {
    return attributesForFaceting;
  }

  public String getAttributeForDistinct() {
    return attributeForDistinct;
  }

  public List<String> getRanking() {
    return ranking;
  }

  public List<String> getCustomRanking() {
    return customRanking;
  }

  public String getSeparatorsToIndex() {
    return separatorsToIndex;
  }

  public List<String> getSlaves() {
    return slaves;
  }

  public List<String> getUnretrievableAttributes() {
    return unretrievableAttributes;
  }

  public Boolean getAllowCompressionOfIntegerArray() {
    return allowCompressionOfIntegerArray;
  }

  public List<String> getDisableTypoToleranceOnWords() {
    return disableTypoToleranceOnWords;
  }

  public List<String> getDisableTypoToleranceOnAttributes() {
    return disableTypoToleranceOnAttributes;
  }

  public Integer getMinWordSizefor1Typo() {
    return minWordSizefor1Typo;
  }

  public Integer getMinWordSizefor2Typos() {
    return minWordSizefor2Typos;
  }

  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public List<String> getAttributesToRetrieve() {
    return attributesToRetrieve;
  }

  public List<String> getAttributesToHighlight() {
    return attributesToHighlight;
  }

  public List<String> getAttributesToSnippet() {
    return attributesToSnippet;
  }

  public String getQueryType() {
    return queryType;
  }

  public Integer getMinProximity() {
    return minProximity;
  }

  public String getHighlightPreTag() {
    return highlightPreTag;
  }

  public String getHighlightPostTag() {
    return highlightPostTag;
  }

  public List<String> getOptionalWords() {
    return optionalWords;
  }

  public Boolean getAllowTyposOnNumericTokens() {
    return allowTyposOnNumericTokens;
  }

  public IgnorePlurals getIgnorePlurals() {
    return ignorePlurals;
  }

  public Boolean getAdvancedSyntax() {
    return advancedSyntax;
  }

  public Boolean getReplaceSynonymsInHighlight() {
    return replaceSynonymsInHighlight;
  }

  public Integer getMaxValuesPerFacet() {
    return maxValuesPerFacet;
  }

  public Distinct getDistinct() {
    return distinct;
  }

  public String getTypoTolerance() {
    return typoTolerance;
  }

  public RemoveStopWords getRemoveStopWords() {
    return removeStopWords;
  }

  public String getSnippetEllipsisText() {
    return snippetEllipsisText;
  }

  /**
   * Deprecated: Use setSearchableAttributes
   */
  @Deprecated
  public IndexSettings setAttributesToIndex(List<String> attributesToIndex) {
    this.attributesToIndex = attributesToIndex;
    return this;
  }

  public IndexSettings setNumericAttributesToIndex(List<String> numericAttributesToIndex) {
    this.numericAttributesToIndex = numericAttributesToIndex;
    return this;
  }

  public IndexSettings setAttributesForFaceting(List<String> attributesForFaceting) {
    this.attributesForFaceting = attributesForFaceting;
    return this;
  }

  public IndexSettings setAttributeForDistinct(String attributeForDistinct) {
    this.attributeForDistinct = attributeForDistinct;
    return this;
  }

  public IndexSettings setRanking(List<String> ranking) {
    this.ranking = ranking;
    return this;
  }

  public IndexSettings setCustomRanking(List<String> customRanking) {
    this.customRanking = customRanking;
    return this;
  }

  public IndexSettings setSeparatorsToIndex(String separatorsToIndex) {
    this.separatorsToIndex = separatorsToIndex;
    return this;
  }

  public IndexSettings setSlaves(List<String> slaves) {
    this.slaves = slaves;
    return this;
  }

  public IndexSettings setUnretrievableAttributes(List<String> unretrievableAttributes) {
    this.unretrievableAttributes = unretrievableAttributes;
    return this;
  }

  public IndexSettings setAllowCompressionOfIntegerArray(Boolean allowCompressionOfIntegerArray) {
    this.allowCompressionOfIntegerArray = allowCompressionOfIntegerArray;
    return this;
  }

  public IndexSettings setDisableTypoToleranceOnWords(List<String> disableTypoToleranceOnWords) {
    this.disableTypoToleranceOnWords = disableTypoToleranceOnWords;
    return this;
  }

  public IndexSettings setDisableTypoToleranceOnAttributes(List<String> disableTypoToleranceOnAttributes) {
    this.disableTypoToleranceOnAttributes = disableTypoToleranceOnAttributes;
    return this;
  }

  public IndexSettings setMinWordSizefor1Typo(Integer minWordSizefor1Typo) {
    this.minWordSizefor1Typo = minWordSizefor1Typo;
    return this;
  }

  public IndexSettings setMinWordSizefor2Typos(Integer minWordSizefor2Typos) {
    this.minWordSizefor2Typos = minWordSizefor2Typos;
    return this;
  }

  public IndexSettings setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  public IndexSettings setAttributesToRetrieve(List<String> attributesToRetrieve) {
    this.attributesToRetrieve = attributesToRetrieve;
    return this;
  }

  public IndexSettings setAttributesToHighlight(List<String> attributesToHighlight) {
    this.attributesToHighlight = attributesToHighlight;
    return this;
  }

  public IndexSettings setAttributesToSnippet(List<String> attributesToSnippet) {
    this.attributesToSnippet = attributesToSnippet;
    return this;
  }

  public IndexSettings setQueryType(String queryType) {
    this.queryType = queryType;
    return this;
  }

  public IndexSettings setMinProximity(Integer minProximity) {
    this.minProximity = minProximity;
    return this;
  }

  public IndexSettings setHighlightPreTag(String highlightPreTag) {
    this.highlightPreTag = highlightPreTag;
    return this;
  }

  public IndexSettings setHighlightPostTag(String highlightPostTag) {
    this.highlightPostTag = highlightPostTag;
    return this;
  }

  public IndexSettings setOptionalWords(List<String> optionalWords) {
    this.optionalWords = optionalWords;
    return this;
  }

  public IndexSettings setAllowTyposOnNumericTokens(Boolean allowTyposOnNumericTokens) {
    this.allowTyposOnNumericTokens = allowTyposOnNumericTokens;
    return this;
  }

  @JsonIgnore
  public IndexSettings setIgnorePlurals(Boolean ignorePlurals) {
    return this.setIgnorePlurals(IgnorePlurals.of(ignorePlurals));
  }

  @JsonIgnore
  public IndexSettings setIgnorePlurals(List<String> ignorePlurals) {
    return this.setIgnorePlurals(IgnorePlurals.of(ignorePlurals));
  }

  @JsonProperty
  public IndexSettings setIgnorePlurals(IgnorePlurals ignorePlurals) {
    this.ignorePlurals = ignorePlurals;
    return this;
  }

  public IndexSettings setAdvancedSyntax(Boolean advancedSyntax) {
    this.advancedSyntax = advancedSyntax;
    return this;
  }

  public IndexSettings setReplaceSynonymsInHighlight(Boolean replaceSynonymsInHighlight) {
    this.replaceSynonymsInHighlight = replaceSynonymsInHighlight;
    return this;
  }

  public IndexSettings setMaxValuesPerFacet(Integer maxValuesPerFacet) {
    this.maxValuesPerFacet = maxValuesPerFacet;
    return this;
  }

  @JsonIgnore
  public IndexSettings setDistinct(Integer distinct) {
    return this.setDistinct(Distinct.of(distinct));
  }

  @JsonIgnore
  public IndexSettings setDistinct(Boolean distinct) {
    return this.setDistinct(Distinct.of(distinct));
  }

  @JsonProperty
  public IndexSettings setDistinct(Distinct distinct) {
    this.distinct = distinct;
    return this;
  }

  public IndexSettings setTypoTolerance(String typoTolerance) {
    this.typoTolerance = typoTolerance;
    return this;
  }

  @JsonIgnore
  public IndexSettings setRemoveStopWords(Boolean removeStopWords) {
    return this.setRemoveStopWords(RemoveStopWords.of(removeStopWords));
  }

  @JsonIgnore
  public IndexSettings setRemoveStopWords(List<String> removeStopWords) {
    return this.setRemoveStopWords(RemoveStopWords.of(removeStopWords));
  }

  @JsonProperty
  public IndexSettings setRemoveStopWords(RemoveStopWords removeStopWords) {
    this.removeStopWords = removeStopWords;
    return this;
  }

  public IndexSettings setSnippetEllipsisText(String snippetEllipsisText) {
    this.snippetEllipsisText = snippetEllipsisText;
    return this;
  }

  public String getRemoveWordsIfNoResults() {
    return removeWordsIfNoResults;
  }

  public IndexSettings setRemoveWordsIfNoResults(String removeWordsIfNoResults) {
    this.removeWordsIfNoResults = removeWordsIfNoResults;
    return this;
  }

  public List<String> getReplicas() {
    return replicas;
  }

  public IndexSettings setReplicas(List<String> replicas) {
    this.replicas = replicas;
    return this;
  }

  public List<String> getSearchableAttributes() {
    return searchableAttributes;
  }

  public IndexSettings setSearchableAttributes(List<String> searchableAttributes) {
    this.searchableAttributes = searchableAttributes;
    return this;
  }
}
