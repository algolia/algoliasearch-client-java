package com.algolia.search.models.rules;

import com.algolia.search.models.indexing.AroundRadius;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.RemoveWordsType;
import com.algolia.search.models.settings.Distinct;
import com.algolia.search.models.settings.IgnorePlurals;
import com.algolia.search.models.settings.RemoveStopWords;
import com.algolia.search.models.settings.TypoTolerance;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

/**
 * Consequence parameter. More information:
 *
 * @see <a href="https://www.algolia.com/doc/api-client/methods/query-rules">Algolia.com</a>
 */
@SuppressWarnings({"unused"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsequenceParams extends Query {

  private ConsequenceQuery query;

  @JsonDeserialize(using = AutomaticFacetFilterDeserializer.class)
  private List<AutomaticFacetFilter> automaticFacetFilters;

  @JsonDeserialize(using = AutomaticFacetFilterDeserializer.class)
  private List<AutomaticFacetFilter> automaticOptionalFacetFilters;

  public ConsequenceParams() {}

  @JsonProperty("query")
  public ConsequenceQuery getConsequenceQuery() {
    return query;
  }

  @JsonProperty("query")
  public ConsequenceParams setConsequenceQuery(ConsequenceQuery consequenceQuery) {
    this.query = consequenceQuery;
    return this;
  }

  public List<AutomaticFacetFilter> getAutomaticFacetFilters() {
    return automaticFacetFilters;
  }

  public ConsequenceParams setAutomaticFacetFilters(
      List<AutomaticFacetFilter> automaticFacetFilters) {
    this.automaticFacetFilters = automaticFacetFilters;
    return this;
  }

  public List<AutomaticFacetFilter> getAutomaticOptionalFacetFilters() {
    return automaticOptionalFacetFilters;
  }

  public ConsequenceParams setAutomaticOptionalFacetFilters(
      List<AutomaticFacetFilter> automaticOptionalFacetFilters) {
    this.automaticOptionalFacetFilters = automaticOptionalFacetFilters;
    return this;
  }

  @Override
  public ConsequenceParams setGetRankingInfo(Boolean getRankingInfo) {
    return (ConsequenceParams) super.setGetRankingInfo(getRankingInfo);
  }

  @Override
  public ConsequenceParams setNumericFilters(List<List<String>> numericFilters) {
    return (ConsequenceParams) super.setNumericFilters(numericFilters);
  }

  @Override
  public ConsequenceParams setTagFilters(List<List<String>> tagFilters) {
    return (ConsequenceParams) super.setTagFilters(tagFilters);
  }

  @Override
  public ConsequenceParams setClickAnalytics(Boolean clickAnalytics) {
    return (ConsequenceParams) super.setClickAnalytics(clickAnalytics);
  }

  @Override
  public ConsequenceParams setAnalytics(Boolean analytics) {
    return (ConsequenceParams) super.setAnalytics(analytics);
  }

  @Override
  public ConsequenceParams setAnalyticsTags(List<String> analyticsTags) {
    return (ConsequenceParams) super.setAnalyticsTags(analyticsTags);
  }

  @Override
  public ConsequenceParams setSynonyms(Boolean synonyms) {
    return (ConsequenceParams) super.setSynonyms(synonyms);
  }

  @Override
  public ConsequenceParams setReplaceSynonymsInHighlight(Boolean replaceSynonymsInHighlight) {
    return (ConsequenceParams) super.setReplaceSynonymsInHighlight(replaceSynonymsInHighlight);
  }

  @Override
  public ConsequenceParams setMinProximity(Integer minProximity) {
    return (ConsequenceParams) super.setMinProximity(minProximity);
  }

  @Override
  public ConsequenceParams setResponseFields(List<String> responseFields) {
    return (ConsequenceParams) super.setResponseFields(responseFields);
  }

  @Override
  public ConsequenceParams setMaxFacetHits(Long maxFacetHits) {
    return (ConsequenceParams) super.setMaxFacetHits(maxFacetHits);
  }

  @Override
  public ConsequenceParams setPercentileComputation(Boolean percentileComputation) {
    return (ConsequenceParams) super.setPercentileComputation(percentileComputation);
  }

  @Override
  public ConsequenceParams setQueryLanguages(List<String> queryLanguages) {
    return (ConsequenceParams) super.setQueryLanguages(queryLanguages);
  }

  @Override
  public ConsequenceParams setAttributesToRetrieve(List<String> attributesToRetrieve) {
    return (ConsequenceParams) super.setAttributesToRetrieve(attributesToRetrieve);
  }

  @Override
  public ConsequenceParams setRestrictSearchableAttributes(
      List<String> restrictSearchableAttributes) {
    return (ConsequenceParams) super.setRestrictSearchableAttributes(restrictSearchableAttributes);
  }

  @Override
  public ConsequenceParams setFilters(String filters) {
    return (ConsequenceParams) super.setFilters(filters);
  }

  @Override
  public ConsequenceParams setFacets(List<String> facets) {
    return (ConsequenceParams) super.setFacets(facets);
  }

  @Override
  public ConsequenceParams setMaxValuesPerFacet(Long maxValuesPerFacet) {
    return (ConsequenceParams) super.setMaxValuesPerFacet(maxValuesPerFacet);
  }

  @Override
  public ConsequenceParams setFacetingAfterDistinct(Boolean facetingAfterDistinct) {
    return (ConsequenceParams) super.setFacetingAfterDistinct(facetingAfterDistinct);
  }

  @Override
  public ConsequenceParams setSortFacetValuesBy(String sortFacetValuesBy) {
    return (ConsequenceParams) super.setSortFacetValuesBy(sortFacetValuesBy);
  }

  @Override
  public ConsequenceParams setAroundLatLng(String aroundLatLng) {
    return (ConsequenceParams) super.setAroundLatLng(aroundLatLng);
  }

  @Override
  public ConsequenceParams setAroundLatLngViaIP(Boolean aroundLatLngViaIP) {
    return (ConsequenceParams) super.setAroundLatLngViaIP(aroundLatLngViaIP);
  }

  @Override
  public ConsequenceParams setAroundPrecision(Integer aroundPrecision) {
    return (ConsequenceParams) super.setAroundPrecision(aroundPrecision);
  }

  @Override
  public ConsequenceParams setMinimumAroundRadius(Integer minimumAroundRadius) {
    return (ConsequenceParams) super.setMinimumAroundRadius(minimumAroundRadius);
  }

  @Override
  public ConsequenceParams setInsideBoundingBox(List<List<Float>> insideBoundingBox) {
    return (ConsequenceParams) super.setInsideBoundingBox(insideBoundingBox);
  }

  @Override
  public ConsequenceParams setInsidePolygon(List<List<Float>> insidePolygon) {
    return (ConsequenceParams) super.setInsidePolygon(insidePolygon);
  }

  @Override
  public ConsequenceParams setAttributesToHighlight(List<String> attributesToHighlight) {
    return (ConsequenceParams) super.setAttributesToHighlight(attributesToHighlight);
  }

  @Override
  public ConsequenceParams setAttributesToSnippet(List<String> attributesToSnippet) {
    return (ConsequenceParams) super.setAttributesToSnippet(attributesToSnippet);
  }

  @Override
  public ConsequenceParams setHighlightPreTag(String highlightPreTag) {
    return (ConsequenceParams) super.setHighlightPreTag(highlightPreTag);
  }

  @Override
  public ConsequenceParams setHighlightPostTag(String highlightPostTag) {
    return (ConsequenceParams) super.setHighlightPostTag(highlightPostTag);
  }

  @Override
  public ConsequenceParams setSnippetEllipsisText(String snippetEllipsisText) {
    return (ConsequenceParams) super.setSnippetEllipsisText(snippetEllipsisText);
  }

  @Override
  public ConsequenceParams setRestrictHighlightAndSnippetArrays(
      Boolean restrictHighlightAndSnippetArrays) {
    return (ConsequenceParams)
        super.setRestrictHighlightAndSnippetArrays(restrictHighlightAndSnippetArrays);
  }

  @Override
  public ConsequenceParams setPage(Integer page) {
    return (ConsequenceParams) super.setPage(page);
  }

  @Override
  public ConsequenceParams setHitsPerPage(Integer hitsPerPage) {
    return (ConsequenceParams) super.setHitsPerPage(hitsPerPage);
  }

  @Override
  public ConsequenceParams setOffset(Integer offset) {
    return (ConsequenceParams) super.setOffset(offset);
  }

  @Override
  public ConsequenceParams setLength(Integer length) {
    return (ConsequenceParams) super.setLength(length);
  }

  @Override
  public ConsequenceParams setEnableRules(Boolean enableRules) {
    return (ConsequenceParams) super.setEnableRules(enableRules);
  }

  @Override
  public ConsequenceParams setRuleContexts(List<String> ruleContexts) {
    return (ConsequenceParams) super.setRuleContexts(ruleContexts);
  }

  @Override
  public ConsequenceParams setQueryType(String queryType) {
    return (ConsequenceParams) super.setQueryType(queryType);
  }

  @Override
  public ConsequenceParams setAdvancedSyntax(Boolean advancedSyntax) {
    return (ConsequenceParams) super.setAdvancedSyntax(advancedSyntax);
  }

  @Override
  public ConsequenceParams setAdvancedSyntaxFeatures(List<String> advancedSyntaxFeatures) {
    return (ConsequenceParams) super.setAdvancedSyntaxFeatures(advancedSyntaxFeatures);
  }

  @Override
  public ConsequenceParams setOptionalWords(List<String> optionalWords) {
    return (ConsequenceParams) super.setOptionalWords(optionalWords);
  }

  @Override
  public ConsequenceParams setDisableExactOnAttributes(List<String> disableExactOnAttributes) {
    return (ConsequenceParams) super.setDisableExactOnAttributes(disableExactOnAttributes);
  }

  @Override
  public ConsequenceParams setExactOnSingleWordQuery(String exactOnSingleWordQuery) {
    return (ConsequenceParams) super.setExactOnSingleWordQuery(exactOnSingleWordQuery);
  }

  @Override
  public ConsequenceParams setAlternativesAsExact(List<String> alternativesAsExact) {
    return (ConsequenceParams) super.setAlternativesAsExact(alternativesAsExact);
  }

  @Override
  public ConsequenceParams setMinWordSizefor1Typo(Integer minWordSizefor1Typo) {
    return (ConsequenceParams) super.setMinWordSizefor1Typo(minWordSizefor1Typo);
  }

  @Override
  public ConsequenceParams setMinWordSizefor2Typos(Integer minWordSizefor2Typos) {
    return (ConsequenceParams) super.setMinWordSizefor2Typos(minWordSizefor2Typos);
  }

  @Override
  public ConsequenceParams setAllowTyposOnNumericTokens(Boolean allowTyposOnNumericTokens) {
    return (ConsequenceParams) super.setAllowTyposOnNumericTokens(allowTyposOnNumericTokens);
  }

  @Override
  public ConsequenceParams setDisableTypoToleranceOnAttributes(
      List<String> disableTypoToleranceOnAttributes) {
    return (ConsequenceParams)
        super.setDisableTypoToleranceOnAttributes(disableTypoToleranceOnAttributes);
  }

  @Override
  public ConsequenceParams setUserToken(String userToken) {
    return (ConsequenceParams) super.setUserToken(userToken);
  }

  @Override
  public ConsequenceParams setValidUntil(Integer validUntil) {
    return (ConsequenceParams) super.setValidUntil(validUntil);
  }

  @Override
  public ConsequenceParams setRestrictIndices(List<String> restrictIndices) {
    return (ConsequenceParams) super.setRestrictIndices(restrictIndices);
  }

  @Override
  public ConsequenceParams setRestrictSources(String restrictSources) {
    return (ConsequenceParams) super.setRestrictSources(restrictSources);
  }

  @Override
  public ConsequenceParams setEnablePersonalization(Boolean enablePersonalization) {
    return (ConsequenceParams) super.setEnablePersonalization(enablePersonalization);
  }

  @Override
  public ConsequenceParams setPersonalizationImpact(Integer personalizationImpact) {
    return (ConsequenceParams) super.setPersonalizationImpact(personalizationImpact);
  }

  @Override
  public ConsequenceParams setFacetFilters(List<List<String>> facetFilters) {
    return (ConsequenceParams) super.setFacetFilters(facetFilters);
  }

  @Override
  public ConsequenceParams setDistinct(Distinct distinct) {
    return (ConsequenceParams) super.setDistinct(distinct);
  }

  @Override
  public ConsequenceParams setAroundRadius(AroundRadius aroundRadius) {
    return (ConsequenceParams) super.setAroundRadius(aroundRadius);
  }

  @Override
  public ConsequenceParams setRemoveWordsIfNoResults(RemoveWordsType removeWordsIfNoResults) {
    return (ConsequenceParams) super.setRemoveWordsIfNoResults(removeWordsIfNoResults);
  }

  @Override
  public ConsequenceParams setRemoveStopWords(RemoveStopWords removeStopWords) {
    return (ConsequenceParams) super.setRemoveStopWords(removeStopWords);
  }

  @Override
  public ConsequenceParams setTypoTolerance(TypoTolerance typoTolerance) {
    return (ConsequenceParams) super.setTypoTolerance(typoTolerance);
  }

  @Override
  public ConsequenceParams setIgnorePlurals(IgnorePlurals ignorePlurals) {
    return (ConsequenceParams) super.setIgnorePlurals(ignorePlurals);
  }

  @Override
  public ConsequenceParams setOptionalFilters(List<List<String>> optionalFilters) {
    return (ConsequenceParams) super.setOptionalFilters(optionalFilters);
  }

  @Override
  public ConsequenceParams setSumOrFiltersScores(Boolean sumOrFiltersScores) {
    return (ConsequenceParams) super.setSumOrFiltersScores(sumOrFiltersScores);
  }
}
