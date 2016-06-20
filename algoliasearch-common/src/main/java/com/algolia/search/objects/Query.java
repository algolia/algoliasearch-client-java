package com.algolia.search.objects;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

import java.util.List;

public class Query {

  private List<String> attributes;
  
  private List<String> attributesToHighlight;
  
  private List<String> attributesToSnippet;
  
  private List<String> noTypoToleranceOn;
  
  private Integer minWordSizeForApprox1;
  
  private Integer minWordSizeForApprox2;
  
  private Boolean getRankingInfo;
  
  private Boolean ignorePlural;
  
  private Integer distinct;
  
  private Boolean advancedSyntax;
  
  private Integer page;
  
  private Integer hitsPerPage;
  
  private String restrictSearchableAttributes;
  
  private String tags;
  
  private String filters;
  
  private String highlightPreTag;
  
  private String highlightPostTag;
  
  private String snippetEllipsisText;
  
  private Integer minProximity;
  
  private String numerics;
  
  private String insideBoundingBox;
  
  private String insidePolygon;
  
  private String aroundLatLong;
  
  private Boolean aroundLatLongViaIP;
  
  private String query;
  
  private String similarQuery;
  
  private QueryType queryType;
  
  private String optionalWords;
  
  private String facets;
  
  private String facetFilters;
  
  private Integer maxNumberOfFacets;
  
  private Boolean analytics;
  
  private Boolean synonyms;
  
  private Boolean replaceSynonyms;
  
  private Boolean allowTyposOnNumericTokens;
  
  private RemoveWordsType removeWordsIfNoResult;
  
  private TypoTolerance typoTolerance;
  
  private String analyticsTags;
  
  private Integer aroundPrecision;
  
  private Integer aroundRadius;
  
  private Integer minimumAroundRadius;
  
  private Boolean removeStopWords;
  
  private String userToken;
  
  private String referers;
  
  private Integer validUntil;
  
  private String restrictIndices;

  public String getQueryString() {
    GenericUrl url = new GenericUrl();
    url = add(url, "attributes", attributes);
    url = add(url, "attributesToHighlight", attributesToHighlight);
    url = add(url, "attributesToSnippet", attributesToSnippet);
    url = add(url, "noTypoToleranceOn", noTypoToleranceOn);
    url = url.set("minWordSizeForApprox1", minWordSizeForApprox1);
    url = url.set("minWordSizeForApprox2", minWordSizeForApprox2);
    url = url.set("getRankingInfo", getRankingInfo);
    url = url.set("ignorePlural", ignorePlural);
    url = url.set("distinct", distinct);
    url = url.set("advancedSyntax", advancedSyntax);
    url = url.set("page", page);
    url = url.set("hitsPerPage", hitsPerPage);
    url = url.set("restrictSearchableAttributes", restrictSearchableAttributes);
    url = url.set("tags", tags);
    url = url.set("filters", filters);
    url = url.set("highlightPreTag", highlightPreTag);
    url = url.set("highlightPostTag", highlightPostTag);
    url = url.set("snippetEllipsisText", snippetEllipsisText);
    url = url.set("minProximity", minProximity);
    url = url.set("numerics", numerics);
    url = url.set("insideBoundingBox", insideBoundingBox);
    url = url.set("insidePolygon", insidePolygon);
    url = url.set("aroundLatLong", aroundLatLong);
    url = url.set("aroundLatLongViaIP", aroundLatLongViaIP);
    url = url.set("query", query);
    url = url.set("similarQuery", similarQuery);
    url = url.set("queryType", queryType);
    url = url.set("optionalWords", optionalWords);
    url = url.set("facets", facets);
    url = url.set("facetFilters", facetFilters);
    url = url.set("maxNumberOfFacets", maxNumberOfFacets);
    url = url.set("analytics", analytics);
    url = url.set("synonyms", synonyms);
    url = url.set("replaceSynonyms", replaceSynonyms);
    url = url.set("allowTyposOnNumericTokens", allowTyposOnNumericTokens);
    url = url.set("removeWordsIfNoResult", removeWordsIfNoResult);
    url = url.set("typoTolerance", typoTolerance);
    url = url.set("analyticsTags", analyticsTags);
    url = url.set("aroundPrecision", aroundPrecision);
    url = url.set("aroundRadius", aroundRadius);
    url = url.set("minimumAroundRadius", minimumAroundRadius);
    url = url.set("removeStopWords", removeStopWords);
    url = url.set("userToken", userToken);
    url = url.set("referers", referers);
    url = url.set("validUntil", validUntil);
    url = url.set("restrictIndices", restrictIndices);

    String params = url.buildRelativeUrl();
    if (!params.isEmpty() && params.charAt(0) == '?') {
      params = params.substring(1); //remove the first `?`
    }

    return params;
  }

  private GenericUrl add(GenericUrl url, String name, List<String> attributes) {
    if (attributes == null) {
      return url;
    }
    return url.set(name, String.join(",", attributes));
  }

  public Query() {}

  public Query(String query) {
    this.query = query;
  }

  public Query setUserToken(String userToken) {
    this.userToken = userToken;
    return this;
  }

  public Query setAttributes(List<String> attributes) {
    this.attributes = attributes;
    return this;
  }

  public Query setAttributesToHighlight(List<String> attributesToHighlight) {
    this.attributesToHighlight = attributesToHighlight;
    return this;
  }

  public Query setAttributesToSnippet(List<String> attributesToSnippet) {
    this.attributesToSnippet = attributesToSnippet;
    return this;
  }

  public Query setNoTypoToleranceOn(List<String> noTypoToleranceOn) {
    this.noTypoToleranceOn = noTypoToleranceOn;
    return this;
  }

  public Query setMinWordSizeForApprox1(Integer minWordSizeForApprox1) {
    this.minWordSizeForApprox1 = minWordSizeForApprox1;
    return this;
  }

  public Query setMinWordSizeForApprox2(Integer minWordSizeForApprox2) {
    this.minWordSizeForApprox2 = minWordSizeForApprox2;
    return this;
  }

  public Query setGetRankingInfo(Boolean getRankingInfo) {
    this.getRankingInfo = getRankingInfo;
    return this;
  }

  public Query setIgnorePlural(Boolean ignorePlural) {
    this.ignorePlural = ignorePlural;
    return this;
  }

  public Query setDistinct(Integer distinct) {
    this.distinct = distinct;
    return this;
  }

  public Query setAdvancedSyntax(Boolean advancedSyntax) {
    this.advancedSyntax = advancedSyntax;
    return this;
  }

  public Query setPage(Integer page) {
    this.page = page;
    return this;
  }

  public Query setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  public Query setRestrictSearchableAttributes(String restrictSearchableAttributes) {
    this.restrictSearchableAttributes = restrictSearchableAttributes;
    return this;
  }

  public Query setTags(String tags) {
    this.tags = tags;
    return this;
  }

  public Query setFilters(String filters) {
    this.filters = filters;
    return this;
  }

  public Query setHighlightPreTag(String highlightPreTag) {
    this.highlightPreTag = highlightPreTag;
    return this;
  }

  public Query setHighlightPostTag(String highlightPostTag) {
    this.highlightPostTag = highlightPostTag;
    return this;
  }

  public Query setSnippetEllipsisText(String snippetEllipsisText) {
    this.snippetEllipsisText = snippetEllipsisText;
    return this;
  }

  public Query setMinProximity(Integer minProximity) {
    this.minProximity = minProximity;
    return this;
  }

  public Query setNumerics(String numerics) {
    this.numerics = numerics;
    return this;
  }

  public Query setInsideBoundingBox(String insideBoundingBox) {
    this.insideBoundingBox = insideBoundingBox;
    return this;
  }

  public Query setInsidePolygon(String insidePolygon) {
    this.insidePolygon = insidePolygon;
    return this;
  }

  public Query setAroundLatLong(String aroundLatLong) {
    this.aroundLatLong = aroundLatLong;
    return this;
  }

  public Query setAroundLatLongViaIP(Boolean aroundLatLongViaIP) {
    this.aroundLatLongViaIP = aroundLatLongViaIP;
    return this;
  }

  public Query setQuery(String query) {
    this.query = query;
    return this;
  }

  public Query setSimilarQuery(String similarQuery) {
    this.similarQuery = similarQuery;
    return this;
  }

  public Query setQueryType(QueryType queryType) {
    this.queryType = queryType;
    return this;
  }

  public Query setOptionalWords(String optionalWords) {
    this.optionalWords = optionalWords;
    return this;
  }

  public Query setFacets(String facets) {
    this.facets = facets;
    return this;
  }

  public Query setFacetFilters(String facetFilters) {
    this.facetFilters = facetFilters;
    return this;
  }

  public Query setMaxNumberOfFacets(Integer maxNumberOfFacets) {
    this.maxNumberOfFacets = maxNumberOfFacets;
    return this;
  }

  public Query setAnalytics(Boolean analytics) {
    this.analytics = analytics;
    return this;
  }

  public Query setSynonyms(Boolean synonyms) {
    this.synonyms = synonyms;
    return this;
  }

  public Query setReplaceSynonyms(Boolean replaceSynonyms) {
    this.replaceSynonyms = replaceSynonyms;
    return this;
  }

  public Query setAllowTyposOnNumericTokens(Boolean allowTyposOnNumericTokens) {
    this.allowTyposOnNumericTokens = allowTyposOnNumericTokens;
    return this;
  }

  public Query setRemoveWordsIfNoResult(RemoveWordsType removeWordsIfNoResult) {
    this.removeWordsIfNoResult = removeWordsIfNoResult;
    return this;
  }

  public Query setTypoTolerance(TypoTolerance typoTolerance) {
    this.typoTolerance = typoTolerance;
    return this;
  }

  public Query setAnalyticsTags(String analyticsTags) {
    this.analyticsTags = analyticsTags;
    return this;
  }

  public Query setAroundPrecision(int aroundPrecision) {
    this.aroundPrecision = aroundPrecision;
    return this;
  }

  public Query setAroundRadius(int aroundRadius) {
    this.aroundRadius = aroundRadius;
    return this;
  }

  public Query setMinimumAroundRadius(int minimumAroundRadius) {
    this.minimumAroundRadius = minimumAroundRadius;
    return this;
  }

  public Query setRemoveStopWords(Boolean removeStopWords) {
    this.removeStopWords = removeStopWords;
    return this;
  }

  public Query setReferers(String referers) {
    this.referers = referers;
    return this;
  }

  public Query setValidUntil(Integer validUntil) {
    this.validUntil = validUntil;
    return this;
  }

  public Query setRestrictIndices(String restrictIndices) {
    this.restrictIndices = restrictIndices;
    return this;
  }

  public enum QueryType {
    // / all query words are interpreted as prefixes.
    PREFIX_ALL,
    // / only the last word is interpreted as a prefix (default behavior).
    PREFIX_LAST,
    // / no query word is interpreted as a prefix. This option is not
    // recommended.
    PREFIX_NONE,
    // The parameter isn't set
    PREFIX_NOTSET
  }


  public enum RemoveWordsType {
    // / when a query does not return any result, the final word will be
    // removed until there is results. This option is particulary useful on
    // e-commerce websites
    REMOVE_LAST_WORDS,
    // / when a query does not return any result, the first word will be
    // removed until there is results. This option is useful on adress
    // search.
    REMOVE_FIRST_WORDS,
    // / No specific processing is done when a query does not return any
    // result.
    REMOVE_NONE,
    // / When a query does not return any result, a second trial will be
    // made with all words as optional (which is equivalent to transforming
    // the AND operand between query terms in a OR operand)
    REMOVE_ALLOPTIONAL,
    // The parameter isn't set
    REMOVE_NOTSET
  }

  public enum TypoTolerance {
    // / the typotolerance is enabled and all typos are retrieved. (Default
    // behavior)
    TYPO_TRUE,
    // / the typotolerance is disabled.
    TYPO_FALSE,
    // / only keep results with the minimum number of typos.
    TYPO_MIN,
    // / the typotolerance with a distance=2 is disabled if the results
    // contain hits without typo.
    TYPO_STRICT,
    // The parameter isn't set
    TYPO_NOTSET
  }
}
