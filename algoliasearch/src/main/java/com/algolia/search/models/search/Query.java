package com.algolia.search.models.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Query implements Serializable {

  public Query() {}

  public Query(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

  public Query setQuery(String query) {
    this.query = query;
    return this;
  }

  public Boolean getGetRankingInfo() {
    return getRankingInfo;
  }

  public Query setGetRankingInfo(Boolean getRankingInfo) {
    this.getRankingInfo = getRankingInfo;
    return this;
  }

  public List<String> getNumericFilters() {
    return numericFilters;
  }

  public Query setNumericFilters(List<String> numericFilters) {
    this.numericFilters = numericFilters;
    return this;
  }

  public List<String> getTagFilters() {
    return tagFilters;
  }

  public Query setTagFilters(List<String> tagFilters) {
    this.tagFilters = tagFilters;
    return this;
  }

  public Boolean getClickAnalytics() {
    return clickAnalytics;
  }

  public Query setClickAnalytics(Boolean clickAnalytics) {
    this.clickAnalytics = clickAnalytics;
    return this;
  }

  public Boolean getAnalytics() {
    return analytics;
  }

  public Query setAnalytics(Boolean analytics) {
    this.analytics = analytics;
    return this;
  }

  public String getAnalyticsTags() {
    return analyticsTags;
  }

  public Query setAnalyticsTags(String analyticsTags) {
    this.analyticsTags = analyticsTags;
    return this;
  }

  public Boolean getSynonyms() {
    return synonyms;
  }

  public Query setSynonyms(Boolean synonyms) {
    this.synonyms = synonyms;
    return this;
  }

  public Boolean getReplaceSynonymsInHighlight() {
    return replaceSynonymsInHighlight;
  }

  public Query setReplaceSynonymsInHighlight(Boolean replaceSynonymsInHighlight) {
    this.replaceSynonymsInHighlight = replaceSynonymsInHighlight;
    return this;
  }

  public Integer getMinProximity() {
    return minProximity;
  }

  public Query setMinProximity(Integer minProximity) {
    this.minProximity = minProximity;
    return this;
  }

  public List<String> getResponseFields() {
    return responseFields;
  }

  public Query setResponseFields(List<String> responseFields) {
    this.responseFields = responseFields;
    return this;
  }

  public Long getMaxFacetHits() {
    return maxFacetHits;
  }

  public Query setMaxFacetHits(Long maxFacetHits) {
    this.maxFacetHits = maxFacetHits;
    return this;
  }

  public Boolean getPercentileComputation() {
    return percentileComputation;
  }

  public Query setPercentileComputation(Boolean percentileComputation) {
    this.percentileComputation = percentileComputation;
    return this;
  }

  public List<String> getQueryLanguages() {
    return queryLanguages;
  }

  public Query setQueryLanguages(List<String> queryLanguages) {
    this.queryLanguages = queryLanguages;
    return this;
  }

  public List<String> getAttributesToRetrieve() {
    return attributesToRetrieve;
  }

  public Query setAttributesToRetrieve(List<String> attributesToRetrieve) {
    this.attributesToRetrieve = attributesToRetrieve;
    return this;
  }

  public List<String> getRestrictSearchableAttributes() {
    return restrictSearchableAttributes;
  }

  public Query setRestrictSearchableAttributes(List<String> restrictSearchableAttributes) {
    this.restrictSearchableAttributes = restrictSearchableAttributes;
    return this;
  }

  public String getFilters() {
    return filters;
  }

  public Query setFilters(String filters) {
    this.filters = filters;
    return this;
  }

  public List<String> getFacets() {
    return facets;
  }

  public Query setFacets(List<String> facets) {
    this.facets = facets;
    return this;
  }

  public Long getMaxValuesPerFacet() {
    return maxValuesPerFacet;
  }

  public Query setMaxValuesPerFacet(Long maxValuesPerFacet) {
    this.maxValuesPerFacet = maxValuesPerFacet;
    return this;
  }

  public Boolean getFacetingAfterDistinct() {
    return facetingAfterDistinct;
  }

  public Query setFacetingAfterDistinct(Boolean facetingAfterDistinct) {
    this.facetingAfterDistinct = facetingAfterDistinct;
    return this;
  }

  public String getSortFacetValuesBy() {
    return sortFacetValuesBy;
  }

  public Query setSortFacetValuesBy(String sortFacetValuesBy) {
    this.sortFacetValuesBy = sortFacetValuesBy;
    return this;
  }

  public String getAroundLatLng() {
    return aroundLatLng;
  }

  public Query setAroundLatLng(String aroundLatLng) {
    this.aroundLatLng = aroundLatLng;
    return this;
  }

  public Boolean getAroundLatLngViaIP() {
    return aroundLatLngViaIP;
  }

  public Query setAroundLatLngViaIP(Boolean aroundLatLngViaIP) {
    this.aroundLatLngViaIP = aroundLatLngViaIP;
    return this;
  }

  public Integer getAroundPrecision() {
    return aroundPrecision;
  }

  public Query setAroundPrecision(Integer aroundPrecision) {
    this.aroundPrecision = aroundPrecision;
    return this;
  }

  public Integer getMinimumAroundRadius() {
    return minimumAroundRadius;
  }

  public Query setMinimumAroundRadius(Integer minimumAroundRadius) {
    this.minimumAroundRadius = minimumAroundRadius;
    return this;
  }

  public List<List<Float>> getInsideBoundingBox() {
    return insideBoundingBox;
  }

  public Query setInsideBoundingBox(List<List<Float>> insideBoundingBox) {
    this.insideBoundingBox = insideBoundingBox;
    return this;
  }

  public List<List<Float>> getInsidePolygon() {
    return insidePolygon;
  }

  public Query setInsidePolygon(List<List<Float>> insidePolygon) {
    this.insidePolygon = insidePolygon;
    return this;
  }

  public List<String> getAttributesToHighlight() {
    return attributesToHighlight;
  }

  public Query setAttributesToHighlight(List<String> attributesToHighlight) {
    this.attributesToHighlight = attributesToHighlight;
    return this;
  }

  public List<String> getAttributesToSnippet() {
    return attributesToSnippet;
  }

  public Query setAttributesToSnippet(List<String> attributesToSnippet) {
    this.attributesToSnippet = attributesToSnippet;
    return this;
  }

  public String getHighlightPreTag() {
    return highlightPreTag;
  }

  public Query setHighlightPreTag(String highlightPreTag) {
    this.highlightPreTag = highlightPreTag;
    return this;
  }

  public String getHighlightPostTag() {
    return highlightPostTag;
  }

  public Query setHighlightPostTag(String highlightPostTag) {
    this.highlightPostTag = highlightPostTag;
    return this;
  }

  public String getSnippetEllipsisText() {
    return snippetEllipsisText;
  }

  public Query setSnippetEllipsisText(String snippetEllipsisText) {
    this.snippetEllipsisText = snippetEllipsisText;
    return this;
  }

  public Boolean getRestrictHighlightAndSnippetArrays() {
    return restrictHighlightAndSnippetArrays;
  }

  public Query setRestrictHighlightAndSnippetArrays(Boolean restrictHighlightAndSnippetArrays) {
    this.restrictHighlightAndSnippetArrays = restrictHighlightAndSnippetArrays;
    return this;
  }

  public Integer getPage() {
    return page;
  }

  public Query setPage(Integer page) {
    this.page = page;
    return this;
  }

  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public Query setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  public Integer getOffset() {
    return offset;
  }

  public Query setOffset(Integer offset) {
    this.offset = offset;
    return this;
  }

  public Integer getLength() {
    return length;
  }

  public Query setLength(Integer length) {
    this.length = length;
    return this;
  }

  public Boolean getEnableRules() {
    return enableRules;
  }

  public Query setEnableRules(Boolean enableRules) {
    this.enableRules = enableRules;
    return this;
  }

  public List<String> getRuleContexts() {
    return ruleContexts;
  }

  public Query setRuleContexts(List<String> ruleContexts) {
    this.ruleContexts = ruleContexts;
    return this;
  }

  public String getQueryType() {
    return queryType;
  }

  public Query setQueryType(String queryType) {
    this.queryType = queryType;
    return this;
  }

  public Boolean getAdvancedSyntax() {
    return advancedSyntax;
  }

  public Query setAdvancedSyntax(Boolean advancedSyntax) {
    this.advancedSyntax = advancedSyntax;
    return this;
  }

  public List<String> getAdvancedSyntaxFeatures() {
    return advancedSyntaxFeatures;
  }

  public Query setAdvancedSyntaxFeatures(List<String> advancedSyntaxFeatures) {
    this.advancedSyntaxFeatures = advancedSyntaxFeatures;
    return this;
  }

  public List<String> getOptionalWords() {
    return optionalWords;
  }

  public Query setOptionalWords(List<String> optionalWords) {
    this.optionalWords = optionalWords;
    return this;
  }

  public List<String> getDisableExactOnAttributes() {
    return disableExactOnAttributes;
  }

  public Query setDisableExactOnAttributes(List<String> disableExactOnAttributes) {
    this.disableExactOnAttributes = disableExactOnAttributes;
    return this;
  }

  public String getExactOnSingleWordQuery() {
    return exactOnSingleWordQuery;
  }

  public Query setExactOnSingleWordQuery(String exactOnSingleWordQuery) {
    this.exactOnSingleWordQuery = exactOnSingleWordQuery;
    return this;
  }

  public List<String> getAlternativesAsExact() {
    return alternativesAsExact;
  }

  public Query setAlternativesAsExact(List<String> alternativesAsExact) {
    this.alternativesAsExact = alternativesAsExact;
    return this;
  }

  public Integer getMinWordSizefor1Typo() {
    return minWordSizefor1Typo;
  }

  public Query setMinWordSizefor1Typo(Integer minWordSizefor1Typo) {
    this.minWordSizefor1Typo = minWordSizefor1Typo;
    return this;
  }

  public Integer getMinWordSizefor2Typos() {
    return minWordSizefor2Typos;
  }

  public Query setMinWordSizefor2Typos(Integer minWordSizefor2Typos) {
    this.minWordSizefor2Typos = minWordSizefor2Typos;
    return this;
  }

  public Boolean getAllowTyposOnNumericTokens() {
    return allowTyposOnNumericTokens;
  }

  public Query setAllowTyposOnNumericTokens(Boolean allowTyposOnNumericTokens) {
    this.allowTyposOnNumericTokens = allowTyposOnNumericTokens;
    return this;
  }

  public List<String> getDisableTypoToleranceOnAttributes() {
    return disableTypoToleranceOnAttributes;
  }

  public Query setDisableTypoToleranceOnAttributes(List<String> disableTypoToleranceOnAttributes) {
    this.disableTypoToleranceOnAttributes = disableTypoToleranceOnAttributes;
    return this;
  }

  public String getUserToken() {
    return userToken;
  }

  public Query setUserToken(String userToken) {
    this.userToken = userToken;
    return this;
  }

  public Integer getValidUntil() {
    return validUntil;
  }

  public Query setValidUntil(Integer validUntil) {
    this.validUntil = validUntil;
    return this;
  }

  public List<String> getRestrictIndices() {
    return restrictIndices;
  }

  public Query setRestrictIndices(List<String> restrictIndices) {
    this.restrictIndices = restrictIndices;
    return this;
  }

  public String getRestrictSources() {
    return restrictSources;
  }

  public Query setRestrictSources(String restrictSources) {
    this.restrictSources = restrictSources;
    return this;
  }

  public Boolean getEnablePersonalization() {
    return enablePersonalization;
  }

  public Query setEnablePersonalization(Boolean enablePersonalization) {
    this.enablePersonalization = enablePersonalization;
    return this;
  }

  public List<List<String>> getFacetFilters() {
    return facetFilters;
  }

  public Query setFacetFilters(List<List<String>> facetFilters) {
    this.facetFilters = facetFilters;
    return this;
  }

  /* search */
  @JsonProperty("query")
  private String query;

  /* advanced */
  // protected Distinct distinct;
  private Boolean getRankingInfo;
  private List<String> numericFilters;
  private List<String> tagFilters;
  private Boolean clickAnalytics;
  private Boolean analytics;
  private String analyticsTags;
  private Boolean synonyms;
  private Boolean replaceSynonymsInHighlight;
  private Integer minProximity;
  private List<String> responseFields;
  private Long maxFacetHits;
  private Boolean percentileComputation;
  private List<String> queryLanguages;

  /* attributes */
  private List<String> attributesToRetrieve;
  private List<String> restrictSearchableAttributes;

  /* filtering-faceting */
  private String filters;
  private List<String> facets;
  private Long maxValuesPerFacet;
  // protected FacetFilters facetFilters;
  private List<List<String>> facetFilters;
  private Boolean facetingAfterDistinct;
  private String sortFacetValuesBy;

  /* geo-search */
  private String aroundLatLng;
  private Boolean aroundLatLngViaIP;
  // protected AroundRadius aroundRadius;
  private Integer aroundPrecision;
  private Integer minimumAroundRadius;
  private List<List<Float>> insideBoundingBox;
  private List<List<Float>> insidePolygon;

  /* highlighting-snippeting */
  private List<String> attributesToHighlight;
  private List<String> attributesToSnippet;
  private String highlightPreTag;
  private String highlightPostTag;
  private String snippetEllipsisText;
  private Boolean restrictHighlightAndSnippetArrays;

  /* pagination */
  private Integer page;
  private Integer hitsPerPage;
  private Integer offset;
  private Integer length;

  /* performance */
  // Nothing in Query

  /* query rules */
  private Boolean enableRules;
  private List<String> ruleContexts;

  /* query strategy */
  private String queryType;
  // protected RemoveWordsType removeWordsIfNoResults;
  private Boolean advancedSyntax;
  private List<String> advancedSyntaxFeatures;
  private List<String> optionalWords;
  // protected RemoveStopWords removeStopWords;
  private List<String> disableExactOnAttributes;
  private String exactOnSingleWordQuery;
  private List<String> alternativesAsExact;

  /* ranking */
  // Nothing in Query

  /* typos */
  private Integer minWordSizefor1Typo;
  private Integer minWordSizefor2Typos;
  // protected TypoTolerance typoTolerance;
  private Boolean allowTyposOnNumericTokens;
  // protected IgnorePlurals ignorePlurals;
  private List<String> disableTypoToleranceOnAttributes;

  /* SECURED API KEYS */
  private String userToken;
  private Integer validUntil;
  private List<String> restrictIndices;
  private String restrictSources;

  /* Personalization */
  private Boolean enablePersonalization;
}
