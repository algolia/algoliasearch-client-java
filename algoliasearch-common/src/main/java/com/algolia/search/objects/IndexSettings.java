package com.algolia.search.objects;

import java.util.List;

public class IndexSettings {

  private List<String> attributesToIndex;
  private List<String> numericAttributesToIndex;
  private List<String> attributesForFaceting;
  private String attributeForDistinc;
  private List<String> ranking;
  private List<String> customRanking;
  private String separatorsToInde;
  private List<String> slaves;
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
  private Boolean ignorePlurals;
  private Boolean advancedSyntax;
  private Boolean replaceSynonymsInHighlight;
  private Integer maxValuesPerFacet;
  private Integer distinct;
  private String typoTolerance;
  private Boolean removeStopWords;
  private String snippetEllipsisText;

  public List<String> getAttributesToIndex() {
    return attributesToIndex;
  }

  public List<String> getNumericAttributesToIndex() {
    return numericAttributesToIndex;
  }

  public List<String> getAttributesForFaceting() {
    return attributesForFaceting;
  }

  public String getAttributeForDistinc() {
    return attributeForDistinc;
  }

  public List<String> getRanking() {
    return ranking;
  }

  public List<String> getCustomRanking() {
    return customRanking;
  }

  public String getSeparatorsToInde() {
    return separatorsToInde;
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

  public Boolean getIgnorePlurals() {
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

  public Integer getDistinct() {
    return distinct;
  }

  public String getTypoTolerance() {
    return typoTolerance;
  }

  public Boolean getRemoveStopWords() {
    return removeStopWords;
  }

  public String getSnippetEllipsisText() {
    return snippetEllipsisText;
  }

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

  public IndexSettings setAttributeForDistinc(String attributeForDistinc) {
    this.attributeForDistinc = attributeForDistinc;
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

  public IndexSettings setSeparatorsToInde(String separatorsToInde) {
    this.separatorsToInde = separatorsToInde;
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

  public IndexSettings setIgnorePlurals(Boolean ignorePlurals) {
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

  public IndexSettings setDistinct(Integer distinct) {
    this.distinct = distinct;
    return this;
  }

  public IndexSettings setTypoTolerance(String typoTolerance) {
    this.typoTolerance = typoTolerance;
    return this;
  }

  public IndexSettings setRemoveStopWords(Boolean removeStopWords) {
    this.removeStopWords = removeStopWords;
    return this;
  }

  public IndexSettings setSnippetEllipsisText(String snippetEllipsisText) {
    this.snippetEllipsisText = snippetEllipsisText;
    return this;
  }
}
