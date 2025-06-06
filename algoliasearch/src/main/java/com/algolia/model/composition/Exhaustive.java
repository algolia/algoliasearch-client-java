// Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost
// - read more on https://github.com/algolia/api-clients-automation. DO NOT EDIT.

package com.algolia.model.composition;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.Objects;

/**
 * Whether certain properties of the search response are calculated exhaustive (exact) or
 * approximated.
 */
public class Exhaustive {

  @JsonProperty("facetsCount")
  private Boolean facetsCount;

  @JsonProperty("facetValues")
  private Boolean facetValues;

  @JsonProperty("nbHits")
  private Boolean nbHits;

  @JsonProperty("rulesMatch")
  private Boolean rulesMatch;

  @JsonProperty("typo")
  private Boolean typo;

  public Exhaustive setFacetsCount(Boolean facetsCount) {
    this.facetsCount = facetsCount;
    return this;
  }

  /**
   * Whether the facet count is exhaustive (`true`) or approximate (`false`). See the [related
   * discussion](https://support.algolia.com/hc/en-us/articles/4406975248145-Why-are-my-facet-and-hit-counts-not-accurate-).
   */
  @javax.annotation.Nullable
  public Boolean getFacetsCount() {
    return facetsCount;
  }

  public Exhaustive setFacetValues(Boolean facetValues) {
    this.facetValues = facetValues;
    return this;
  }

  /** The value is `false` if not all facet values are retrieved. */
  @javax.annotation.Nullable
  public Boolean getFacetValues() {
    return facetValues;
  }

  public Exhaustive setNbHits(Boolean nbHits) {
    this.nbHits = nbHits;
    return this;
  }

  /**
   * Whether the `nbHits` is exhaustive (`true`) or approximate (`false`). When the query takes more
   * than 50ms to be processed, the engine makes an approximation. This can happen when using
   * complex filters on millions of records, when typo-tolerance was not exhaustive, or when enough
   * hits have been retrieved (for example, after the engine finds 10,000 exact matches). `nbHits`
   * is reported as non-exhaustive whenever an approximation is made, even if the approximation
   * didn’t, in the end, impact the exhaustivity of the query.
   */
  @javax.annotation.Nullable
  public Boolean getNbHits() {
    return nbHits;
  }

  public Exhaustive setRulesMatch(Boolean rulesMatch) {
    this.rulesMatch = rulesMatch;
    return this;
  }

  /**
   * Rules matching exhaustivity. The value is `false` if rules were enable for this query, and
   * could not be fully processed due a timeout. This is generally caused by the number of
   * alternatives (such as typos) which is too large.
   */
  @javax.annotation.Nullable
  public Boolean getRulesMatch() {
    return rulesMatch;
  }

  public Exhaustive setTypo(Boolean typo) {
    this.typo = typo;
    return this;
  }

  /**
   * Whether the typo search was exhaustive (`true`) or approximate (`false`). An approximation is
   * done when the typo search query part takes more than 10% of the query budget (ie. 5ms by
   * default) to be processed (this can happen when a lot of typo alternatives exist for the query).
   * This field will not be included when typo-tolerance is entirely disabled.
   */
  @javax.annotation.Nullable
  public Boolean getTypo() {
    return typo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Exhaustive exhaustive = (Exhaustive) o;
    return (
      Objects.equals(this.facetsCount, exhaustive.facetsCount) &&
      Objects.equals(this.facetValues, exhaustive.facetValues) &&
      Objects.equals(this.nbHits, exhaustive.nbHits) &&
      Objects.equals(this.rulesMatch, exhaustive.rulesMatch) &&
      Objects.equals(this.typo, exhaustive.typo)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(facetsCount, facetValues, nbHits, rulesMatch, typo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Exhaustive {\n");
    sb.append("    facetsCount: ").append(toIndentedString(facetsCount)).append("\n");
    sb.append("    facetValues: ").append(toIndentedString(facetValues)).append("\n");
    sb.append("    nbHits: ").append(toIndentedString(nbHits)).append("\n");
    sb.append("    rulesMatch: ").append(toIndentedString(rulesMatch)).append("\n");
    sb.append("    typo: ").append(toIndentedString(typo)).append("\n");
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
