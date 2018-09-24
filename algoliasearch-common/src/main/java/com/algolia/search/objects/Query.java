package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.*;
import com.google.common.collect.ImmutableMap;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Query implements Serializable {

  /* advanced */
  protected Distinct distinct;
  protected Boolean getRankingInfo;
  protected List<String> numericFilters;
  protected List<String> tagFilters;
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
  protected Boolean facetingAfterDistinct;
  protected String sortFacetValuesBy;

  /* geo-search */
  protected String aroundLatLng;
  protected Boolean aroundLatLngViaIP;
  protected AroundRadius aroundRadius;
  protected Integer aroundPrecision;
  protected Integer minimumAroundRadius;
  protected List<String> insideBoundingBox;
  protected List<String> insidePolygon;

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

  /* CUSTOM */
  @JsonIgnore protected Map<String, Object> customParameters = new HashMap<>();

  public Query() {}

  public Query(String query) {
    this.query = query;
  }

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
    // Nothing in Query

    /* query rules */
    builder = add(builder, "enableRules", enableRules);
    builder = add(builder, "ruleContexts", ruleContexts);

    /* query strategy */
    builder = add(builder, "queryType", queryType);
    builder = add(builder, "removeWordsIfNoResults", removeWordsIfNoResults);
    builder = add(builder, "advancedSyntax", advancedSyntax);
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

  public Query setAroundRadius(AroundRadius aroundRadius) {
    this.aroundRadius = aroundRadius;
    return this;
  }

  @JsonAnySetter
  public Query addCustomParameter(String key, String value) {
    this.customParameters.put(key, value);
    return this;
  }

  public Distinct getDistinct() {
    return distinct;
  }

  public Query setDistinct(Distinct distinct) {
    this.distinct = distinct;
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

  @JsonSetter
  public Query setMaxFacetHits(Long maxFacetHits) {
    this.maxFacetHits = maxFacetHits;
    return this;
  }

  public Query setMaxFacetHits(Integer maxFacetHits) {
    return this.setMaxFacetHits(maxFacetHits.longValue());
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

  public String getFacets() {
    return facets;
  }

  public Query setFacets(String facets) {
    this.facets = facets;
    return this;
  }

  public Long getMaxValuesPerFacet() {
    return maxValuesPerFacet;
  }

  @JsonSetter
  public Query setMaxValuesPerFacet(Long maxValuesPerFacet) {
    this.maxValuesPerFacet = maxValuesPerFacet;
    return this;
  }

  public Query setMaxValuesPerFacet(Integer maxValuesPerFacet) {
    return this.setMaxValuesPerFacet(maxValuesPerFacet.longValue());
  }

  public FacetFilters getFacetFilters() {
    return facetFilters;
  }

  @Deprecated
  public Query setFacetFilters(List<String> facetFilters) {
    this.facetFilters = new FacetFiltersAsListOfString(facetFilters);
    return this;
  }

  @JsonSetter
  public Query setFacetFilters(FacetFilters facetFilters) {
    this.facetFilters = facetFilters;
    return this;
  }

  public Boolean getFacetingAfterDistinct() {
    return facetingAfterDistinct;
  }

  public Query setFacetingAfterDistinct(Boolean facetingAfterDistinct) {
    this.facetingAfterDistinct = facetingAfterDistinct;
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

  public AroundRadius getAroundRadius() {
    return aroundRadius;
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

  public List<String> getInsideBoundingBox() {
    return insideBoundingBox;
  }

  public Query setInsideBoundingBox(List<String> insideBoundingBox) {
    this.insideBoundingBox = insideBoundingBox;
    return this;
  }

  public List<String> getInsidePolygon() {
    return insidePolygon;
  }

  public Query setInsidePolygon(List<String> insidePolygon) {
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

  public Long getPage() {
    return page;
  }

  @JsonSetter
  public Query setPage(Long page) {
    this.page = page;
    return this;
  }

  public Query setPage(Integer page) {
    return this.setPage(page.longValue());
  }

  public Long getHitsPerPage() {
    return hitsPerPage;
  }

  @JsonSetter
  public Query setHitsPerPage(Long hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  public Query setHitsPerPage(Integer hitsPerPage) {
    return this.setHitsPerPage(hitsPerPage.longValue());
  }

  public Long getOffset() {
    return offset;
  }

  @JsonSetter
  public Query setOffset(Long offset) {
    this.offset = offset;
    return this;
  }

  public Query setOffset(Integer offset) {
    return this.setOffset(offset.longValue());
  }

  public Long getLength() {
    return length;
  }

  @JsonSetter
  public Query setLength(Long length) {
    this.length = length;
    return this;
  }

  public Query setLength(Integer length) {
    return this.setLength(length.longValue());
  }

  public String getQueryType() {
    return queryType;
  }

  public Query setQueryType(String queryType) {
    this.queryType = queryType;
    return this;
  }

  public RemoveWordsType getRemoveWordsIfNoResults() {
    return removeWordsIfNoResults;
  }

  public Query setRemoveWordsIfNoResults(RemoveWordsType removeWordsIfNoResults) {
    this.removeWordsIfNoResults = removeWordsIfNoResults;
    return this;
  }

  public Boolean getAdvancedSyntax() {
    return advancedSyntax;
  }

  public Query setAdvancedSyntax(Boolean advancedSyntax) {
    this.advancedSyntax = advancedSyntax;
    return this;
  }

  public List<String> getOptionalWords() {
    return optionalWords;
  }

  public Query setOptionalWords(List<String> optionalWords) {
    this.optionalWords = optionalWords;
    return this;
  }

  public RemoveStopWords getRemoveStopWords() {
    return removeStopWords;
  }

  public Query setRemoveStopWords(RemoveStopWords removeStopWords) {
    this.removeStopWords = removeStopWords;
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

  public TypoTolerance getTypoTolerance() {
    return typoTolerance;
  }

  public Query setTypoTolerance(TypoTolerance typoTolerance) {
    this.typoTolerance = typoTolerance;
    return this;
  }

  public Boolean getAllowTyposOnNumericTokens() {
    return allowTyposOnNumericTokens;
  }

  public Query setAllowTyposOnNumericTokens(Boolean allowTyposOnNumericTokens) {
    this.allowTyposOnNumericTokens = allowTyposOnNumericTokens;
    return this;
  }

  public IgnorePlurals getIgnorePlurals() {
    return ignorePlurals;
  }

  public Query setIgnorePlurals(IgnorePlurals ignorePlurals) {
    this.ignorePlurals = ignorePlurals;
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

  public String getCursor() {
    return cursor;
  }

  public Query setCursor(String cursor) {
    this.cursor = cursor;
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

  public Query setQuery(String query) {
    this.query = query;
    return this;
  }

  public String getSortFacetValuesBy() {
    return sortFacetValuesBy;
  }

  public Query setSortFacetValuesBy(String sortFacetValuesBy) {
    this.sortFacetValuesBy = sortFacetValuesBy;
    return this;
  }

  @JsonAnyGetter
  public Map<String, Object> getCustomParameters() {
    return customParameters;
  }

  @JsonAnySetter
  public Query setCustomParameters(Map<String, Object> customParameters) {
    this.customParameters = customParameters;
    return this;
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
