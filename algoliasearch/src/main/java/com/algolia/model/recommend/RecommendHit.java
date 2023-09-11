// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.recommend;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Recommend hit. */
public class RecommendHit {

  @JsonProperty("objectID")
  private String objectID;

  @JsonProperty("_highlightResult")
  private Map<String, HighlightResult> highlightResult;

  @JsonProperty("_snippetResult")
  private Map<String, SnippetResult> snippetResult;

  @JsonProperty("_rankingInfo")
  private RankingInfo rankingInfo;

  @JsonProperty("_distinctSeqID")
  private Integer distinctSeqID;

  @JsonProperty("_score")
  private Double score;

  private Map<String, Object> additionalProperties = new HashMap<>();

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public RecommendHit setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
    return this;
  }

  public RecommendHit setObjectID(String objectID) {
    this.objectID = objectID;
    return this;
  }

  /** Unique object identifier. */
  @javax.annotation.Nonnull
  public String getObjectID() {
    return objectID;
  }

  public RecommendHit setHighlightResult(Map<String, HighlightResult> highlightResult) {
    this.highlightResult = highlightResult;
    return this;
  }

  public RecommendHit putHighlightResult(String key, HighlightResult highlightResultItem) {
    if (this.highlightResult == null) {
      this.highlightResult = new HashMap<>();
    }
    this.highlightResult.put(key, highlightResultItem);
    return this;
  }

  /** Show highlighted section and words matched on a query. */
  @javax.annotation.Nullable
  public Map<String, HighlightResult> getHighlightResult() {
    return highlightResult;
  }

  public RecommendHit setSnippetResult(Map<String, SnippetResult> snippetResult) {
    this.snippetResult = snippetResult;
    return this;
  }

  public RecommendHit putSnippetResult(String key, SnippetResult snippetResultItem) {
    if (this.snippetResult == null) {
      this.snippetResult = new HashMap<>();
    }
    this.snippetResult.put(key, snippetResultItem);
    return this;
  }

  /**
   * Snippeted attributes show parts of the matched attributes. Only returned when
   * attributesToSnippet is non-empty.
   */
  @javax.annotation.Nullable
  public Map<String, SnippetResult> getSnippetResult() {
    return snippetResult;
  }

  public RecommendHit setRankingInfo(RankingInfo rankingInfo) {
    this.rankingInfo = rankingInfo;
    return this;
  }

  /** Get rankingInfo */
  @javax.annotation.Nullable
  public RankingInfo getRankingInfo() {
    return rankingInfo;
  }

  public RecommendHit setDistinctSeqID(Integer distinctSeqID) {
    this.distinctSeqID = distinctSeqID;
    return this;
  }

  /** Get distinctSeqID */
  @javax.annotation.Nullable
  public Integer getDistinctSeqID() {
    return distinctSeqID;
  }

  public RecommendHit setScore(Double score) {
    this.score = score;
    return this;
  }

  /** Recommendation score. minimum: 0 maximum: 100 */
  @javax.annotation.Nonnull
  public Double getScore() {
    return score;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecommendHit recommendHit = (RecommendHit) o;
    return (
      Objects.equals(this.objectID, recommendHit.objectID) &&
      Objects.equals(this.highlightResult, recommendHit.highlightResult) &&
      Objects.equals(this.snippetResult, recommendHit.snippetResult) &&
      Objects.equals(this.rankingInfo, recommendHit.rankingInfo) &&
      Objects.equals(this.distinctSeqID, recommendHit.distinctSeqID) &&
      Objects.equals(this.score, recommendHit.score) &&
      super.equals(o)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectID, highlightResult, snippetResult, rankingInfo, distinctSeqID, score, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecommendHit {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    objectID: ").append(toIndentedString(objectID)).append("\n");
    sb.append("    highlightResult: ").append(toIndentedString(highlightResult)).append("\n");
    sb.append("    snippetResult: ").append(toIndentedString(snippetResult)).append("\n");
    sb.append("    rankingInfo: ").append(toIndentedString(rankingInfo)).append("\n");
    sb.append("    distinctSeqID: ").append(toIndentedString(distinctSeqID)).append("\n");
    sb.append("    score: ").append(toIndentedString(score)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
