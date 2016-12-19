package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableMap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Query {

  /* FULL TEXT SEARCH PARAMETERS */
  private String query;
  private QueryType queryType;
  private TypoTolerance typoTolerance;
  private Integer minWordSizeForApprox1;
  private Integer minWordSizeForApprox2;
  private Boolean allowTyposOnNumericTokens;
  private IgnorePlurals ignorePlural;
  private String restrictSearchableAttributes;
  private Boolean advancedSyntax;
  private Boolean analytics;
  private String analyticsTags;
  private Boolean synonyms;
  private Boolean replaceSynonymsInHighlight;
  private String optionalWords;
  private Integer minProximity;
  private RemoveWordsType removeWordsIfNoResult;
  private String disableTypoToleranceOnAttributes;
  private RemoveStopWords removeStopWords;
  private String exactOnSingleWordQuery;
  private String alternativesAsExact;

  /* PAGINATION PARAMETERS */
  private Integer page;
  private Integer hitsPerPage;

  /* PARAMETERS TO CONTROL RESULTS CONTENT */
  private List<String> attributesToRetrieve;
  private List<String> attributesToHighlight;
  private List<String> attributesToSnippet;
  private Boolean getRankingInfo;
  private String highlightPreTag;
  private String highlightPostTag;
  private String snippetEllipsisText;

  /* NUMERIC SEARCH PARAMETERS */
  private String numericFilters;

  /* CATEGORY SEARCH PARAMETER */
  private String tagFilters;

  private List<String> responseFields;

  /* DISTINCT PARAMETER */
  private Integer distinct;

  /* FACETING PARAMETERS */
  private String facets;
  private String facetFilters;
  private Integer maxValuesPerFacet;

  /* UNIFIED FILTER PARAMETER (SQL LIKE) */
  private String filters;

  /* GEO-SEARCH PARAMETERS */
  private String aroundLatLng;
  private Boolean aroundLatLngViaIP;
  private Object aroundRadius;
  private Integer aroundPrecision;
  private Integer minimumAroundRadius;
  private String insideBoundingBox;
  private String insidePolygon;

  /* SECURED API KEYS */
  private String userToken;
  private Integer validUntil;
  private List<String> restrictIndices;
  private String restrictSources;

  /* BROWSE */
  private String cursor;

  /* CUSTOM */
  private Map<String, String> customParameters = new HashMap<>();

  public Query() {
  }

  public Query(String query) {
    this.query = query;
  }

  public Map<String, String> toQueryParam() {
    ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

    /* FULL TEXT SEARCH PARAMETERS */
    builder = add(builder, "query", query);
    builder = add(builder, "queryType", queryType);
    builder = add(builder, "typoTolerance", typoTolerance);
    builder = add(builder, "minWordSizeForApprox1", minWordSizeForApprox1);
    builder = add(builder, "minWordSizeForApprox2", minWordSizeForApprox2);
    builder = add(builder, "allowTyposOnNumericTokens", allowTyposOnNumericTokens);
    builder = add(builder, "ignorePlural", ignorePlural);
    builder = add(builder, "restrictSearchableAttributes", restrictSearchableAttributes);
    builder = add(builder, "advancedSyntax", advancedSyntax);
    builder = add(builder, "analytics", analytics);
    builder = add(builder, "analyticsTags", analyticsTags);
    builder = add(builder, "synonyms", synonyms);
    builder = add(builder, "replaceSynonymsInHighlight", replaceSynonymsInHighlight);
    builder = add(builder, "optionalWords", optionalWords);
    builder = add(builder, "minProximity", minProximity);
    builder = add(builder, "removeWordsIfNoResult", removeWordsIfNoResult);
    builder = add(builder, "disableTypoToleranceOnAttributes", disableTypoToleranceOnAttributes);
    builder = add(builder, "removeStopWords", removeStopWords);
    builder = add(builder, "exactOnSingleWordQuery", exactOnSingleWordQuery);
    builder = add(builder, "alternativesAsExact", alternativesAsExact);

    /* PAGINATION PARAMETERS */
    builder = add(builder, "page", page);
    builder = add(builder, "hitsPerPage", hitsPerPage);

    /* PARAMETERS TO CONTROL RESULTS CONTENT */
    builder = add(builder, "attributesToRetrieve", attributesToRetrieve);
    builder = add(builder, "attributesToHighlight", attributesToHighlight);
    builder = add(builder, "attributesToSnippet", attributesToSnippet);
    builder = add(builder, "getRankingInfo", getRankingInfo);
    builder = add(builder, "highlightPreTag", highlightPreTag);
    builder = add(builder, "highlightPostTag", highlightPostTag);
    builder = add(builder, "snippetEllipsisText", snippetEllipsisText);

    /* NUMERIC SEARCH PARAMETERS */
    builder = add(builder, "numericFilters", numericFilters);

    /* CATEGORY SEARCH PARAMETER */
    builder = add(builder, "tagFilters", tagFilters);

    /* DISTINCT PARAMETER */
    builder = add(builder, "distinct", distinct);

    builder = add(builder, "responseFields", responseFields);

    /* FACETING PARAMETERS */
    builder = add(builder, "facets", facets);
    builder = add(builder, "facetFilters", facetFilters);
    builder = add(builder, "maxValuesPerFacet", maxValuesPerFacet);

    /* UNIFIED FILTER PARAMETER (SQL LIKE) */
    builder = add(builder, "filters", filters);

    /* GEO-SEARCH PARAMETERS */
    builder = add(builder, "aroundLatLng", aroundLatLng);
    builder = add(builder, "aroundLatLngViaIP", aroundLatLngViaIP);
    builder = add(builder, "aroundRadius", aroundRadius);
    builder = add(builder, "aroundPrecision", aroundPrecision);
    builder = add(builder, "minimumAroundRadius", minimumAroundRadius);
    builder = add(builder, "insideBoundingBox", insideBoundingBox);
    builder = add(builder, "insidePolygon", insidePolygon);

    /* SECURED API KEYS */
    builder = add(builder, "userToken", userToken);
    builder = add(builder, "validUntil", validUntil);
    builder = add(builder, "restrictIndices", restrictIndices);
    builder = add(builder, "restrictSources", restrictSources);

    /* BROWSE */
    builder = add(builder, "cursor", cursor);

    /* CUSTOM */
    for (Map.Entry<String, String> entry : customParameters.entrySet()) {
      builder = add(builder, entry.getKey(), entry.getValue());
    }

    return builder.build();
  }

  private ImmutableMap.Builder<String, String> add(ImmutableMap.Builder<String, String> builder, String name, Object value) {
    if (value == null) {
      return builder;
    }
    return builder.put(name, value.toString());
  }

  private ImmutableMap.Builder<String, String> add(ImmutableMap.Builder<String, String> builder, String name, Enum<?> value) {
    if (value == null) {
      return builder;
    }
    return builder.put(name, value.toString());
  }

  private ImmutableMap.Builder<String, String> add(ImmutableMap.Builder<String, String> builder, String name, String value) {
    if (value == null) {
      return builder;
    }
    return builder.put(name, value);
  }

  private ImmutableMap.Builder<String, String> add(ImmutableMap.Builder<String, String> builder, String name, Boolean value) {
    if (value == null) {
      return builder;
    }
    return builder.put(name, value.toString());
  }

  private ImmutableMap.Builder<String, String> add(ImmutableMap.Builder<String, String> builder, String name, Integer value) {
    if (value == null) {
      return builder;
    }
    return builder.put(name, value.toString());
  }

  private ImmutableMap.Builder<String, String> add(ImmutableMap.Builder<String, String> builder, String name, List<String> attributes) {
    if (attributes == null || attributes.isEmpty()) {
      return builder;
    }
    return builder.put(name, String.join(",", attributes));
  }

  public String toParam() {
    StringBuilder builder = new StringBuilder();
    boolean firstOne = true;
    for (Map.Entry<String, String> entry : toQueryParam().entrySet()) {
      try {
        if(!firstOne) {
          builder = builder.append("&");
        }

        builder = builder
          .append(entry.getKey())
          .append("=")
          .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
      } catch (UnsupportedEncodingException ignore) {
      }
      firstOne = false;
    }

    return builder.toString();
  }

  public Query setQuery(String query) {
    this.query = query;
    return this;
  }

  public Query setQueryType(QueryType queryType) {
    this.queryType = queryType;
    return this;
  }

  public Query setTypoTolerance(TypoTolerance typoTolerance) {
    this.typoTolerance = typoTolerance;
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

  public Query setAllowTyposOnNumericTokens(Boolean allowTyposOnNumericTokens) {
    this.allowTyposOnNumericTokens = allowTyposOnNumericTokens;
    return this;
  }

  public Query setIgnorePlural(Boolean ignorePlurals) {
    this.ignorePlural = IgnorePlurals.of(ignorePlurals);
    return this;
  }

  public Query setIgnorePlural(List<String> isoCodes) {
    this.ignorePlural = IgnorePlurals.of(isoCodes);
    return this;
  }

  public Query setRestrictSearchableAttributes(String restrictSearchableAttributes) {
    this.restrictSearchableAttributes = restrictSearchableAttributes;
    return this;
  }

  public Query setAdvancedSyntax(Boolean advancedSyntax) {
    this.advancedSyntax = advancedSyntax;
    return this;
  }

  public Query setAnalytics(Boolean analytics) {
    this.analytics = analytics;
    return this;
  }

  public Query setAnalyticsTags(String analyticsTags) {
    this.analyticsTags = analyticsTags;
    return this;
  }

  public Query setSynonyms(Boolean synonyms) {
    this.synonyms = synonyms;
    return this;
  }

  public Query setReplaceSynonymsInHighlight(Boolean replaceSynonymsInHighlight) {
    this.replaceSynonymsInHighlight = replaceSynonymsInHighlight;
    return this;
  }

  public Query setOptionalWords(String optionalWords) {
    this.optionalWords = optionalWords;
    return this;
  }

  public Query setMinProximity(Integer minProximity) {
    this.minProximity = minProximity;
    return this;
  }

  public Query setRemoveWordsIfNoResult(RemoveWordsType removeWordsIfNoResult) {
    this.removeWordsIfNoResult = removeWordsIfNoResult;
    return this;
  }

  public Query setDisableTypoToleranceOnAttributes(String disableTypoToleranceOnAttributes) {
    this.disableTypoToleranceOnAttributes = disableTypoToleranceOnAttributes;
    return this;
  }

  public Query setRemoveStopWords(Boolean removeStopWords) {
    this.removeStopWords = RemoveStopWords.of(removeStopWords);
    return this;
  }

  public Query setRemoveStopWords(List<String> isoCodes) {
    this.removeStopWords = RemoveStopWords.of(isoCodes);
    return this;
  }

  public Query setExactOnSingleWordQuery(String exactOnSingleWordQuery) {
    this.exactOnSingleWordQuery = exactOnSingleWordQuery;
    return this;
  }

  public Query setAlternativesAsExact(String alternativesAsExact) {
    this.alternativesAsExact = alternativesAsExact;
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

  public Query setAttributesToRetrieve(List<String> attributesToRetrieve) {
    this.attributesToRetrieve = attributesToRetrieve;
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

  public Query setGetRankingInfo(Boolean getRankingInfo) {
    this.getRankingInfo = getRankingInfo;
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

  public Query setNumericFilters(String numericFilters) {
    this.numericFilters = numericFilters;
    return this;
  }

  public Query setTagFilters(String tagFilters) {
    this.tagFilters = tagFilters;
    return this;
  }

  public Query setDistinct(Boolean distinct) {
    this.distinct = distinct ? 1 : 0;
    return this;
  }

  public Query setDistinct(Integer distinct) {
    this.distinct = distinct;
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

  public Query setMaxValuesPerFacet(Integer maxValuesPerFacet) {
    this.maxValuesPerFacet = maxValuesPerFacet;
    return this;
  }

  public Query setFilters(String filters) {
    this.filters = filters;
    return this;
  }

  public Query setAroundLatLng(String aroundLatLng) {
    this.aroundLatLng = aroundLatLng;
    return this;
  }

  public Query setAroundLatLngViaIP(Boolean aroundLatLngViaIP) {
    this.aroundLatLngViaIP = aroundLatLngViaIP;
    return this;
  }

  public Query setAroundRadius(Integer aroundRadius) {
    this.aroundRadius = aroundRadius;
    return this;
  }

  public Query setAroundRadiusAll() {
    this.aroundRadius = "all";
    return this;
  }

  public Query setAroundPrecision(Integer aroundPrecision) {
    this.aroundPrecision = aroundPrecision;
    return this;
  }

  public Query setMinimumAroundRadius(Integer minimumAroundRadius) {
    this.minimumAroundRadius = minimumAroundRadius;
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

  public Query setUserToken(String userToken) {
    this.userToken = userToken;
    return this;
  }

  public Query setCursor(String cursor) {
    this.cursor = cursor;
    return this;
  }

  public Query addCustomParameter(String key, String value) {
    this.customParameters.put(key, value);
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

  public List<String> getResponseFields() {
    return responseFields;
  }

  public Query setResponseFields(List<String> responseFields) {
    this.responseFields = responseFields;
    return this;
  }

  public enum QueryType {
    // all query words are interpreted as prefixes.
    PREFIX_ALL("prefixAll"),
    // only the last word is interpreted as a prefix (default behavior).
    PREFIX_LAST("prefixLast"),
    // no query word is interpreted as a prefix. This option is not recommended.
    PREFIX_NONE("prefixNone");

    private final String name;

    QueryType(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }
  }


  public enum RemoveWordsType {
    // when a query does not return any result, the final word will be
    // removed until there is results. This option is particulary useful on
    // e-commerce websites
    REMOVE_LAST_WORDS("LastWords"),
    // when a query does not return any result, the first word will be
    // removed until there is results. This option is useful on adress
    // search.
    REMOVE_FIRST_WORDS("FirstWords"),
    // No specific processing is done when a query does not return any
    // result.
    REMOVE_NONE("none"),
    // When a query does not return any result, a second trial will be
    // made with all words as optional (which is equivalent to transforming
    // the AND operand between query terms in a OR operand)
    REMOVE_ALLOPTIONAL("allOptional");

    private final String name;

    RemoveWordsType(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }
  }

  public enum TypoTolerance {
    // the typotolerance is enabled and all typos are retrieved. (Default behavior)
    TYPO_TRUE("true"),
    // the typotolerance is disabled.
    TYPO_FALSE("false"),
    // only keep results with the minimum number of typos.
    TYPO_MIN("min"),
    // the typotolerance with a distance=2 is disabled if the results contain hits without typo.
    TYPO_STRICT("strict");

    private final String name;

    TypoTolerance(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
