package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexSettings implements Serializable {

  /* advanced */
  private String attributeForDistinct;
  private Distinct distinct;
  private Boolean replaceSynonymsInHighlight;
  private Map<String, List<String>> placeholders;
  private Integer minProximity;
  private List<String> responseFields;
  private Integer maxFacetHits;
  private String keepDiacriticsOnCharacters;
  private List<String> queryLanguages;

  /* attributes */
  /** Deprecated: use {@link #searchableAttributes} */
  @Deprecated private List<String> attributesToIndex;

  private List<String> searchableAttributes;
  private List<String> attributesForFaceting;
  private List<String> unretrievableAttributes;
  private List<String> attributesToRetrieve;
  private List<String> camelCaseAttributes;
  private Map<String, List<String>> decompoundedAttributes;

  /* filtering-faceting */
  private Long maxValuesPerFacet;
  private String sortFacetValuesBy;

  /* geo-search */
  // nothing on Index Settings

  /* highlighting-snippeting */
  private List<String> attributesToHighlight;
  private List<String> attributesToSnippet;
  private String highlightPreTag;
  private String highlightPostTag;
  private String snippetEllipsisText;
  private Boolean restrictHighlightAndSnippetArrays;

  /* pagination */
  private Long hitsPerPage;
  private Long paginationLimitedTo;

  /* performance */
  /** Deprecated: use {@link #numericAttributesForFiltering} */
  @Deprecated private List<String> numericAttributesToIndex;

  private List<String> numericAttributesForFiltering;
  private Boolean allowCompressionOfIntegerArray;

  /* query rules */
  private Boolean enableRules;

  /* query strategy */
  private String queryType;
  private String removeWordsIfNoResults;
  private Boolean advancedSyntax;
  private List<String> optionalWords;
  private RemoveStopWords removeStopWords;
  private List<String> disablePrefixOnAttributes;
  private List<String> disableExactOnAttributes;
  private String exactOnSingleWordQuery;
  private List<String> alternativesAsExact;

  /* ranking */
  private List<String> ranking;
  private List<String> customRanking;
  /** Deprecated: use {@link #replicas} */
  @Deprecated private List<String> slaves;

  private List<String> replicas;

  /* search */
  // nothing on Index Settings

  /* typos */
  private Integer minWordSizefor1Typo;
  private Integer minWordSizefor2Typos;
  private TypoTolerance typoTolerance;
  private Boolean allowTyposOnNumericTokens;
  private IgnorePlurals ignorePlurals;
  private List<String> disableTypoToleranceOnAttributes;
  private List<String> disableTypoToleranceOnWords;
  private String separatorsToIndex;

  /* custom */
  private Integer version;
  private Map<String, Object> customSettings = new HashMap<>();

  /** Deprecated: use {@link #getSearchableAttributes()} */
  @Deprecated
  public List<String> getAttributesToIndex() {
    return attributesToIndex;
  }

  /** Deprecated: use {@link #setSearchableAttributes(List)} */
  @Deprecated
  public IndexSettings setAttributesToIndex(List<String> attributesToIndex) {
    this.attributesToIndex = attributesToIndex;
    return this;
  }

  /** Deprecated: use {@link #getNumericAttributesForFiltering()} */
  @Deprecated
  public List<String> getNumericAttributesToIndex() {
    return numericAttributesToIndex;
  }

  /** Deprecated: use {@link #setNumericAttributesForFiltering(List)} */
  @Deprecated
  public IndexSettings setNumericAttributesToIndex(List<String> numericAttributesToIndex) {
    this.numericAttributesToIndex = numericAttributesToIndex;
    return this;
  }

  public List<String> getAttributesForFaceting() {
    return attributesForFaceting;
  }

  public IndexSettings setAttributesForFaceting(List<String> attributesForFaceting) {
    this.attributesForFaceting = attributesForFaceting;
    return this;
  }

  public String getAttributeForDistinct() {
    return attributeForDistinct;
  }

  public IndexSettings setAttributeForDistinct(String attributeForDistinct) {
    this.attributeForDistinct = attributeForDistinct;
    return this;
  }

  public List<String> getRanking() {
    return ranking;
  }

  public IndexSettings setRanking(List<String> ranking) {
    this.ranking = ranking;
    return this;
  }

  public List<String> getCustomRanking() {
    return customRanking;
  }

  public IndexSettings setCustomRanking(List<String> customRanking) {
    this.customRanking = customRanking;
    return this;
  }

  public String getSeparatorsToIndex() {
    return separatorsToIndex;
  }

  public IndexSettings setSeparatorsToIndex(String separatorsToIndex) {
    this.separatorsToIndex = separatorsToIndex;
    return this;
  }

  /** Deprecated: use {@link #getReplicas()} */
  @Deprecated
  public List<String> getSlaves() {
    return slaves;
  }

  /** Deprecated: use {@link #setReplicas(List)} */
  @Deprecated
  public IndexSettings setSlaves(List<String> slaves) {
    this.slaves = slaves;
    return this;
  }

  public List<String> getUnretrievableAttributes() {
    return unretrievableAttributes;
  }

  public IndexSettings setUnretrievableAttributes(List<String> unretrievableAttributes) {
    this.unretrievableAttributes = unretrievableAttributes;
    return this;
  }

  public Boolean getAllowCompressionOfIntegerArray() {
    return allowCompressionOfIntegerArray;
  }

  public IndexSettings setAllowCompressionOfIntegerArray(Boolean allowCompressionOfIntegerArray) {
    this.allowCompressionOfIntegerArray = allowCompressionOfIntegerArray;
    return this;
  }

  public List<String> getDisableTypoToleranceOnWords() {
    return disableTypoToleranceOnWords;
  }

  public IndexSettings setDisableTypoToleranceOnWords(List<String> disableTypoToleranceOnWords) {
    this.disableTypoToleranceOnWords = disableTypoToleranceOnWords;
    return this;
  }

  public List<String> getDisableTypoToleranceOnAttributes() {
    return disableTypoToleranceOnAttributes;
  }

  public IndexSettings setDisableTypoToleranceOnAttributes(
      List<String> disableTypoToleranceOnAttributes) {
    this.disableTypoToleranceOnAttributes = disableTypoToleranceOnAttributes;
    return this;
  }

  public Integer getMinWordSizefor1Typo() {
    return minWordSizefor1Typo;
  }

  public IndexSettings setMinWordSizefor1Typo(Integer minWordSizefor1Typo) {
    this.minWordSizefor1Typo = minWordSizefor1Typo;
    return this;
  }

  public Integer getMinWordSizefor2Typos() {
    return minWordSizefor2Typos;
  }

  public IndexSettings setMinWordSizefor2Typos(Integer minWordSizefor2Typos) {
    this.minWordSizefor2Typos = minWordSizefor2Typos;
    return this;
  }

  public Long getHitsPerPage() {
    return hitsPerPage;
  }

  public IndexSettings setHitsPerPage(Integer hitsPerPage) {
    return this.setHitsPerPage(hitsPerPage.longValue());
  }

  @JsonSetter
  public IndexSettings setHitsPerPage(Long hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  public List<String> getAttributesToRetrieve() {
    return attributesToRetrieve;
  }

  public IndexSettings setAttributesToRetrieve(List<String> attributesToRetrieve) {
    this.attributesToRetrieve = attributesToRetrieve;
    return this;
  }

  public List<String> getCamelCaseAttributes() {
    return camelCaseAttributes;
  }

  public IndexSettings setCamelCaseAttributes(List<String> camelCaseAttributes) {
    this.camelCaseAttributes = camelCaseAttributes;
    return this;
  }

  public Map<String, List<String>> getDecompoundedAttributes() {
    return decompoundedAttributes;
  }

  public IndexSettings setDecompoundedAttributes(Map<String, List<String>> decompoundedAttributes) {
    this.decompoundedAttributes = decompoundedAttributes;
    return this;
  }

  public List<String> getAttributesToHighlight() {
    return attributesToHighlight;
  }

  public IndexSettings setAttributesToHighlight(List<String> attributesToHighlight) {
    this.attributesToHighlight = attributesToHighlight;
    return this;
  }

  public List<String> getAttributesToSnippet() {
    return attributesToSnippet;
  }

  public IndexSettings setAttributesToSnippet(List<String> attributesToSnippet) {
    this.attributesToSnippet = attributesToSnippet;
    return this;
  }

  public String getQueryType() {
    return queryType;
  }

  public IndexSettings setQueryType(String queryType) {
    this.queryType = queryType;
    return this;
  }

  public Integer getMinProximity() {
    return minProximity;
  }

  public IndexSettings setMinProximity(Integer minProximity) {
    this.minProximity = minProximity;
    return this;
  }

  public String getHighlightPreTag() {
    return highlightPreTag;
  }

  public IndexSettings setHighlightPreTag(String highlightPreTag) {
    this.highlightPreTag = highlightPreTag;
    return this;
  }

  public String getHighlightPostTag() {
    return highlightPostTag;
  }

  public IndexSettings setHighlightPostTag(String highlightPostTag) {
    this.highlightPostTag = highlightPostTag;
    return this;
  }

  public List<String> getOptionalWords() {
    return optionalWords;
  }

  public IndexSettings setOptionalWords(List<String> optionalWords) {
    this.optionalWords = optionalWords;
    return this;
  }

  public Boolean getAllowTyposOnNumericTokens() {
    return allowTyposOnNumericTokens;
  }

  public IndexSettings setAllowTyposOnNumericTokens(Boolean allowTyposOnNumericTokens) {
    this.allowTyposOnNumericTokens = allowTyposOnNumericTokens;
    return this;
  }

  public IgnorePlurals getIgnorePlurals() {
    return ignorePlurals;
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

  @JsonIgnore
  public IndexSettings setIgnorePlurals(Boolean ignorePlurals) {
    return this.setIgnorePlurals(IgnorePlurals.of(ignorePlurals));
  }

  public Boolean getAdvancedSyntax() {
    return advancedSyntax;
  }

  public IndexSettings setAdvancedSyntax(Boolean advancedSyntax) {
    this.advancedSyntax = advancedSyntax;
    return this;
  }

  public Boolean getReplaceSynonymsInHighlight() {
    return replaceSynonymsInHighlight;
  }

  public IndexSettings setReplaceSynonymsInHighlight(Boolean replaceSynonymsInHighlight) {
    this.replaceSynonymsInHighlight = replaceSynonymsInHighlight;
    return this;
  }

  public Long getMaxValuesPerFacet() {
    return maxValuesPerFacet;
  }

  public IndexSettings setMaxValuesPerFacet(Integer maxValuesPerFacet) {
    return this.setMaxValuesPerFacet(maxValuesPerFacet.longValue());
  }

  @JsonSetter
  public IndexSettings setMaxValuesPerFacet(Long maxValuesPerFacet) {
    this.maxValuesPerFacet = maxValuesPerFacet;
    return this;
  }

  public Distinct getDistinct() {
    return distinct;
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

  @JsonIgnore
  public IndexSettings setDistinct(Integer distinct) {
    return this.setDistinct(Distinct.of(distinct));
  }

  public TypoTolerance getTypoTolerance() {
    return typoTolerance;
  }

  public IndexSettings setTypoTolerance(TypoTolerance typoTolerance) {
    this.typoTolerance = typoTolerance;
    return this;
  }

  public RemoveStopWords getRemoveStopWords() {
    return removeStopWords;
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

  @JsonIgnore
  public IndexSettings setRemoveStopWords(Boolean removeStopWords) {
    return this.setRemoveStopWords(RemoveStopWords.of(removeStopWords));
  }

  public String getSnippetEllipsisText() {
    return snippetEllipsisText;
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

  public Map<String, List<String>> getPlaceholders() {
    return placeholders;
  }

  public IndexSettings setPlaceholders(Map<String, List<String>> placeholders) {
    this.placeholders = placeholders;
    return this;
  }

  public List<String> getResponseFields() {
    return responseFields;
  }

  public IndexSettings setResponseFields(List<String> responseFields) {
    this.responseFields = responseFields;
    return this;
  }

  public Integer getMaxFacetHits() {
    return maxFacetHits;
  }

  public IndexSettings setMaxFacetHits(Integer maxFacetHits) {
    this.maxFacetHits = maxFacetHits;
    return this;
  }

  public Boolean getRestrictHighlightAndSnippetArrays() {
    return restrictHighlightAndSnippetArrays;
  }

  public IndexSettings setRestrictHighlightAndSnippetArrays(
      Boolean restrictHighlightAndSnippetArrays) {
    this.restrictHighlightAndSnippetArrays = restrictHighlightAndSnippetArrays;
    return this;
  }

  public Long getPaginationLimitedTo() {
    return paginationLimitedTo;
  }

  public IndexSettings setPaginationLimitedTo(Integer paginationLimitedTo) {
    return this.setPaginationLimitedTo(paginationLimitedTo.longValue());
  }

  @JsonSetter
  public IndexSettings setPaginationLimitedTo(Long paginationLimitedTo) {
    this.paginationLimitedTo = paginationLimitedTo;
    return this;
  }

  public List<String> getNumericAttributesForFiltering() {
    return numericAttributesForFiltering;
  }

  public IndexSettings setNumericAttributesForFiltering(
      List<String> numericAttributesForFiltering) {
    this.numericAttributesForFiltering = numericAttributesForFiltering;
    return this;
  }

  public List<String> getDisablePrefixOnAttributes() {
    return disablePrefixOnAttributes;
  }

  public IndexSettings setDisablePrefixOnAttributes(List<String> disablePrefixOnAttributes) {
    this.disablePrefixOnAttributes = disablePrefixOnAttributes;
    return this;
  }

  public List<String> getDisableExactOnAttributes() {
    return disableExactOnAttributes;
  }

  public IndexSettings setDisableExactOnAttributes(List<String> disableExactOnAttributes) {
    this.disableExactOnAttributes = disableExactOnAttributes;
    return this;
  }

  public String getExactOnSingleWordQuery() {
    return exactOnSingleWordQuery;
  }

  public IndexSettings setExactOnSingleWordQuery(String exactOnSingleWordQuery) {
    this.exactOnSingleWordQuery = exactOnSingleWordQuery;
    return this;
  }

  public List<String> getAlternativesAsExact() {
    return alternativesAsExact;
  }

  public IndexSettings setAlternativesAsExact(List<String> alternativesAsExact) {
    this.alternativesAsExact = alternativesAsExact;
    return this;
  }

  public Boolean getEnableRules() {
    return enableRules;
  }

  public IndexSettings setEnableRules(Boolean enableRules) {
    this.enableRules = enableRules;
    return this;
  }

  public String getSortFacetValuesBy() {
    return sortFacetValuesBy;
  }

  public IndexSettings setSortFacetValuesBy(String sortFacetValuesBy) {
    this.sortFacetValuesBy = sortFacetValuesBy;
    return this;
  }

  public String getKeepDiacriticsOnCharacters() {
    return keepDiacriticsOnCharacters;
  }

  public IndexSettings setKeepDiacriticsOnCharacters(String keepDiacriticsOnCharacters) {
    this.keepDiacriticsOnCharacters = keepDiacriticsOnCharacters;
    return this;
  }

  public List<String> getQueryLanguages() {
    return queryLanguages;
  }

  public IndexSettings setQueryLanguages(List<String> queryLanguages) {
    this.queryLanguages = queryLanguages;
    return this;
  }

  @JsonAnySetter
  public IndexSettings setCustomSetting(String key, Object value) {
    this.customSettings.put(key, value);
    return this;
  }

  @JsonAnyGetter
  public Map<String, Object> getCustomSettings() {
    return customSettings;
  }

  @JsonAnySetter
  public IndexSettings setCustomSettings(Map<String, Object> customSettings) {
    this.customSettings = customSettings;
    return this;
  }

  public IndexSettings setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public String toString() {
    return "IndexSettings{"
        + "attributeForDistinct='"
        + attributeForDistinct
        + '\''
        + ", distinct="
        + distinct
        + ", replaceSynonymsInHighlight="
        + replaceSynonymsInHighlight
        + ", placeholders="
        + placeholders
        + ", minProximity="
        + minProximity
        + ", responseFields="
        + responseFields
        + ", maxFacetHits="
        + maxFacetHits
        + ", attributesToIndex="
        + attributesToIndex
        + ", searchableAttributes="
        + searchableAttributes
        + ", attributesForFaceting="
        + attributesForFaceting
        + ", unretrievableAttributes="
        + unretrievableAttributes
        + ", attributesToRetrieve="
        + attributesToRetrieve
        + ", camelCaseAttributes="
        + camelCaseAttributes
        + ", decompoundedAttributes="
        + decompoundedAttributes
        + ", maxValuesPerFacet="
        + maxValuesPerFacet
        + ", sortFacetValuesBy="
        + sortFacetValuesBy
        + ", attributesToHighlight="
        + attributesToHighlight
        + ", attributesToSnippet="
        + attributesToSnippet
        + ", highlightPreTag='"
        + highlightPreTag
        + '\''
        + ", highlightPostTag='"
        + highlightPostTag
        + '\''
        + ", snippetEllipsisText='"
        + snippetEllipsisText
        + '\''
        + ", restrictHighlightAndSnippetArrays="
        + restrictHighlightAndSnippetArrays
        + ", hitsPerPage="
        + hitsPerPage
        + ", paginationLimitedTo="
        + paginationLimitedTo
        + ", numericAttributesToIndex="
        + numericAttributesToIndex
        + ", numericAttributesForFiltering="
        + numericAttributesForFiltering
        + ", allowCompressionOfIntegerArray="
        + allowCompressionOfIntegerArray
        + ", enableRules="
        + enableRules
        + ", queryType='"
        + queryType
        + '\''
        + ", removeWordsIfNoResults='"
        + removeWordsIfNoResults
        + '\''
        + ", advancedSyntax="
        + advancedSyntax
        + ", optionalWords="
        + optionalWords
        + ", removeStopWords="
        + removeStopWords
        + ", disablePrefixOnAttributes="
        + disablePrefixOnAttributes
        + ", disableExactOnAttributes="
        + disableExactOnAttributes
        + ", exactOnSingleWordQuery='"
        + exactOnSingleWordQuery
        + '\''
        + ", alternativesAsExact="
        + alternativesAsExact
        + ", ranking="
        + ranking
        + ", customRanking="
        + customRanking
        + ", slaves="
        + slaves
        + ", replicas="
        + replicas
        + ", minWordSizefor1Typo="
        + minWordSizefor1Typo
        + ", minWordSizefor2Typos="
        + minWordSizefor2Typos
        + ", typoTolerance="
        + typoTolerance
        + ", allowTyposOnNumericTokens="
        + allowTyposOnNumericTokens
        + ", ignorePlurals="
        + ignorePlurals
        + ", disableTypoToleranceOnAttributes="
        + disableTypoToleranceOnAttributes
        + ", disableTypoToleranceOnWords="
        + disableTypoToleranceOnWords
        + ", separatorsToIndex='"
        + separatorsToIndex
        + '\''
        + ", keepDiacriticsOnCharacters='"
        + keepDiacriticsOnCharacters
        + '\''
        + ", queryLanguages="
        + queryLanguages
        + ", customSettings="
        + customSettings
        + '}';
  }
}
