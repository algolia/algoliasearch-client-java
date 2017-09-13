package com.algolia.search.inputs.query_rules;

import com.algolia.search.objects.Distinct;
import com.algolia.search.objects.IgnorePlurals;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RemoveStopWords;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsequenceParams extends Query {
  private ConsequenceQuery query;
  private List<String> automaticFacetFilters;
  private List<String> automaticOptionalFacetFilters;

  public ConsequenceParams() {
  }

  public ConsequenceQuery getQuery() {
    return query;
  }

  public ConsequenceParams setQuery(ConsequenceQuery query) {
    this.query = query;
    return this;
  }

  public ConsequenceParams setQuery(String query) {
    return this.setQuery(new ConsequenceQueryString(query));
  }

  public List<String> getAutomaticFacetFilters() {
    return automaticFacetFilters;
  }

  public ConsequenceParams setAutomaticFacetFilters(List<String> automaticFacetFilters) {
    this.automaticFacetFilters = automaticFacetFilters;
    return this;
  }

  public List<String> getAutomaticOptionalFacetFilters() {
    return automaticOptionalFacetFilters;
  }

  //Fields from Query

  public ConsequenceParams setAutomaticOptionalFacetFilters(List<String> automaticOptionalFacetFilters) {
    this.automaticOptionalFacetFilters = automaticOptionalFacetFilters;
    return this;
  }

  public ConsequenceParams setAroundRadius(Integer aroundRadius) {
    return (ConsequenceParams) super.setAroundRadius(aroundRadius);
  }


  public ConsequenceParams setAroundRadiusAll() {
    return (ConsequenceParams) super.setAroundRadiusAll();
  }

  public Distinct getDistinct() {
    return distinct;
  }

  public ConsequenceParams setDistinct(Distinct distinct) {
    return (ConsequenceParams) super.setDistinct(distinct);
  }

  public Boolean getGetRankingInfo() {
    return getRankingInfo;
  }

  public ConsequenceParams setGetRankingInfo(Boolean getRankingInfo) {
    return (ConsequenceParams) super.setGetRankingInfo(getRankingInfo);
  }

  public List<String> getNumericFilters() {
    return numericFilters;
  }

  public ConsequenceParams setNumericFilters(List<String> numericFilters) {
    return (ConsequenceParams) super.setNumericFilters(numericFilters);
  }

  public List<String> getTagFilters() {
    return tagFilters;
  }

  public ConsequenceParams setTagFilters(List<String> tagFilters) {
    return (ConsequenceParams) super.setTagFilters(tagFilters);
  }

  public Boolean getAnalytics() {
    return analytics;
  }

  public ConsequenceParams setAnalytics(Boolean analytics) {
    return (ConsequenceParams) super.setAnalytics(analytics);
  }

  public String getAnalyticsTags() {
    return analyticsTags;
  }

  public ConsequenceParams setAnalyticsTags(String analyticsTags) {
    return (ConsequenceParams) super.setAnalyticsTags(analyticsTags);
  }

  public Boolean getSynonyms() {
    return synonyms;
  }

  public ConsequenceParams setSynonyms(Boolean synonyms) {
    return (ConsequenceParams) super.setSynonyms(synonyms);
  }

  public Boolean getReplaceSynonymsInHighlight() {
    return replaceSynonymsInHighlight;
  }

  public ConsequenceParams setReplaceSynonymsInHighlight(Boolean replaceSynonymsInHighlight) {
    return (ConsequenceParams) super.setReplaceSynonymsInHighlight(replaceSynonymsInHighlight);
  }

  public Integer getMinProximity() {
    return minProximity;
  }

  public ConsequenceParams setMinProximity(Integer minProximity) {
    return (ConsequenceParams) super.setMinProximity(minProximity);
  }

  public List<String> getResponseFields() {
    return responseFields;
  }

  public ConsequenceParams setResponseFields(List<String> responseFields) {
    return (ConsequenceParams) super.setResponseFields(responseFields);
  }

  public Integer getMaxFacetHits() {
    return maxFacetHits;
  }

  public ConsequenceParams setMaxFacetHits(Integer maxFacetHits) {
    return (ConsequenceParams) super.setMaxFacetHits(maxFacetHits);
  }

  public Boolean getPercentileComputation() {
    return percentileComputation;
  }

  public ConsequenceParams setPercentileComputation(Boolean percentileComputation) {
    return (ConsequenceParams) super.setPercentileComputation(percentileComputation);
  }

  public List<String> getAttributesToRetrieve() {
    return attributesToRetrieve;
  }

  public ConsequenceParams setAttributesToRetrieve(List<String> attributesToRetrieve) {
    return (ConsequenceParams) super.setAttributesToRetrieve(attributesToRetrieve);
  }

  public List<String> getRestrictSearchableAttributes() {
    return restrictSearchableAttributes;
  }

  public ConsequenceParams setRestrictSearchableAttributes(List<String> restrictSearchableAttributes) {
    return (ConsequenceParams) super.setRestrictSearchableAttributes(restrictSearchableAttributes);
  }

  public String getFilters() {
    return filters;
  }

  public ConsequenceParams setFilters(String filters) {
    return (ConsequenceParams) super.setFilters(filters);
  }

  public String getFacets() {
    return facets;
  }

  public ConsequenceParams setFacets(String facets) {
    return (ConsequenceParams) super.setFacets(facets);
  }

  public Integer getMaxValuesPerFacet() {
    return maxValuesPerFacet;
  }

  public ConsequenceParams setMaxValuesPerFacet(Integer maxValuesPerFacet) {
    return (ConsequenceParams) super.setMaxValuesPerFacet(maxValuesPerFacet);
  }

  public List<String> getFacetFilters() {
    return facetFilters;
  }

  public ConsequenceParams setFacetFilters(List<String> facetFilters) {
    return (ConsequenceParams) super.setFacetFilters(facetFilters);
  }

  public Boolean getFacetingAfterDistinct() {
    return facetingAfterDistinct;
  }

  public ConsequenceParams setFacetingAfterDistinct(Boolean facetingAfterDistinct) {
    return (ConsequenceParams) super.setFacetingAfterDistinct(facetingAfterDistinct);
  }

  public String getAroundLatLng() {
    return aroundLatLng;
  }

  public ConsequenceParams setAroundLatLng(String aroundLatLng) {
    return (ConsequenceParams) super.setAroundLatLng(aroundLatLng);
  }

  public Boolean getAroundLatLngViaIP() {
    return aroundLatLngViaIP;
  }

  public ConsequenceParams setAroundLatLngViaIP(Boolean aroundLatLngViaIP) {
    return (ConsequenceParams) super.setAroundLatLngViaIP(aroundLatLngViaIP);
  }

  public Object getAroundRadius() {
    return aroundRadius;
  }

  public Integer getAroundPrecision() {
    return aroundPrecision;
  }

  public ConsequenceParams setAroundPrecision(Integer aroundPrecision) {
    return (ConsequenceParams) super.setAroundPrecision(aroundPrecision);
  }

  public Integer getMinimumAroundRadius() {
    return minimumAroundRadius;
  }

  public ConsequenceParams setMinimumAroundRadius(Integer minimumAroundRadius) {
    return (ConsequenceParams) super.setMinimumAroundRadius(minimumAroundRadius);
  }

  public List<String> getInsideBoundingBox() {
    return insideBoundingBox;
  }

  public ConsequenceParams setInsideBoundingBox(List<String> insideBoundingBox) {
    return (ConsequenceParams) super.setInsideBoundingBox(insideBoundingBox);
  }

  public List<String> getInsidePolygon() {
    return insidePolygon;
  }

  public ConsequenceParams setInsidePolygon(List<String> insidePolygon) {
    return (ConsequenceParams) super.setInsidePolygon(insidePolygon);
  }

  public List<String> getAttributesToHighlight() {
    return attributesToHighlight;
  }

  public ConsequenceParams setAttributesToHighlight(List<String> attributesToHighlight) {
    return (ConsequenceParams) super.setAttributesToHighlight(attributesToHighlight);
  }

  public List<String> getAttributesToSnippet() {
    return attributesToSnippet;
  }

  public ConsequenceParams setAttributesToSnippet(List<String> attributesToSnippet) {
    return (ConsequenceParams) super.setAttributesToSnippet(attributesToSnippet);
  }

  public String getHighlightPreTag() {
    return highlightPreTag;
  }

  public ConsequenceParams setHighlightPreTag(String highlightPreTag) {
    return (ConsequenceParams) super.setHighlightPreTag(highlightPreTag);
  }

  public String getHighlightPostTag() {
    return highlightPostTag;
  }

  public ConsequenceParams setHighlightPostTag(String highlightPostTag) {
    return (ConsequenceParams) super.setHighlightPostTag(highlightPostTag);
  }

  public String getSnippetEllipsisText() {
    return snippetEllipsisText;
  }

  public ConsequenceParams setSnippetEllipsisText(String snippetEllipsisText) {
    return (ConsequenceParams) super.setSnippetEllipsisText(snippetEllipsisText);
  }

  public Boolean getRestrictHighlightAndSnippetArrays() {
    return restrictHighlightAndSnippetArrays;
  }

  public ConsequenceParams setRestrictHighlightAndSnippetArrays(Boolean restrictHighlightAndSnippetArrays) {
    return (ConsequenceParams) super.setRestrictHighlightAndSnippetArrays(restrictHighlightAndSnippetArrays);
  }

  public Integer getPage() {
    return page;
  }

  public ConsequenceParams setPage(Integer page) {
    return (ConsequenceParams) super.setPage(page);
  }

  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public ConsequenceParams setHitsPerPage(Integer hitsPerPage) {
    return (ConsequenceParams) super.setHitsPerPage(hitsPerPage);
  }

  public Integer getOffset() {
    return offset;
  }

  public ConsequenceParams setOffset(Integer offset) {
    return (ConsequenceParams) super.setOffset(offset);
  }

  public Integer getLength() {
    return length;
  }

  public ConsequenceParams setLength(Integer length) {
    return (ConsequenceParams) super.setLength(length);
  }

  public String getQueryType() {
    return queryType;
  }

  public ConsequenceParams setQueryType(String queryType) {
    return (ConsequenceParams) super.setQueryType(queryType);
  }

  public RemoveWordsType getRemoveWordsIfNoResults() {
    return removeWordsIfNoResults;
  }

  public ConsequenceParams setRemoveWordsIfNoResults(Query.RemoveWordsType removeWordsIfNoResults) {
    return (ConsequenceParams) super.setRemoveWordsIfNoResults(removeWordsIfNoResults);
  }

  public Boolean getAdvancedSyntax() {
    return advancedSyntax;
  }

  public ConsequenceParams setAdvancedSyntax(Boolean advancedSyntax) {
    return (ConsequenceParams) super.setAdvancedSyntax(advancedSyntax);
  }

  public List<String> getOptionalWords() {
    return optionalWords;
  }

  public ConsequenceParams setOptionalWords(List<String> optionalWords) {
    return (ConsequenceParams) super.setOptionalWords(optionalWords);
  }

  public RemoveStopWords getRemoveStopWords() {
    return removeStopWords;
  }

  public ConsequenceParams setRemoveStopWords(RemoveStopWords removeStopWords) {
    return (ConsequenceParams) super.setRemoveStopWords(removeStopWords);
  }

  public List<String> getDisableExactOnAttributes() {
    return disableExactOnAttributes;
  }

  public ConsequenceParams setDisableExactOnAttributes(List<String> disableExactOnAttributes) {
    return (ConsequenceParams) super.setDisableExactOnAttributes(disableExactOnAttributes);
  }

  public String getExactOnSingleWordQuery() {
    return exactOnSingleWordQuery;
  }

  public ConsequenceParams setExactOnSingleWordQuery(String exactOnSingleWordQuery) {
    return (ConsequenceParams) super.setExactOnSingleWordQuery(exactOnSingleWordQuery);
  }

  public List<String> getAlternativesAsExact() {
    return alternativesAsExact;
  }

  public ConsequenceParams setAlternativesAsExact(List<String> alternativesAsExact) {
    return (ConsequenceParams) super.setAlternativesAsExact(alternativesAsExact);
  }

  public Integer getMinWordSizefor1Typo() {
    return minWordSizefor1Typo;
  }

  public ConsequenceParams setMinWordSizefor1Typo(Integer minWordSizefor1Typo) {
    return (ConsequenceParams) super.setMinWordSizefor1Typo(minWordSizefor1Typo);
  }

  public Integer getMinWordSizefor2Typos() {
    return minWordSizefor2Typos;
  }

  public ConsequenceParams setMinWordSizefor2Typos(Integer minWordSizefor2Typos) {
    return (ConsequenceParams) super.setMinWordSizefor2Typos(minWordSizefor2Typos);
  }

  public TypoTolerance getTypoTolerance() {
    return typoTolerance;
  }

  public ConsequenceParams setTypoTolerance(Query.TypoTolerance typoTolerance) {
    return (ConsequenceParams) super.setTypoTolerance(typoTolerance);
  }

  public Boolean getAllowTyposOnNumericTokens() {
    return allowTyposOnNumericTokens;
  }

  public ConsequenceParams setAllowTyposOnNumericTokens(Boolean allowTyposOnNumericTokens) {
    return (ConsequenceParams) super.setAllowTyposOnNumericTokens(allowTyposOnNumericTokens);
  }

  public IgnorePlurals getIgnorePlurals() {
    return ignorePlurals;
  }

  public ConsequenceParams setIgnorePlurals(IgnorePlurals ignorePlurals) {
    return (ConsequenceParams) super.setIgnorePlurals(ignorePlurals);
  }

  public List<String> getDisableTypoToleranceOnAttributes() {
    return disableTypoToleranceOnAttributes;
  }

  public ConsequenceParams setDisableTypoToleranceOnAttributes(List<String> disableTypoToleranceOnAttributes) {
    return (ConsequenceParams) super.setDisableTypoToleranceOnAttributes(disableTypoToleranceOnAttributes);
  }

  @JsonAnySetter
  public ConsequenceParams addCustomParameter(String key, String value) {
    return (ConsequenceParams) super.addCustomParameter(key, value);
  }

}
