package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.*;
import com.google.common.collect.ImmutableMap;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QueryBase contains all the members of a Query and their associated getters and setters used for
 * Jackson to serialize/deserialize. Note that all those parameters can also be set for the
 * ConsequenceParams (used in the Query Rules context). This is why both Query and ConsequenceParams
 * concrete classes inherit from QueryBase. The T parametric type is used to let us use chaining for
 * the builder pattern i.e. making sure that we can write for instance:
 *
 * <pre>
 *   Query q = new Query().setFilters(...); ConsequenceParams q = new
 *   ConsequenceParams().setFilters(...);
 * </pre>
 *
 * without having to rewrite the setters in both concrete classes even though their returned type is
 * different.
 *
 * @param <T>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class QueryBase<T extends QueryBase<?>> implements Serializable {

  /* advanced */
  protected Distinct distinct;
  protected Boolean getRankingInfo;
  protected List<String> numericFilters;
  protected List<String> tagFilters;
  protected Boolean clickAnalytics;
  protected Boolean analytics;
  protected String analyticsTags;
  protected Boolean synonyms;
  protected Boolean replaceSynonymsInHighlight;
  protected Integer minProximity;
  protected List<String> responseFields;
  protected Long maxFacetHits;
  protected Boolean percentileComputation;
  protected List<String> queryLanguages;

  /* attributes */
  protected List<String> attributesToRetrieve;
  protected List<String> restrictSearchableAttributes;

  /* filtering-faceting */
  protected String filters;
  protected String facets;
  protected Long maxValuesPerFacet;
  protected FacetFilters facetFilters;
  protected List<List<String>> optionalFilters;
  protected Boolean facetingAfterDistinct;
  protected String sortFacetValuesBy;

  /* geo-search */
  protected String aroundLatLng;
  protected Boolean aroundLatLngViaIP;
  protected AroundRadius aroundRadius;
  protected Integer aroundPrecision;
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
  protected Long page;
  protected Long hitsPerPage;
  protected Long offset;
  protected Long length;

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

  /* ranking */
  // Nothing in Query

  /* search */
  @JsonProperty("query")
  protected String query;

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

  /* BROWSE */
  protected String cursor;

  /* Personalization */
  protected Boolean enablePersonalization;

  /* CUSTOM */
  @JsonIgnore protected Map<String, Object> customParameters = new HashMap<>();

  private Map<String, String> toQueryParam() {
    ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

    /* advanced */
    if (distinct != null) {
      builder = add(builder, "distinct", distinct.getInsideValue());
    }
    builder = add(builder, "getRankingInfo", getRankingInfo);
    builder = add(builder, "numericFilters", numericFilters);
    builder = add(builder, "tagFilters", tagFilters);
    builder = add(builder, "analytics", analytics);
    builder = add(builder, "analyticsTags", analyticsTags);
    builder = add(builder, "clickAnalytics", clickAnalytics);
    builder = add(builder, "synonyms", synonyms);
    builder = add(builder, "replaceSynonymsInHighlight", replaceSynonymsInHighlight);
    builder = add(builder, "minProximity", minProximity);
    builder = add(builder, "responseFields", responseFields);
    builder = add(builder, "maxFacetHits", maxFacetHits);
    builder = add(builder, "percentileComputation", percentileComputation);
    builder = add(builder, "queryLanguages", queryLanguages);

    /* attributes */
    builder = add(builder, "attributesToRetrieve", attributesToRetrieve);
    builder = add(builder, "restrictSearchableAttributes", restrictSearchableAttributes);

    /* filtering-faceting */
    builder = add(builder, "filters", filters);
    builder = add(builder, "facets", facets);
    builder = add(builder, "maxValuesPerFacet", maxValuesPerFacet);
    builder = add(builder, "facetFilters", facetFilters);
    builder = add(builder, "facetingAfterDistinct", facetingAfterDistinct);
    builder = add(builder, "sortFacetValuesBy", sortFacetValuesBy);

    /* geo-search */
    builder = add(builder, "aroundLatLng", aroundLatLng);
    builder = add(builder, "aroundLatLngViaIP", aroundLatLngViaIP);
    builder = add(builder, "aroundRadius", aroundRadius);
    builder = add(builder, "aroundPrecision", aroundPrecision);
    builder = add(builder, "minimumAroundRadius", minimumAroundRadius);

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
    // Nothing in Query

    /* query rules */
    builder = add(builder, "enableRules", enableRules);
    builder = add(builder, "ruleContexts", ruleContexts);

    /* query strategy */
    builder = add(builder, "queryType", queryType);
    builder = add(builder, "removeWordsIfNoResults", removeWordsIfNoResults);
    builder = add(builder, "advancedSyntax", advancedSyntax);
    builder = add(builder, "advancedSyntaxFeatures", advancedSyntaxFeatures);
    builder = add(builder, "optionalWords", optionalWords);
    if (removeStopWords != null) {
      builder = add(builder, "removeStopWords", removeStopWords.getInsideValue());
    }
    builder = add(builder, "disableExactOnAttributes", disableExactOnAttributes);
    builder = add(builder, "exactOnSingleWordQuery", exactOnSingleWordQuery);
    builder = add(builder, "alternativesAsExact", alternativesAsExact);

    /* ranking */
    // Nothing in Query

    /* search */
    builder = add(builder, "query", query);

    /* typos */
    builder = add(builder, "minWordSizefor1Typo", minWordSizefor1Typo);
    builder = add(builder, "minWordSizefor2Typos", minWordSizefor2Typos);
    builder = add(builder, "typoTolerance", typoTolerance);
    builder = add(builder, "allowTyposOnNumericTokens", allowTyposOnNumericTokens);
    if (ignorePlurals != null) {
      builder = add(builder, "ignorePlurals", ignorePlurals.getInsideValue());
    }
    builder = add(builder, "disableTypoToleranceOnAttributes", disableTypoToleranceOnAttributes);

    /* SECURED API KEYS */
    builder = add(builder, "userToken", userToken);
    builder = add(builder, "validUntil", validUntil);
    builder = add(builder, "restrictIndices", restrictIndices);
    builder = add(builder, "restrictSources", restrictSources);

    /* BROWSE */
    builder = add(builder, "cursor", cursor);

    /* Personalization */
    builder = add(builder, "enablePersonalization", enablePersonalization);

    /* CUSTOM */
    for (Map.Entry<String, Object> entry : customParameters.entrySet()) {
      builder = add(builder, entry.getKey(), entry.getValue());
    }

    return builder.build();
  }

  private ImmutableMap.Builder<String, String> add(
      ImmutableMap.Builder<String, String> builder, String name, Object value) {
    if (value == null) {
      return builder;
    }
    return builder.put(name, value.toString());
  }

  private ImmutableMap.Builder<String, String> add(
      ImmutableMap.Builder<String, String> builder, String name, CompoundType value) {
    if (value == null) {
      return builder;
    }
    return builder.put(name, value.getInsideValue().toString());
  }

  private ImmutableMap.Builder<String, String> add(
      ImmutableMap.Builder<String, String> builder, String name, Enum<?> value) {
    if (value == null) {
      return builder;
    }
    return builder.put(name, value.toString());
  }

  private ImmutableMap.Builder<String, String> add(
      ImmutableMap.Builder<String, String> builder, String name, String value) {
    if (value == null) {
      return builder;
    }
    return builder.put(name, value);
  }

  private ImmutableMap.Builder<String, String> add(
      ImmutableMap.Builder<String, String> builder, String name, Boolean value) {
    if (value == null) {
      return builder;
    }
    return builder.put(name, value.toString());
  }

  private ImmutableMap.Builder<String, String> add(
      ImmutableMap.Builder<String, String> builder, String name, Integer value) {
    if (value == null) {
      return builder;
    }
    return builder.put(name, value.toString());
  }

  private ImmutableMap.Builder<String, String> add(
      ImmutableMap.Builder<String, String> builder, String name, List<String> attributes) {
    if (attributes == null) {
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

        builder =
            builder
                .append(entry.getKey())
                .append("=")
                .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
      } catch (UnsupportedEncodingException ignore) {
      }
      firstOne = false;
    }

    return builder.toString();
  }

  public T setAroundRadius(AroundRadius aroundRadius) {
    this.aroundRadius = aroundRadius;
    return (T) this;
  }

  @JsonAnySetter
  public T addCustomParameter(String key, String value) {
    this.customParameters.put(key, value);
    return (T) this;
  }

  public Distinct getDistinct() {
    return distinct;
  }

  public T setDistinct(Distinct distinct) {
    this.distinct = distinct;
    return (T) this;
  }

  public Boolean getGetRankingInfo() {
    return getRankingInfo;
  }

  public T setGetRankingInfo(Boolean getRankingInfo) {
    this.getRankingInfo = getRankingInfo;
    return (T) this;
  }

  public List<String> getNumericFilters() {
    return numericFilters;
  }

  public T setNumericFilters(List<String> numericFilters) {
    this.numericFilters = numericFilters;
    return (T) this;
  }

  public List<String> getTagFilters() {
    return tagFilters;
  }

  public T setTagFilters(List<String> tagFilters) {
    this.tagFilters = tagFilters;
    return (T) this;
  }

  public Boolean getClickAnalytics() {
    return clickAnalytics;
  }

  public T setClickAnalytics(Boolean clickAnalytics) {
    this.clickAnalytics = clickAnalytics;
    return (T) this;
  }

  public Boolean getAnalytics() {
    return analytics;
  }

  public T setAnalytics(Boolean analytics) {
    this.analytics = analytics;
    return (T) this;
  }

  public String getAnalyticsTags() {
    return analyticsTags;
  }

  public T setAnalyticsTags(String analyticsTags) {
    this.analyticsTags = analyticsTags;
    return (T) this;
  }

  public Boolean getSynonyms() {
    return synonyms;
  }

  public T setSynonyms(Boolean synonyms) {
    this.synonyms = synonyms;
    return (T) this;
  }

  public Boolean getReplaceSynonymsInHighlight() {
    return replaceSynonymsInHighlight;
  }

  public T setReplaceSynonymsInHighlight(Boolean replaceSynonymsInHighlight) {
    this.replaceSynonymsInHighlight = replaceSynonymsInHighlight;
    return (T) this;
  }

  public Integer getMinProximity() {
    return minProximity;
  }

  public T setMinProximity(Integer minProximity) {
    this.minProximity = minProximity;
    return (T) this;
  }

  public List<String> getResponseFields() {
    return responseFields;
  }

  public T setResponseFields(List<String> responseFields) {
    this.responseFields = responseFields;
    return (T) this;
  }

  public Long getMaxFacetHits() {
    return maxFacetHits;
  }

  @JsonSetter
  public T setMaxFacetHits(Long maxFacetHits) {
    this.maxFacetHits = maxFacetHits;
    return (T) this;
  }

  public T setMaxFacetHits(Integer maxFacetHits) {
    return (T) this.setMaxFacetHits(maxFacetHits.longValue());
  }

  public Boolean getPercentileComputation() {
    return percentileComputation;
  }

  public T setPercentileComputation(Boolean percentileComputation) {
    this.percentileComputation = percentileComputation;
    return (T) this;
  }

  public List<String> getQueryLanguages() {
    return queryLanguages;
  }

  public T setQueryLanguages(List<String> queryLanguages) {
    this.queryLanguages = queryLanguages;
    return (T) this;
  }

  public List<String> getAttributesToRetrieve() {
    return attributesToRetrieve;
  }

  public T setAttributesToRetrieve(List<String> attributesToRetrieve) {
    this.attributesToRetrieve = attributesToRetrieve;
    return (T) this;
  }

  public List<String> getRestrictSearchableAttributes() {
    return restrictSearchableAttributes;
  }

  public T setRestrictSearchableAttributes(List<String> restrictSearchableAttributes) {
    this.restrictSearchableAttributes = restrictSearchableAttributes;
    return (T) this;
  }

  public String getFilters() {
    return filters;
  }

  public T setFilters(String filters) {
    this.filters = filters;
    return (T) this;
  }

  public String getFacets() {
    return facets;
  }

  public T setFacets(String facets) {
    this.facets = facets;
    return (T) this;
  }

  public Long getMaxValuesPerFacet() {
    return maxValuesPerFacet;
  }

  @JsonSetter
  public T setMaxValuesPerFacet(Long maxValuesPerFacet) {
    this.maxValuesPerFacet = maxValuesPerFacet;
    return (T) this;
  }

  public T setMaxValuesPerFacet(Integer maxValuesPerFacet) {
    return (T) this.setMaxValuesPerFacet(maxValuesPerFacet.longValue());
  }

  public FacetFilters getFacetFilters() {
    return facetFilters;
  }

  @Deprecated
  public T setFacetFilters(List<String> facetFilters) {
    this.facetFilters = new FacetFiltersAsListOfString(facetFilters);
    return (T) this;
  }

  @JsonSetter
  public T setFacetFilters(FacetFilters facetFilters) {
    this.facetFilters = facetFilters;
    return (T) this;
  }

  public List<List<String>> getOptionalFilters() {
    return optionalFilters;
  }

  public T setOptionalFilters(List<List<String>> optionalFilters) {
    this.optionalFilters = optionalFilters;
    return (T) this;
  }

  public Boolean getFacetingAfterDistinct() {
    return facetingAfterDistinct;
  }

  public T setFacetingAfterDistinct(Boolean facetingAfterDistinct) {
    this.facetingAfterDistinct = facetingAfterDistinct;
    return (T) this;
  }

  public String getAroundLatLng() {
    return aroundLatLng;
  }

  public T setAroundLatLng(String aroundLatLng) {
    this.aroundLatLng = aroundLatLng;
    return (T) this;
  }

  public Boolean getAroundLatLngViaIP() {
    return aroundLatLngViaIP;
  }

  public T setAroundLatLngViaIP(Boolean aroundLatLngViaIP) {
    this.aroundLatLngViaIP = aroundLatLngViaIP;
    return (T) this;
  }

  public AroundRadius getAroundRadius() {
    return aroundRadius;
  }

  public Integer getAroundPrecision() {
    return aroundPrecision;
  }

  public T setAroundPrecision(Integer aroundPrecision) {
    this.aroundPrecision = aroundPrecision;
    return (T) this;
  }

  public Integer getMinimumAroundRadius() {
    return minimumAroundRadius;
  }

  public T setMinimumAroundRadius(Integer minimumAroundRadius) {
    this.minimumAroundRadius = minimumAroundRadius;
    return (T) this;
  }

  public List<List<Float>> getInsideBoundingBox() {
    return insideBoundingBox;
  }

  public T setInsideBoundingBox(List<List<Float>> insideBoundingBox) {
    this.insideBoundingBox = insideBoundingBox;
    return (T) this;
  }

  public List<List<Float>> getInsidePolygon() {
    return insidePolygon;
  }

  public T setInsidePolygon(List<List<Float>> insidePolygon) {
    this.insidePolygon = insidePolygon;
    return (T) this;
  }

  public List<String> getAttributesToHighlight() {
    return attributesToHighlight;
  }

  public T setAttributesToHighlight(List<String> attributesToHighlight) {
    this.attributesToHighlight = attributesToHighlight;
    return (T) this;
  }

  public List<String> getAttributesToSnippet() {
    return attributesToSnippet;
  }

  public T setAttributesToSnippet(List<String> attributesToSnippet) {
    this.attributesToSnippet = attributesToSnippet;
    return (T) this;
  }

  public String getHighlightPreTag() {
    return highlightPreTag;
  }

  public T setHighlightPreTag(String highlightPreTag) {
    this.highlightPreTag = highlightPreTag;
    return (T) this;
  }

  public String getHighlightPostTag() {
    return highlightPostTag;
  }

  public T setHighlightPostTag(String highlightPostTag) {
    this.highlightPostTag = highlightPostTag;
    return (T) this;
  }

  public String getSnippetEllipsisText() {
    return snippetEllipsisText;
  }

  public T setSnippetEllipsisText(String snippetEllipsisText) {
    this.snippetEllipsisText = snippetEllipsisText;
    return (T) this;
  }

  public Boolean getRestrictHighlightAndSnippetArrays() {
    return restrictHighlightAndSnippetArrays;
  }

  public T setRestrictHighlightAndSnippetArrays(Boolean restrictHighlightAndSnippetArrays) {
    this.restrictHighlightAndSnippetArrays = restrictHighlightAndSnippetArrays;
    return (T) this;
  }

  public Long getPage() {
    return page;
  }

  @JsonSetter
  public T setPage(Long page) {
    this.page = page;
    return (T) this;
  }

  public T setPage(Integer page) {
    return (T) this.setPage(page.longValue());
  }

  public Long getHitsPerPage() {
    return hitsPerPage;
  }

  @JsonSetter
  public T setHitsPerPage(Long hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return (T) this;
  }

  public T setHitsPerPage(Integer hitsPerPage) {
    return (T) this.setHitsPerPage(hitsPerPage.longValue());
  }

  public Long getOffset() {
    return offset;
  }

  @JsonSetter
  public T setOffset(Long offset) {
    this.offset = offset;
    return (T) this;
  }

  public T setOffset(Integer offset) {
    return (T) this.setOffset(offset.longValue());
  }

  public Long getLength() {
    return length;
  }

  @JsonSetter
  public T setLength(Long length) {
    this.length = length;
    return (T) this;
  }

  public T setLength(Integer length) {
    return (T) this.setLength(length.longValue());
  }

  public String getQueryType() {
    return queryType;
  }

  public T setQueryType(String queryType) {
    this.queryType = queryType;
    return (T) this;
  }

  public RemoveWordsType getRemoveWordsIfNoResults() {
    return removeWordsIfNoResults;
  }

  public T setRemoveWordsIfNoResults(RemoveWordsType removeWordsIfNoResults) {
    this.removeWordsIfNoResults = removeWordsIfNoResults;
    return (T) this;
  }

  public Boolean getAdvancedSyntax() {
    return advancedSyntax;
  }

  public T setAdvancedSyntax(Boolean advancedSyntax) {
    this.advancedSyntax = advancedSyntax;
    return (T) this;
  }

  public List<String> getAdvancedSyntaxFeatures() {
    return advancedSyntaxFeatures;
  }

  public T setAdvancedSyntaxFeatures(List<String> advancedSyntaxFeatures) {
    this.advancedSyntaxFeatures = advancedSyntaxFeatures;
    return (T) this;
  }

  public List<String> getOptionalWords() {
    return optionalWords;
  }

  public T setOptionalWords(List<String> optionalWords) {
    this.optionalWords = optionalWords;
    return (T) this;
  }

  public RemoveStopWords getRemoveStopWords() {
    return removeStopWords;
  }

  public T setRemoveStopWords(RemoveStopWords removeStopWords) {
    this.removeStopWords = removeStopWords;
    return (T) this;
  }

  public List<String> getDisableExactOnAttributes() {
    return disableExactOnAttributes;
  }

  public T setDisableExactOnAttributes(List<String> disableExactOnAttributes) {
    this.disableExactOnAttributes = disableExactOnAttributes;
    return (T) this;
  }

  public String getExactOnSingleWordQuery() {
    return exactOnSingleWordQuery;
  }

  public T setExactOnSingleWordQuery(String exactOnSingleWordQuery) {
    this.exactOnSingleWordQuery = exactOnSingleWordQuery;
    return (T) this;
  }

  public List<String> getAlternativesAsExact() {
    return alternativesAsExact;
  }

  public T setAlternativesAsExact(List<String> alternativesAsExact) {
    this.alternativesAsExact = alternativesAsExact;
    return (T) this;
  }

  public Integer getMinWordSizefor1Typo() {
    return minWordSizefor1Typo;
  }

  public T setMinWordSizefor1Typo(Integer minWordSizefor1Typo) {
    this.minWordSizefor1Typo = minWordSizefor1Typo;
    return (T) this;
  }

  public Integer getMinWordSizefor2Typos() {
    return minWordSizefor2Typos;
  }

  public T setMinWordSizefor2Typos(Integer minWordSizefor2Typos) {
    this.minWordSizefor2Typos = minWordSizefor2Typos;
    return (T) this;
  }

  public TypoTolerance getTypoTolerance() {
    return typoTolerance;
  }

  public T setTypoTolerance(TypoTolerance typoTolerance) {
    this.typoTolerance = typoTolerance;
    return (T) this;
  }

  public Boolean getAllowTyposOnNumericTokens() {
    return allowTyposOnNumericTokens;
  }

  public T setAllowTyposOnNumericTokens(Boolean allowTyposOnNumericTokens) {
    this.allowTyposOnNumericTokens = allowTyposOnNumericTokens;
    return (T) this;
  }

  public IgnorePlurals getIgnorePlurals() {
    return ignorePlurals;
  }

  public T setIgnorePlurals(IgnorePlurals ignorePlurals) {
    this.ignorePlurals = ignorePlurals;
    return (T) this;
  }

  public List<String> getDisableTypoToleranceOnAttributes() {
    return disableTypoToleranceOnAttributes;
  }

  public T setDisableTypoToleranceOnAttributes(List<String> disableTypoToleranceOnAttributes) {
    this.disableTypoToleranceOnAttributes = disableTypoToleranceOnAttributes;
    return (T) this;
  }

  public String getUserToken() {
    return userToken;
  }

  public T setUserToken(String userToken) {
    this.userToken = userToken;
    return (T) this;
  }

  public Integer getValidUntil() {
    return validUntil;
  }

  public T setValidUntil(Integer validUntil) {
    this.validUntil = validUntil;
    return (T) this;
  }

  public List<String> getRestrictIndices() {
    return restrictIndices;
  }

  public T setRestrictIndices(List<String> restrictIndices) {
    this.restrictIndices = restrictIndices;
    return (T) this;
  }

  public String getRestrictSources() {
    return restrictSources;
  }

  public T setRestrictSources(String restrictSources) {
    this.restrictSources = restrictSources;
    return (T) this;
  }

  public String getCursor() {
    return cursor;
  }

  public T setCursor(String cursor) {
    this.cursor = cursor;
    return (T) this;
  }

  public Boolean getEnableRules() {
    return enableRules;
  }

  public T setEnableRules(Boolean enableRules) {
    this.enableRules = enableRules;
    return (T) this;
  }

  public List<String> getRuleContexts() {
    return ruleContexts;
  }

  public Boolean getEnablePersonalization() {
    return enablePersonalization;
  }

  public T setEnablePersonalization(Boolean enablePersonalization) {
    this.enablePersonalization = enablePersonalization;
    return (T) this;
  }

  public T setRuleContexts(List<String> ruleContexts) {
    this.ruleContexts = ruleContexts;
    return (T) this;
  }

  public T setQuery(String query) {
    this.query = query;
    return (T) this;
  }

  public String getSortFacetValuesBy() {
    return sortFacetValuesBy;
  }

  public T setSortFacetValuesBy(String sortFacetValuesBy) {
    this.sortFacetValuesBy = sortFacetValuesBy;
    return (T) this;
  }

  @JsonAnyGetter
  public Map<String, Object> getCustomParameters() {
    return customParameters;
  }

  @JsonAnySetter
  public T setCustomParameters(Map<String, Object> customParameters) {
    this.customParameters = customParameters;
    return (T) this;
  }

  @Override
  public String toString() {
    return "Query{"
        + "distinct="
        + distinct
        + ", getRankingInfo="
        + getRankingInfo
        + ", numericFilters="
        + numericFilters
        + ", tagFilters="
        + tagFilters
        + ", analytics="
        + analytics
        + ", analyticsTags='"
        + analyticsTags
        + '\''
        + ", synonyms="
        + synonyms
        + ", replaceSynonymsInHighlight="
        + replaceSynonymsInHighlight
        + ", minProximity="
        + minProximity
        + ", responseFields="
        + responseFields
        + ", maxFacetHits="
        + maxFacetHits
        + ", percentileComputation="
        + percentileComputation
        + ", queryLanguages="
        + queryLanguages
        + ", attributesToRetrieve="
        + attributesToRetrieve
        + ", restrictSearchableAttributes="
        + restrictSearchableAttributes
        + ", filters='"
        + filters
        + '\''
        + ", facets='"
        + facets
        + '\''
        + ", maxValuesPerFacet="
        + maxValuesPerFacet
        + ", facetFilters="
        + facetFilters
        + ", facetingAfterDistinct="
        + facetingAfterDistinct
        + ", sortFacetValuesBy"
        + sortFacetValuesBy
        + ", aroundLatLng='"
        + aroundLatLng
        + '\''
        + ", aroundLatLngViaIP="
        + aroundLatLngViaIP
        + ", aroundRadius="
        + aroundRadius
        + ", aroundPrecision="
        + aroundPrecision
        + ", minimumAroundRadius="
        + minimumAroundRadius
        + ", insideBoundingBox="
        + insideBoundingBox
        + ", insidePolygon="
        + insidePolygon
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
        + ", page="
        + page
        + ", hitsPerPage="
        + hitsPerPage
        + ", offset="
        + offset
        + ", length="
        + length
        + ", enableRules"
        + enableRules
        + ", ruleContexts"
        + ruleContexts
        + ", queryType='"
        + queryType
        + '\''
        + ", removeWordsIfNoResults="
        + removeWordsIfNoResults
        + ", advancedSyntax="
        + advancedSyntax
        + ", advancedSyntaxFeatures="
        + advancedSyntaxFeatures
        + ", optionalWords="
        + optionalWords
        + ", removeStopWords="
        + removeStopWords
        + ", disableExactOnAttributes="
        + disableExactOnAttributes
        + ", exactOnSingleWordQuery='"
        + exactOnSingleWordQuery
        + '\''
        + ", alternativesAsExact="
        + alternativesAsExact
        + ", query='"
        + query
        + '\''
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
        + ", userToken='"
        + userToken
        + '\''
        + ", validUntil="
        + validUntil
        + ", restrictIndices="
        + restrictIndices
        + ", restrictSources='"
        + restrictSources
        + '\''
        + ", cursor='"
        + cursor
        + '\''
        + ", customParameters="
        + customParameters
        + '}';
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
}
