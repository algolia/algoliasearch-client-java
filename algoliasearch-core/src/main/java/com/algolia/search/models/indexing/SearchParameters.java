package com.algolia.search.models.indexing;

import com.algolia.search.models.settings.Distinct;
import com.algolia.search.models.settings.IgnorePlurals;
import com.algolia.search.models.settings.RemoveStopWords;
import com.algolia.search.models.settings.TypoTolerance;
import com.algolia.search.util.QueryStringUtils;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SearchParameters<T extends SearchParameters<T>> implements Serializable {

  /** To prevent unchecked cast warning. */
  @JsonIgnore
  public abstract T getThis();

  public String getQuery() {
    return query;
  }

  public T setQuery(String query) {
    this.query = query;
    return getThis();
  }

  public String getSimilarQuery() {
    return similarQuery;
  }

  public T setSimilarQuery(String similarQuery) {
    this.similarQuery = similarQuery;
    return getThis();
  }

  public Boolean getGetRankingInfo() {
    return getRankingInfo;
  }

  public T setGetRankingInfo(Boolean getRankingInfo) {
    this.getRankingInfo = getRankingInfo;
    return getThis();
  }

  public List<String> getExplain() {
    return explain;
  }

  public T setExplain(List<String> explain) {
    this.explain = explain;
    return getThis();
  }

  public List<String> getNaturalLanguages() {
    return naturalLanguages;
  }

  public T setNaturalLanguages(List<String> naturalLanguages) {
    this.naturalLanguages = naturalLanguages;
    return getThis();
  }

  public List<List<String>> getNumericFilters() {
    return numericFilters;
  }

  public T setNumericFilters(List<List<String>> numericFilters) {
    this.numericFilters = numericFilters;
    return getThis();
  }

  public List<List<String>> getTagFilters() {
    return tagFilters;
  }

  public T setTagFilters(List<List<String>> tagFilters) {
    this.tagFilters = tagFilters;
    return getThis();
  }

  public Boolean getClickAnalytics() {
    return clickAnalytics;
  }

  public T setClickAnalytics(Boolean clickAnalytics) {
    this.clickAnalytics = clickAnalytics;
    return getThis();
  }

  public Boolean getAnalytics() {
    return analytics;
  }

  public T setAnalytics(Boolean analytics) {
    this.analytics = analytics;
    return getThis();
  }

  public List<String> getAnalyticsTags() {
    return analyticsTags;
  }

  public T setAnalyticsTags(List<String> analyticsTags) {
    this.analyticsTags = analyticsTags;
    return getThis();
  }

  public Boolean getSynonyms() {
    return synonyms;
  }

  public T setSynonyms(Boolean synonyms) {
    this.synonyms = synonyms;
    return getThis();
  }

  public Boolean getReplaceSynonymsInHighlight() {
    return replaceSynonymsInHighlight;
  }

  public T setReplaceSynonymsInHighlight(Boolean replaceSynonymsInHighlight) {
    this.replaceSynonymsInHighlight = replaceSynonymsInHighlight;
    return getThis();
  }

  public Integer getMinProximity() {
    return minProximity;
  }

  public T setMinProximity(Integer minProximity) {
    this.minProximity = minProximity;
    return getThis();
  }

  public List<String> getResponseFields() {
    return responseFields;
  }

  public T setResponseFields(List<String> responseFields) {
    this.responseFields = responseFields;
    return getThis();
  }

  public Long getMaxFacetHits() {
    return maxFacetHits;
  }

  public T setMaxFacetHits(Long maxFacetHits) {
    this.maxFacetHits = maxFacetHits;
    return getThis();
  }

  public Boolean getPercentileComputation() {
    return percentileComputation;
  }

  public T setPercentileComputation(Boolean percentileComputation) {
    this.percentileComputation = percentileComputation;
    return getThis();
  }

  public List<String> getQueryLanguages() {
    return queryLanguages;
  }

  public T setQueryLanguages(List<String> queryLanguages) {
    this.queryLanguages = queryLanguages;
    return getThis();
  }

  public List<String> getAttributesToRetrieve() {
    return attributesToRetrieve;
  }

  public T setAttributesToRetrieve(List<String> attributesToRetrieve) {
    this.attributesToRetrieve = attributesToRetrieve;
    return getThis();
  }

  public List<String> getRestrictSearchableAttributes() {
    return restrictSearchableAttributes;
  }

  public T setRestrictSearchableAttributes(List<String> restrictSearchableAttributes) {
    this.restrictSearchableAttributes = restrictSearchableAttributes;
    return getThis();
  }

  public String getFilters() {
    return filters;
  }

  public T setFilters(String filters) {
    this.filters = filters;
    return getThis();
  }

  public List<String> getFacets() {
    return facets;
  }

  public T setFacets(List<String> facets) {
    this.facets = facets;
    return getThis();
  }

  public Long getMaxValuesPerFacet() {
    return maxValuesPerFacet;
  }

  public T setMaxValuesPerFacet(Long maxValuesPerFacet) {
    this.maxValuesPerFacet = maxValuesPerFacet;
    return getThis();
  }

  public Boolean getFacetingAfterDistinct() {
    return facetingAfterDistinct;
  }

  public T setFacetingAfterDistinct(Boolean facetingAfterDistinct) {
    this.facetingAfterDistinct = facetingAfterDistinct;
    return getThis();
  }

  public String getSortFacetValuesBy() {
    return sortFacetValuesBy;
  }

  public T setSortFacetValuesBy(String sortFacetValuesBy) {
    this.sortFacetValuesBy = sortFacetValuesBy;
    return getThis();
  }

  public String getAroundLatLng() {
    return aroundLatLng;
  }

  public T setAroundLatLng(String aroundLatLng) {
    this.aroundLatLng = aroundLatLng;
    return getThis();
  }

  public Boolean getAroundLatLngViaIP() {
    return aroundLatLngViaIP;
  }

  public T setAroundLatLngViaIP(Boolean aroundLatLngViaIP) {
    this.aroundLatLngViaIP = aroundLatLngViaIP;
    return getThis();
  }

  public List<AroundPrecision> getAroundPrecision() {
    return aroundPrecision;
  }

  public T setAroundPrecision(List<AroundPrecision> aroundPrecision) {
    this.aroundPrecision = aroundPrecision;
    return getThis();
  }

  public Integer getMinimumAroundRadius() {
    return minimumAroundRadius;
  }

  public T setMinimumAroundRadius(Integer minimumAroundRadius) {
    this.minimumAroundRadius = minimumAroundRadius;
    return getThis();
  }

  public List<List<Float>> getInsideBoundingBox() {
    return insideBoundingBox;
  }

  public T setInsideBoundingBox(List<List<Float>> insideBoundingBox) {
    this.insideBoundingBox = insideBoundingBox;
    return getThis();
  }

  public List<List<Float>> getInsidePolygon() {
    return insidePolygon;
  }

  public T setInsidePolygon(List<List<Float>> insidePolygon) {
    this.insidePolygon = insidePolygon;
    return getThis();
  }

  public List<String> getAttributesToHighlight() {
    return attributesToHighlight;
  }

  public T setAttributesToHighlight(List<String> attributesToHighlight) {
    this.attributesToHighlight = attributesToHighlight;
    return getThis();
  }

  public List<String> getAttributesToSnippet() {
    return attributesToSnippet;
  }

  public T setAttributesToSnippet(List<String> attributesToSnippet) {
    this.attributesToSnippet = attributesToSnippet;
    return getThis();
  }

  public String getHighlightPreTag() {
    return highlightPreTag;
  }

  public T setHighlightPreTag(String highlightPreTag) {
    this.highlightPreTag = highlightPreTag;
    return getThis();
  }

  public String getHighlightPostTag() {
    return highlightPostTag;
  }

  public T setHighlightPostTag(String highlightPostTag) {
    this.highlightPostTag = highlightPostTag;
    return getThis();
  }

  public String getSnippetEllipsisText() {
    return snippetEllipsisText;
  }

  public T setSnippetEllipsisText(String snippetEllipsisText) {
    this.snippetEllipsisText = snippetEllipsisText;
    return getThis();
  }

  public Boolean getRestrictHighlightAndSnippetArrays() {
    return restrictHighlightAndSnippetArrays;
  }

  public T setRestrictHighlightAndSnippetArrays(Boolean restrictHighlightAndSnippetArrays) {
    this.restrictHighlightAndSnippetArrays = restrictHighlightAndSnippetArrays;
    return getThis();
  }

  public Integer getPage() {
    return page;
  }

  public T setPage(Integer page) {
    this.page = page;
    return getThis();
  }

  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public T setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return getThis();
  }

  public Integer getOffset() {
    return offset;
  }

  public T setOffset(Integer offset) {
    this.offset = offset;
    return getThis();
  }

  public Integer getLength() {
    return length;
  }

  public T setLength(Integer length) {
    this.length = length;
    return getThis();
  }

  public Boolean getEnableRules() {
    return enableRules;
  }

  public T setEnableRules(Boolean enableRules) {
    this.enableRules = enableRules;
    return getThis();
  }

  public List<String> getRuleContexts() {
    return ruleContexts;
  }

  public T setRuleContexts(List<String> ruleContexts) {
    this.ruleContexts = ruleContexts;
    return getThis();
  }

  public String getQueryType() {
    return queryType;
  }

  public T setQueryType(String queryType) {
    this.queryType = queryType;
    return getThis();
  }

  public Boolean getAdvancedSyntax() {
    return advancedSyntax;
  }

  public T setAdvancedSyntax(Boolean advancedSyntax) {
    this.advancedSyntax = advancedSyntax;
    return getThis();
  }

  public List<String> getAdvancedSyntaxFeatures() {
    return advancedSyntaxFeatures;
  }

  public T setAdvancedSyntaxFeatures(List<String> advancedSyntaxFeatures) {
    this.advancedSyntaxFeatures = advancedSyntaxFeatures;
    return getThis();
  }

  public List<String> getOptionalWords() {
    return optionalWords;
  }

  public T setOptionalWords(List<String> optionalWords) {
    this.optionalWords = optionalWords;
    return getThis();
  }

  public List<String> getDisableExactOnAttributes() {
    return disableExactOnAttributes;
  }

  public T setDisableExactOnAttributes(List<String> disableExactOnAttributes) {
    this.disableExactOnAttributes = disableExactOnAttributes;
    return getThis();
  }

  public String getExactOnSingleWordQuery() {
    return exactOnSingleWordQuery;
  }

  public T setExactOnSingleWordQuery(String exactOnSingleWordQuery) {
    this.exactOnSingleWordQuery = exactOnSingleWordQuery;
    return getThis();
  }

  public List<String> getAlternativesAsExact() {
    return alternativesAsExact;
  }

  public T setAlternativesAsExact(List<String> alternativesAsExact) {
    this.alternativesAsExact = alternativesAsExact;
    return getThis();
  }

  public Integer getMinWordSizefor1Typo() {
    return minWordSizefor1Typo;
  }

  public T setMinWordSizefor1Typo(Integer minWordSizefor1Typo) {
    this.minWordSizefor1Typo = minWordSizefor1Typo;
    return getThis();
  }

  public Integer getMinWordSizefor2Typos() {
    return minWordSizefor2Typos;
  }

  public T setMinWordSizefor2Typos(Integer minWordSizefor2Typos) {
    this.minWordSizefor2Typos = minWordSizefor2Typos;
    return getThis();
  }

  public Boolean getAllowTyposOnNumericTokens() {
    return allowTyposOnNumericTokens;
  }

  public T setAllowTyposOnNumericTokens(Boolean allowTyposOnNumericTokens) {
    this.allowTyposOnNumericTokens = allowTyposOnNumericTokens;
    return getThis();
  }

  public List<String> getDisableTypoToleranceOnAttributes() {
    return disableTypoToleranceOnAttributes;
  }

  public T setDisableTypoToleranceOnAttributes(List<String> disableTypoToleranceOnAttributes) {
    this.disableTypoToleranceOnAttributes = disableTypoToleranceOnAttributes;
    return getThis();
  }

  public String getUserToken() {
    return userToken;
  }

  public T setUserToken(String userToken) {
    this.userToken = userToken;
    return getThis();
  }

  public Integer getValidUntil() {
    return validUntil;
  }

  public T setValidUntil(Integer validUntil) {
    this.validUntil = validUntil;
    return getThis();
  }

  public List<String> getRestrictIndices() {
    return restrictIndices;
  }

  public T setRestrictIndices(List<String> restrictIndices) {
    this.restrictIndices = restrictIndices;
    return getThis();
  }

  public String getRestrictSources() {
    return restrictSources;
  }

  public T setRestrictSources(String restrictSources) {
    this.restrictSources = restrictSources;
    return getThis();
  }

  public Boolean getEnablePersonalization() {
    return enablePersonalization;
  }

  public T setEnablePersonalization(Boolean enablePersonalization) {
    this.enablePersonalization = enablePersonalization;
    return getThis();
  }

  public Integer getPersonalizationImpact() {
    return personalizationImpact;
  }

  public T setPersonalizationImpact(Integer personalizationImpact) {
    this.personalizationImpact = personalizationImpact;
    return getThis();
  }

  public List<List<String>> getFacetFilters() {
    return facetFilters;
  }

  public T setFacetFilters(List<List<String>> facetFilters) {
    this.facetFilters = facetFilters;
    return getThis();
  }

  public Distinct getDistinct() {
    return distinct;
  }

  public T setDistinct(Distinct distinct) {
    this.distinct = distinct;
    return getThis();
  }

  public AroundRadius getAroundRadius() {
    return aroundRadius;
  }

  public T setAroundRadius(AroundRadius aroundRadius) {
    this.aroundRadius = aroundRadius;
    return getThis();
  }

  public RemoveWordsType getRemoveWordsIfNoResults() {
    return removeWordsIfNoResults;
  }

  public T setRemoveWordsIfNoResults(RemoveWordsType removeWordsIfNoResults) {
    this.removeWordsIfNoResults = removeWordsIfNoResults;
    return getThis();
  }

  public RemoveStopWords getRemoveStopWords() {
    return removeStopWords;
  }

  public T setRemoveStopWords(RemoveStopWords removeStopWords) {
    this.removeStopWords = removeStopWords;
    return getThis();
  }

  public TypoTolerance getTypoTolerance() {
    return typoTolerance;
  }

  public T setTypoTolerance(TypoTolerance typoTolerance) {
    this.typoTolerance = typoTolerance;
    return getThis();
  }

  public IgnorePlurals getIgnorePlurals() {
    return ignorePlurals;
  }

  public T setIgnorePlurals(IgnorePlurals ignorePlurals) {
    this.ignorePlurals = ignorePlurals;
    return getThis();
  }

  public List<List<String>> getOptionalFilters() {
    return optionalFilters;
  }

  public T setOptionalFilters(List<List<String>> optionalFilters) {
    this.optionalFilters = optionalFilters;
    return getThis();
  }

  public Boolean getSumOrFiltersScores() {
    return sumOrFiltersScores;
  }

  public T setSumOrFiltersScores(Boolean sumOrFiltersScores) {
    this.sumOrFiltersScores = sumOrFiltersScores;
    return getThis();
  }

  public Boolean getEnableABTest() {
    return enableABTest;
  }

  public T setEnableABTest(Boolean enableABTest) {
    this.enableABTest = enableABTest;
    return getThis();
  }

  public Boolean getDecompoundQuery() {
    return decompoundQuery;
  }

  public T setDecompoundQuery(Boolean decompoundQuery) {
    this.decompoundQuery = decompoundQuery;
    return getThis();
  }

  @JsonAnyGetter
  public Map<String, Object> getCustomParameters() {
    return customParameters;
  }

  @JsonAnySetter
  public T setCustomParameters(Map<String, Object> customSettings) {
    this.customParameters = customSettings;
    return getThis();
  }

  @JsonAnySetter
  public T setCustomParameter(String key, Object value) {
    this.customParameters.put(key, value);
    return getThis();
  }

  public Integer getRelevancyStrictness() {
    return relevancyStrictness;
  }

  public T setRelevancyStrictness(Integer relevancyStrictness) {
    this.relevancyStrictness = relevancyStrictness;
    return getThis();
  }

  public String toParam() {
    return QueryStringUtils.buildQueryAsQueryParams(this);
  }

  /* search */
  @JsonProperty("query")
  protected String query;

  protected String similarQuery;

  /* advanced */
  protected Distinct distinct;
  protected Boolean getRankingInfo;
  protected List<String> explain;
  protected List<String> naturalLanguages;

  @JsonDeserialize(using = FiltersJsonDeserializer.class)
  protected List<List<String>> numericFilters;

  @JsonDeserialize(using = FiltersJsonDeserializer.class)
  protected List<List<String>> tagFilters;

  protected Boolean clickAnalytics;
  protected Boolean analytics;
  protected List<String> analyticsTags;
  protected Boolean synonyms;
  protected Boolean replaceSynonymsInHighlight;
  protected Integer minProximity;
  protected List<String> responseFields;
  protected Long maxFacetHits;
  protected Boolean percentileComputation;
  protected List<String> queryLanguages;
  protected Boolean decompoundQuery;

  /* attributes */
  protected List<String> attributesToRetrieve;
  protected List<String> restrictSearchableAttributes;

  /* filtering-faceting */
  protected String filters;
  protected List<String> facets;
  protected Long maxValuesPerFacet;

  @JsonDeserialize(using = FiltersJsonDeserializer.class)
  protected List<List<String>> facetFilters;

  @JsonDeserialize(using = FiltersJsonDeserializer.class)
  protected List<List<String>> optionalFilters;

  protected Boolean facetingAfterDistinct;
  protected String sortFacetValuesBy;

  /* geo-search */
  protected String aroundLatLng;
  protected Boolean aroundLatLngViaIP;
  protected AroundRadius aroundRadius;
  protected List<AroundPrecision> aroundPrecision;
  protected Integer minimumAroundRadius;
  protected List<List<Float>> insideBoundingBox;
  protected List<List<Float>> insidePolygon;

  /* highlighting-snippeting */
  protected List<String> attributesToHighlight;
  protected List<String> attributesToSnippet;
  protected String highlightPreTag;
  protected String highlightPostTag;
  protected String snippetEllipsisText;
  protected Boolean restrictHighlightAndSnippetArrays;

  /* pagination */
  protected Integer page;
  protected Integer hitsPerPage;
  protected Integer offset;
  protected Integer length;

  /* performance */
  // Nothing in Query

  /* query rules */
  protected Boolean enableRules;
  protected List<String> ruleContexts;

  /* query strategy */
  protected String queryType;
  protected RemoveWordsType removeWordsIfNoResults;
  protected Boolean advancedSyntax;
  protected List<String> advancedSyntaxFeatures;
  protected List<String> optionalWords;
  protected RemoveStopWords removeStopWords;
  protected List<String> disableExactOnAttributes;
  protected String exactOnSingleWordQuery;
  protected List<String> alternativesAsExact;
  protected Boolean sumOrFiltersScores;

  /* ranking */
  // Nothing in Query

  /* typos */
  protected Integer minWordSizefor1Typo;
  protected Integer minWordSizefor2Typos;
  protected TypoTolerance typoTolerance;
  protected Boolean allowTyposOnNumericTokens;
  protected IgnorePlurals ignorePlurals;
  protected List<String> disableTypoToleranceOnAttributes;

  /* SECURED API KEYS */
  protected String userToken;
  protected Integer validUntil;
  protected List<String> restrictIndices;
  protected String restrictSources;

  /* Personalization */
  protected Boolean enablePersonalization;
  protected Integer personalizationImpact;

  /* Analytics */
  protected Boolean enableABTest;

  /* Custom Parameters */
  protected Map<String, Object> customParameters = new HashMap<>();

  /* Virtual Indices */
  protected Integer relevancyStrictness;
}
