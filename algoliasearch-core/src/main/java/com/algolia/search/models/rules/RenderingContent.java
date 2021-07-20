package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;

/**
 * Content defining how the search interface should be rendered. This is set via the settings for a
 * default value and can be overridden via rules.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RenderingContent implements Serializable {
  private FacetOrdering facetOrdering;

  public RenderingContent() {}

  public RenderingContent(FacetOrdering facetOrdering) {
    this.facetOrdering = facetOrdering;
  }

  public FacetOrdering getFacetOrdering() {
    return facetOrdering;
  }

  public RenderingContent setFacetOrdering(FacetOrdering facetOrdering) {
    this.facetOrdering = facetOrdering;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RenderingContent)) return false;
    RenderingContent that = (RenderingContent) o;
    return Objects.equals(facetOrdering, that.facetOrdering);
  }

  @Override
  public int hashCode() {
    return Objects.hash(facetOrdering);
  }

  @Override
  public String toString() {
    return "RenderingContent{" + "facetOrdering=" + facetOrdering + '}';
  }
}
