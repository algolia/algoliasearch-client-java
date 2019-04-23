package com.algolia.search.models.indexing;

import com.algolia.search.models.settings.Distinct;
import com.algolia.search.models.settings.IgnorePlurals;
import com.algolia.search.models.settings.RemoveStopWords;
import com.algolia.search.models.settings.TypoTolerance;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrowseIndexQuery extends Query {

  public BrowseIndexQuery() {}

  public BrowseIndexQuery(String query) {
    super(query);
  }

  public String getCursor() {
    return cursor;
  }

  public BrowseIndexQuery setCursor(String cursor) {
    this.cursor = cursor;
    return this;
  }

  private String cursor;

  @Override
  public BrowseIndexQuery setQuery(String query) {
    return (BrowseIndexQuery) super.setQuery(query);
  }

  @Override
  public BrowseIndexQuery setGetRankingInfo(Boolean getRankingInfo) {
    return (BrowseIndexQuery) super.setGetRankingInfo(getRankingInfo);
  }

  @Override
  public BrowseIndexQuery setNumericFilters(List<List<String>> numericFilters) {
    return (BrowseIndexQuery) super.setNumericFilters(numericFilters);
  }

  @Override
  public BrowseIndexQuery setTagFilters(List<List<String>> tagFilters) {
    return (BrowseIndexQuery) super.setTagFilters(tagFilters);
  }

  @Override
  public BrowseIndexQuery setClickAnalytics(Boolean clickAnalytics) {
    return (BrowseIndexQuery) super.setClickAnalytics(clickAnalytics);
  }

  @Override
  public BrowseIndexQuery setAnalytics(Boolean analytics) {
    return (BrowseIndexQuery) super.setAnalytics(analytics);
  }

  @Override
  public BrowseIndexQuery setAnalyticsTags(List<String> analyticsTags) {
    return (BrowseIndexQuery) super.setAnalyticsTags(analyticsTags);
  }

  @Override
  public BrowseIndexQuery setSynonyms(Boolean synonyms) {
    return (BrowseIndexQuery) super.setSynonyms(synonyms);
  }

  @Override
  public BrowseIndexQuery setReplaceSynonymsInHighlight(Boolean replaceSynonymsInHighlight) {
    return (BrowseIndexQuery) super.setReplaceSynonymsInHighlight(replaceSynonymsInHighlight);
  }

  @Override
  public BrowseIndexQuery setMinProximity(Integer minProximity) {
    return (BrowseIndexQuery) super.setMinProximity(minProximity);
  }

  @Override
  public BrowseIndexQuery setResponseFields(List<String> responseFields) {
    return (BrowseIndexQuery) super.setResponseFields(responseFields);
  }

  @Override
  public BrowseIndexQuery setMaxFacetHits(Long maxFacetHits) {
    return (BrowseIndexQuery) super.setMaxFacetHits(maxFacetHits);
  }

  @Override
  public BrowseIndexQuery setPercentileComputation(Boolean percentileComputation) {
    return (BrowseIndexQuery) super.setPercentileComputation(percentileComputation);
  }

  @Override
  public BrowseIndexQuery setQueryLanguages(List<String> queryLanguages) {
    return (BrowseIndexQuery) super.setQueryLanguages(queryLanguages);
  }

  @Override
  public BrowseIndexQuery setAttributesToRetrieve(List<String> attributesToRetrieve) {
    return (BrowseIndexQuery) super.setAttributesToRetrieve(attributesToRetrieve);
  }

  @Override
  public BrowseIndexQuery setRestrictSearchableAttributes(
      List<String> restrictSearchableAttributes) {
    return (BrowseIndexQuery) super.setRestrictSearchableAttributes(restrictSearchableAttributes);
  }

  @Override
  public BrowseIndexQuery setFilters(String filters) {
    return (BrowseIndexQuery) super.setFilters(filters);
  }

  @Override
  public BrowseIndexQuery setFacets(List<String> facets) {
    return (BrowseIndexQuery) super.setFacets(facets);
  }

  @Override
  public BrowseIndexQuery setMaxValuesPerFacet(Long maxValuesPerFacet) {
    return (BrowseIndexQuery) super.setMaxValuesPerFacet(maxValuesPerFacet);
  }

  @Override
  public BrowseIndexQuery setFacetingAfterDistinct(Boolean facetingAfterDistinct) {
    return (BrowseIndexQuery) super.setFacetingAfterDistinct(facetingAfterDistinct);
  }

  @Override
  public BrowseIndexQuery setSortFacetValuesBy(String sortFacetValuesBy) {
    return (BrowseIndexQuery) super.setSortFacetValuesBy(sortFacetValuesBy);
  }

  @Override
  public BrowseIndexQuery setAroundLatLng(String aroundLatLng) {
    return (BrowseIndexQuery) super.setAroundLatLng(aroundLatLng);
  }

  @Override
  public BrowseIndexQuery setAroundLatLngViaIP(Boolean aroundLatLngViaIP) {
    return (BrowseIndexQuery) super.setAroundLatLngViaIP(aroundLatLngViaIP);
  }

  @Override
  public BrowseIndexQuery setAroundPrecision(Integer aroundPrecision) {
    return (BrowseIndexQuery) super.setAroundPrecision(aroundPrecision);
  }

  @Override
  public BrowseIndexQuery setMinimumAroundRadius(Integer minimumAroundRadius) {
    return (BrowseIndexQuery) super.setMinimumAroundRadius(minimumAroundRadius);
  }

  @Override
  public BrowseIndexQuery setInsideBoundingBox(List<List<Float>> insideBoundingBox) {
    return (BrowseIndexQuery) super.setInsideBoundingBox(insideBoundingBox);
  }

  @Override
  public BrowseIndexQuery setInsidePolygon(List<List<Float>> insidePolygon) {
    return (BrowseIndexQuery) super.setInsidePolygon(insidePolygon);
  }

  @Override
  public BrowseIndexQuery setAttributesToHighlight(List<String> attributesToHighlight) {
    return (BrowseIndexQuery) super.setAttributesToHighlight(attributesToHighlight);
  }

  @Override
  public BrowseIndexQuery setAttributesToSnippet(List<String> attributesToSnippet) {
    return (BrowseIndexQuery) super.setAttributesToSnippet(attributesToSnippet);
  }

  @Override
  public BrowseIndexQuery setHighlightPreTag(String highlightPreTag) {
    return (BrowseIndexQuery) super.setHighlightPreTag(highlightPreTag);
  }

  @Override
  public BrowseIndexQuery setHighlightPostTag(String highlightPostTag) {
    return (BrowseIndexQuery) super.setHighlightPostTag(highlightPostTag);
  }

  @Override
  public BrowseIndexQuery setSnippetEllipsisText(String snippetEllipsisText) {
    return (BrowseIndexQuery) super.setSnippetEllipsisText(snippetEllipsisText);
  }

  @Override
  public BrowseIndexQuery setRestrictHighlightAndSnippetArrays(
      Boolean restrictHighlightAndSnippetArrays) {
    return (BrowseIndexQuery)
        super.setRestrictHighlightAndSnippetArrays(restrictHighlightAndSnippetArrays);
  }

  @Override
  public BrowseIndexQuery setPage(Integer page) {
    return (BrowseIndexQuery) super.setPage(page);
  }

  @Override
  public BrowseIndexQuery setHitsPerPage(Integer hitsPerPage) {
    return (BrowseIndexQuery) super.setHitsPerPage(hitsPerPage);
  }

  @Override
  public BrowseIndexQuery setOffset(Integer offset) {
    return (BrowseIndexQuery) super.setOffset(offset);
  }

  @Override
  public BrowseIndexQuery setLength(Integer length) {
    return (BrowseIndexQuery) super.setLength(length);
  }

  @Override
  public BrowseIndexQuery setEnableRules(Boolean enableRules) {
    return (BrowseIndexQuery) super.setEnableRules(enableRules);
  }

  @Override
  public BrowseIndexQuery setRuleContexts(List<String> ruleContexts) {
    return (BrowseIndexQuery) super.setRuleContexts(ruleContexts);
  }

  @Override
  public BrowseIndexQuery setQueryType(String queryType) {
    return (BrowseIndexQuery) super.setQueryType(queryType);
  }

  @Override
  public BrowseIndexQuery setAdvancedSyntax(Boolean advancedSyntax) {
    return (BrowseIndexQuery) super.setAdvancedSyntax(advancedSyntax);
  }

  @Override
  public BrowseIndexQuery setAdvancedSyntaxFeatures(List<String> advancedSyntaxFeatures) {
    return (BrowseIndexQuery) super.setAdvancedSyntaxFeatures(advancedSyntaxFeatures);
  }

  @Override
  public BrowseIndexQuery setOptionalWords(List<String> optionalWords) {
    return (BrowseIndexQuery) super.setOptionalWords(optionalWords);
  }

  @Override
  public BrowseIndexQuery setDisableExactOnAttributes(List<String> disableExactOnAttributes) {
    return (BrowseIndexQuery) super.setDisableExactOnAttributes(disableExactOnAttributes);
  }

  @Override
  public BrowseIndexQuery setExactOnSingleWordQuery(String exactOnSingleWordQuery) {
    return (BrowseIndexQuery) super.setExactOnSingleWordQuery(exactOnSingleWordQuery);
  }

  @Override
  public BrowseIndexQuery setAlternativesAsExact(List<String> alternativesAsExact) {
    return (BrowseIndexQuery) super.setAlternativesAsExact(alternativesAsExact);
  }

  @Override
  public BrowseIndexQuery setMinWordSizefor1Typo(Integer minWordSizefor1Typo) {
    return (BrowseIndexQuery) super.setMinWordSizefor1Typo(minWordSizefor1Typo);
  }

  @Override
  public BrowseIndexQuery setMinWordSizefor2Typos(Integer minWordSizefor2Typos) {
    return (BrowseIndexQuery) super.setMinWordSizefor2Typos(minWordSizefor2Typos);
  }

  @Override
  public BrowseIndexQuery setAllowTyposOnNumericTokens(Boolean allowTyposOnNumericTokens) {
    return (BrowseIndexQuery) super.setAllowTyposOnNumericTokens(allowTyposOnNumericTokens);
  }

  @Override
  public BrowseIndexQuery setDisableTypoToleranceOnAttributes(
      List<String> disableTypoToleranceOnAttributes) {
    return (BrowseIndexQuery)
        super.setDisableTypoToleranceOnAttributes(disableTypoToleranceOnAttributes);
  }

  @Override
  public BrowseIndexQuery setUserToken(String userToken) {
    return (BrowseIndexQuery) super.setUserToken(userToken);
  }

  @Override
  public BrowseIndexQuery setValidUntil(Integer validUntil) {
    return (BrowseIndexQuery) super.setValidUntil(validUntil);
  }

  @Override
  public BrowseIndexQuery setRestrictIndices(List<String> restrictIndices) {
    return (BrowseIndexQuery) super.setRestrictIndices(restrictIndices);
  }

  @Override
  public BrowseIndexQuery setRestrictSources(String restrictSources) {
    return (BrowseIndexQuery) super.setRestrictSources(restrictSources);
  }

  @Override
  public BrowseIndexQuery setEnablePersonalization(Boolean enablePersonalization) {
    return (BrowseIndexQuery) super.setEnablePersonalization(enablePersonalization);
  }

  @Override
  public BrowseIndexQuery setPersonalizationImpact(Integer personalizationImpact) {
    return (BrowseIndexQuery) super.setPersonalizationImpact(personalizationImpact);
  }

  @Override
  public BrowseIndexQuery setFacetFilters(List<List<String>> facetFilters) {
    return (BrowseIndexQuery) super.setFacetFilters(facetFilters);
  }

  @Override
  public BrowseIndexQuery setDistinct(Distinct distinct) {
    return (BrowseIndexQuery) super.setDistinct(distinct);
  }

  @Override
  public BrowseIndexQuery setAroundRadius(AroundRadius aroundRadius) {
    return (BrowseIndexQuery) super.setAroundRadius(aroundRadius);
  }

  @Override
  public BrowseIndexQuery setRemoveWordsIfNoResults(RemoveWordsType removeWordsIfNoResults) {
    return (BrowseIndexQuery) super.setRemoveWordsIfNoResults(removeWordsIfNoResults);
  }

  @Override
  public BrowseIndexQuery setRemoveStopWords(RemoveStopWords removeStopWords) {
    return (BrowseIndexQuery) super.setRemoveStopWords(removeStopWords);
  }

  @Override
  public BrowseIndexQuery setTypoTolerance(TypoTolerance typoTolerance) {
    return (BrowseIndexQuery) super.setTypoTolerance(typoTolerance);
  }

  @Override
  public BrowseIndexQuery setIgnorePlurals(IgnorePlurals ignorePlurals) {
    return (BrowseIndexQuery) super.setIgnorePlurals(ignorePlurals);
  }

  @Override
  public BrowseIndexQuery setOptionalFilters(List<List<String>> optionalFilters) {
    return (BrowseIndexQuery) super.setOptionalFilters(optionalFilters);
  }

  @Override
  public BrowseIndexQuery setSumOrFiltersScores(Boolean sumOrFiltersScores) {
    return (BrowseIndexQuery) super.setSumOrFiltersScores(sumOrFiltersScores);
  }
}
