package com.algolia.search.models.indexing;

import com.algolia.search.util.AlgoliaUtils;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SearchResult<T> implements Serializable {

  private Long page;
  private Long nbHits;
  private Long nbPages;
  private Long hitsPerPage;
  private Long processingTimeMS;
  private Map<String, Map<String, Long>> facets;
  private Map<String, FacetStats> facets_stats;
  private Boolean exhaustiveFacetsCount;
  private String query;
  private String params;
  private List<T> hits;
  private String index;
  private Boolean processed;
  private String queryID;
  private Explain explain;
  private List<Map<String, Object>> userData;
  private List<Map<String, Object>> appliedRules;
  private Boolean exhaustiveNbHits;

  public Explain getExplain() {
    return explain;
  }

  public SearchResult<T> setExplain(Explain explain) {
    this.explain = explain;
    return this;
  }

  public Long getOffset() {
    return offset;
  }

  public SearchResult<T> setOffset(Long offset) {
    this.offset = offset;
    return this;
  }

  public Long getLength() {
    return length;
  }

  public SearchResult<T> setLength(Long length) {
    this.length = length;
    return this;
  }

  public String getParsedQuery() {
    return parsedQuery;
  }

  public SearchResult<T> setParsedQuery(String parsedQuery) {
    this.parsedQuery = parsedQuery;
    return this;
  }

  public Long getAbTestVariantID() {
    return abTestVariantID;
  }

  public SearchResult<T> setAbTestVariantID(Long abTestVariantID) {
    this.abTestVariantID = abTestVariantID;
    return this;
  }

  public String getIndexUsed() {
    return indexUsed;
  }

  public SearchResult<T> setIndexUsed(String indexUsed) {
    this.indexUsed = indexUsed;
    return this;
  }

  public String getServerUsed() {
    return serverUsed;
  }

  public SearchResult<T> setServerUsed(String serverUsed) {
    this.serverUsed = serverUsed;
    return this;
  }

  public String getAutomaticRadius() {
    return automaticRadius;
  }

  public SearchResult<T> setAutomaticRadius(String automaticRadius) {
    this.automaticRadius = automaticRadius;
    return this;
  }

  public String getAroundLatLng() {
    return aroundLatLng;
  }

  public SearchResult<T> setAroundLatLng(String aroundLatLng) {
    this.aroundLatLng = aroundLatLng;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public SearchResult<T> setMessage(String message) {
    this.message = message;
    return this;
  }

  public String getQueryAfterRemoval() {
    return queryAfterRemoval;
  }

  public SearchResult<T> setQueryAfterRemoval(String queryAfterRemoval) {
    this.queryAfterRemoval = queryAfterRemoval;
    return this;
  }

  private Long offset;
  private Long length;
  private String parsedQuery;
  private Long abTestVariantID;
  private String indexUsed;
  private String serverUsed;
  private String automaticRadius;
  private String aroundLatLng;
  private String message;
  private String queryAfterRemoval;

  public List<Map<String, Object>> getAppliedRules() {
    return appliedRules;
  }

  public void setAppliedRules(List<Map<String, Object>> appliedRules) {
    this.appliedRules = appliedRules;
  }

  public List<Map<String, Object>> getUserData() {
    return userData;
  }

  public void setUserData(List<Map<String, Object>> userData) {
    this.userData = userData;
  }

  public List<T> getHits() {
    return hits;
  }

  public SearchResult<T> setHits(List<T> hits) {
    this.hits = hits;
    return this;
  }

  public Long getPage() {
    return page;
  }

  public SearchResult<T> setPage(Integer page) {
    return this.setPage(page.longValue());
  }

  @JsonSetter
  public SearchResult<T> setPage(Long page) {
    this.page = page;
    return this;
  }

  public Long getNbHits() {
    return nbHits;
  }

  public SearchResult<T> setNbHits(Integer nbHits) {
    return this.setNbHits(nbHits.longValue());
  }

  @JsonSetter
  public SearchResult<T> setNbHits(Long nbHits) {
    this.nbHits = nbHits;
    return this;
  }

  public Long getNbPages() {
    return nbPages;
  }

  public SearchResult<T> setNbPages(Integer nbPages) {
    return this.setNbPages(nbPages.longValue());
  }

  @JsonSetter
  public SearchResult<T> setNbPages(Long nbPages) {
    this.nbPages = nbPages;
    return this;
  }

  public Long getHitsPerPage() {
    return hitsPerPage;
  }

  public SearchResult<T> setHitsPerPage(Integer hitsPerPage) {
    return this.setHitsPerPage(hitsPerPage.longValue());
  }

  @JsonSetter
  public SearchResult<T> setHitsPerPage(Long hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  public Long getProcessingTimeMS() {
    return processingTimeMS;
  }

  public SearchResult<T> setProcessingTimeMS(Integer processingTimeMS) {
    return this.setProcessingTimeMS(processingTimeMS.longValue());
  }

  @JsonSetter
  public SearchResult<T> setProcessingTimeMS(Long processingTimeMS) {
    this.processingTimeMS = processingTimeMS;
    return this;
  }

  public Map<String, Map<String, Long>> getFacets() {
    return facets;
  }

  public SearchResult<T> setFacets(Map<String, Map<String, Long>> facets) {
    this.facets = facets;
    return this;
  }

  public Boolean getExhaustiveFacetsCount() {
    return exhaustiveFacetsCount;
  }

  public SearchResult<T> setExhaustiveFacetsCount(Boolean exhaustiveFacetsCount) {
    this.exhaustiveFacetsCount = exhaustiveFacetsCount;
    return this;
  }

  public String getQuery() {
    return query;
  }

  public SearchResult<T> setQuery(String query) {
    this.query = query;
    return this;
  }

  public String getParams() {
    return params;
  }

  public SearchResult<T> setParams(String params) {
    this.params = params;
    return this;
  }

  public String getIndex() {
    return index;
  }

  public SearchResult<T> setIndex(String index) {
    this.index = index;
    return this;
  }

  public Boolean getProcessed() {
    return processed;
  }

  public SearchResult<T> setProcessed(Boolean processed) {
    this.processed = processed;
    return this;
  }

  public String getQueryID() {
    return queryID;
  }

  public SearchResult<T> setQueryID(final String queryID) {
    this.queryID = queryID;
    return this;
  }

  public Map<String, FacetStats> getFacets_stats() {
    return facets_stats;
  }

  public SearchResult<T> setFacets_stats(Map<String, FacetStats> facets_stats) {
    this.facets_stats = facets_stats;
    return this;
  }

  public int getObjectPosition(@Nonnull String objectID, @Nonnull Class<T> clazz) {
    return IntStream.range(0, hits.size())
        .filter(i -> objectID.equals(AlgoliaUtils.getObjectID(hits.get(i), clazz)))
        .findFirst()
        .orElse(-1);
  }

  public Boolean getExhaustiveNbHits() {
    return exhaustiveNbHits;
  }

  public SearchResult<T> setExhaustiveNbHits(Boolean exhaustiveNbHits) {
    this.exhaustiveNbHits = exhaustiveNbHits;
    return this;
  }

  @Override
  public String toString() {
    return "SearchResult{"
        + "page="
        + page
        + ", nbHits="
        + nbHits
        + ", nbPages="
        + nbPages
        + ", hitsPerPage="
        + hitsPerPage
        + ", processingTimeMS="
        + processingTimeMS
        + ", facets="
        + facets
        + ", facets_stats="
        + facets_stats
        + ", exhaustiveFacetsCount="
        + exhaustiveFacetsCount
        + ", query='"
        + query
        + '\''
        + ", params='"
        + params
        + '\''
        + ", hits="
        + hits
        + ", index='"
        + index
        + '\''
        + ", processed="
        + processed
        + '\''
        + ", exhaustiveNbHits="
        + exhaustiveNbHits
        + '}';
  }
}
