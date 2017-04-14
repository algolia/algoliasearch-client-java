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

  /* advanced */
  private Distinct distinct;
  private Boolean getRankingInfo;
  private List<String> numericFilters;
  private List<String> tagFilters;
  private Boolean analytics;
  private String analyticsTags;
  private Boolean synonyms;
  private Boolean replaceSynonymsInHighlight;
  private Integer minProximity;
  private List<String> responseFields;
  private Integer maxFacetHits;
  private Boolean percentileComputation;

  /* attributes */
  private List<String> attributesToRetrieve;
  private List<String> restrictSearchableAttributes;

  /* filtering-faceting */
  private String filters;
  private String facets;
  private Integer maxValuesPerFacet;
  private List<String> facetFilters;
  private Boolean facetingAfterDistinct;

  /* geo-search */
  private String aroundLatLng;
  private Boolean aroundLatLngViaIP;
  private Object aroundRadius;
  private Integer aroundPrecision;
  private Integer minimumAroundRadius;
  private List<String> insideBoundingBox;
  private List<String> insidePolygon;

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
  //Nothing in Query

  /* query strategy */
  private String queryType;
  private RemoveWordsType removeWordsIfNoResults;
  private Boolean advancedSyntax;
  private List<String> optionalWords;
  private RemoveStopWords removeStopWords;
  private List<String> disableExactOnAttributes;
  private String exactOnSingleWordQuery;
  private List<String> alternativesAsExact;

  /* ranking */
  //Nothing in Query

  /* search */
  private String query;

  /* typos */
  private Integer minWordSizefor1Typo;
  private Integer minWordSizefor2Typos;
  private TypoTolerance typoTolerance;
  private Boolean allowTyposOnNumericTokens;
  private IgnorePlurals ignorePlurals;
  private List<String> disableTypoToleranceOnAttributes;

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

    /* advanced */
    builder = add(builder, "distinct", distinct);
    builder = add(builder, "getRankingInfo", getRankingInfo);
    builder = add(builder, "numericFilters", numericFilters);
    builder = add(builder, "tagFilters", tagFilters);
    builder = add(builder, "analytics", analytics);
    builder = add(builder, "analyticsTags", analyticsTags);
    builder = add(builder, "synonyms", synonyms);
    builder = add(builder, "replaceSynonymsInHighlight", replaceSynonymsInHighlight);
    builder = add(builder, "minProximity", minProximity);
    builder = add(builder, "responseFields", responseFields);
    builder = add(builder, "maxFacetHits", maxFacetHits);
    builder = add(builder, "percentileComputation", percentileComputation);

    /* attributes */
    builder = add(builder, "attributesToRetrieve", attributesToRetrieve);
    builder = add(builder, "restrictSearchableAttributes", restrictSearchableAttributes);

    /* filtering-faceting */
    builder = add(builder, "filters", filters);
    builder = add(builder, "facets", facets);
    builder = add(builder, "maxValuesPerFacet", maxValuesPerFacet);
    builder = add(builder, "facetFilters", facetFilters);
    builder = add(builder, "facetingAfterDistinct", facetingAfterDistinct);

    /* geo-search */
    builder = add(builder, "aroundLatLng", aroundLatLng);
    builder = add(builder, "aroundLatLngViaIP", aroundLatLngViaIP);
    builder = add(builder, "aroundRadius", aroundRadius);
    builder = add(builder, "aroundPrecision", aroundPrecision);
    builder = add(builder, "minimumAroundRadius", minimumAroundRadius);
    builder = add(builder, "insideBoundingBox", insideBoundingBox);
    builder = add(builder, "insidePolygon", insidePolygon);

    /* highlighting-snippeting */
    builder = add(builder, "attributesToHighlight", attributesToHighlight);
    builder = add(builder, "attributesToSnippet", attributesToSnippet);
    builder = add(builder, "highlightPreTag", highlightPreTag);
    builder = add(builder, "highlightPostTag", highlightPostTag);
    builder = add(builder, "snippetEllipsisText", snippetEllipsisText);
    builder = add(builder, "restrictHighlightAndSnippetArrays", restrictHighlightAndSnippetArrays);

    /* pagination */
    builder = add(builder, "page", page);
    builder = add(builder, "hitsPerPage", hitsPerPage);
    builder = add(builder, "offset", offset);
    builder = add(builder, "length", length);

    /* performance */
    //Nothing in Query

    /* query strategy */
    builder = add(builder, "queryType", queryType);
    builder = add(builder, "removeWordsIfNoResults", removeWordsIfNoResults);
    builder = add(builder, "advancedSyntax", advancedSyntax);
    builder = add(builder, "optionalWords", optionalWords);
    builder = add(builder, "removeStopWords", removeStopWords);
    builder = add(builder, "disableExactOnAttributes", disableExactOnAttributes);
    builder = add(builder, "exactOnSingleWordQuery", exactOnSingleWordQuery);
    builder = add(builder, "alternativesAsExact", alternativesAsExact);

    /* ranking */
    //Nothing in Query

    /* search */
    builder = add(builder, "query", query);

    /* typos */
    builder = add(builder, "minWordSizefor1Typo", minWordSizefor1Typo);
    builder = add(builder, "minWordSizefor2Typos", minWordSizefor2Typos);
    builder = add(builder, "typoTolerance", typoTolerance);
    builder = add(builder, "allowTyposOnNumericTokens", allowTyposOnNumericTokens);
    builder = add(builder, "ignorePlurals", ignorePlurals);
    builder = add(builder, "disableTypoToleranceOnAttributes", disableTypoToleranceOnAttributes);

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
        if (!firstOne) {
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

  public Query setAroundRadius(Integer aroundRadius) {
    this.aroundRadius = aroundRadius;
    return this;
  }

  public Query setAroundRadiusAll() {
    this.aroundRadius = "all";
    return this;
  }

  public Query addCustomParameter(String key, String value) {
    this.customParameters.put(key, value);
    return this;
  }

  public Query setDistinct(Distinct distinct) {
    this.distinct = distinct;
    return this;
  }

  public Query setGetRankingInfo(Boolean getRankingInfo) {
    this.getRankingInfo = getRankingInfo;
    return this;
  }

  public Query setNumericFilters(List<String> numericFilters) {
    this.numericFilters = numericFilters;
    return this;
  }

  public Query setTagFilters(List<String> tagFilters) {
    this.tagFilters = tagFilters;
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

  public Query setMinProximity(Integer minProximity) {
    this.minProximity = minProximity;
    return this;
  }

  public Query setResponseFields(List<String> responseFields) {
    this.responseFields = responseFields;
    return this;
  }

  public Query setMaxFacetHits(Integer maxFacetHits) {
    this.maxFacetHits = maxFacetHits;
    return this;
  }

  public Query setAttributesToRetrieve(List<String> attributesToRetrieve) {
    this.attributesToRetrieve = attributesToRetrieve;
    return this;
  }

  public Query setRestrictSearchableAttributes(List<String> restrictSearchableAttributes) {
    this.restrictSearchableAttributes = restrictSearchableAttributes;
    return this;
  }

  public Query setFilters(String filters) {
    this.filters = filters;
    return this;
  }

  public Query setFacets(String facets) {
    this.facets = facets;
    return this;
  }

  public Query setMaxValuesPerFacet(Integer maxValuesPerFacet) {
    this.maxValuesPerFacet = maxValuesPerFacet;
    return this;
  }

  public Query setFacetFilters(List<String> facetFilters) {
    this.facetFilters = facetFilters;
    return this;
  }

  public Query setFacetingAfterDistinct(Boolean facetingAfterDistinct) {
    this.facetingAfterDistinct = facetingAfterDistinct;
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

  public Query setAroundRadius(Object aroundRadius) {
    this.aroundRadius = aroundRadius;
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

  public Query setInsideBoundingBox(List<String> insideBoundingBox) {
    this.insideBoundingBox = insideBoundingBox;
    return this;
  }

  public Query setInsidePolygon(List<String> insidePolygon) {
    this.insidePolygon = insidePolygon;
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

  public Query setRestrictHighlightAndSnippetArrays(Boolean restrictHighlightAndSnippetArrays) {
    this.restrictHighlightAndSnippetArrays = restrictHighlightAndSnippetArrays;
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

  public Query setOffset(Integer offset) {
    this.offset = offset;
    return this;
  }

  public Query setLength(Integer length) {
    this.length = length;
    return this;
  }

  public Query setQueryType(String queryType) {
    this.queryType = queryType;
    return this;
  }

  public Query setRemoveWordsIfNoResults(RemoveWordsType removeWordsIfNoResults) {
    this.removeWordsIfNoResults = removeWordsIfNoResults;
    return this;
  }

  public Query setAdvancedSyntax(Boolean advancedSyntax) {
    this.advancedSyntax = advancedSyntax;
    return this;
  }

  public Query setOptionalWords(List<String> optionalWords) {
    this.optionalWords = optionalWords;
    return this;
  }

  public Query setRemoveStopWords(RemoveStopWords removeStopWords) {
    this.removeStopWords = removeStopWords;
    return this;
  }

  public Query setDisableExactOnAttributes(List<String> disableExactOnAttributes) {
    this.disableExactOnAttributes = disableExactOnAttributes;
    return this;
  }

  public Query setExactOnSingleWordQuery(String exactOnSingleWordQuery) {
    this.exactOnSingleWordQuery = exactOnSingleWordQuery;
    return this;
  }

  public Query setAlternativesAsExact(List<String> alternativesAsExact) {
    this.alternativesAsExact = alternativesAsExact;
    return this;
  }

  public Query setQuery(String query) {
    this.query = query;
    return this;
  }

  public Query setMinWordSizefor1Typo(Integer minWordSizefor1Typo) {
    this.minWordSizefor1Typo = minWordSizefor1Typo;
    return this;
  }

  public Query setMinWordSizefor2Typos(Integer minWordSizefor2Typos) {
    this.minWordSizefor2Typos = minWordSizefor2Typos;
    return this;
  }

  public Query setTypoTolerance(TypoTolerance typoTolerance) {
    this.typoTolerance = typoTolerance;
    return this;
  }

  public Query setAllowTyposOnNumericTokens(Boolean allowTyposOnNumericTokens) {
    this.allowTyposOnNumericTokens = allowTyposOnNumericTokens;
    return this;
  }

  public Query setIgnorePlurals(IgnorePlurals ignorePlurals) {
    this.ignorePlurals = ignorePlurals;
    return this;
  }

  public Query setDisableTypoToleranceOnAttributes(List<String> disableTypoToleranceOnAttributes) {
    this.disableTypoToleranceOnAttributes = disableTypoToleranceOnAttributes;
    return this;
  }

  public Query setUserToken(String userToken) {
    this.userToken = userToken;
    return this;
  }

  public Query setValidUntil(Integer validUntil) {
    this.validUntil = validUntil;
    return this;
  }

  public Query setRestrictIndices(List<String> restrictIndices) {
    this.restrictIndices = restrictIndices;
    return this;
  }

  public Query setRestrictSources(String restrictSources) {
    this.restrictSources = restrictSources;
    return this;
  }

  public Query setCursor(String cursor) {
    this.cursor = cursor;
    return this;
  }

  public Query setPercentileComputation(Boolean percentileComputation) {
    this.percentileComputation = percentileComputation;
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
