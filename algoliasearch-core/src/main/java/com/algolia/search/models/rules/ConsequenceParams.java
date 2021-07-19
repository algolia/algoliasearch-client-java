package com.algolia.search.models.rules;

import com.algolia.search.models.indexing.SearchParameters;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

/**
 * Consequence parameter. More information:
 *
 * @see <a href="https://www.algolia.com/doc/api-client/methods/query-rules">Algolia.com</a>
 */
@SuppressWarnings({"unused"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsequenceParams extends SearchParameters<ConsequenceParams> {

  public ConsequenceParams() {}

  @JsonGetter("query")
  public ConsequenceQuery getConsequenceQuery() {
    return query;
  }

  /**
   * Providing a {@link ConsequenceQuery} will describe incremental edits to be made to the query.
   *
   * <p><b>Important NOTE:</b> Setting a ConsequenceQuery will override regular "query" if set. Both
   * can't be set at the same time.
   */
  @JsonSetter("query")
  public ConsequenceParams setConsequenceQuery(ConsequenceQuery consequenceQuery) {
    this.query = consequenceQuery;
    return this;
  }

  @Override
  @JsonIgnore
  public String getQuery() {

    if (this.query != null) {
      return this.query.getQueryString();
    }

    return null;
  }

  /**
   * When providing a string, it replaces the entire query string.
   *
   * <p><b>Important NOTE:</b> Setting a Query String will override any ConsequenceQuery set before.
   * Both can't be set at the same time.
   */
  @Override
  @JsonIgnore
  public ConsequenceParams setQuery(String query) {
    this.query = new ConsequenceQuery();
    this.query.setQueryString(query);
    return this;
  }

  @Override
  @JsonIgnore
  public ConsequenceParams getThis() {
    return this;
  }

  public List<AutomaticFacetFilter> getAutomaticFacetFilters() {
    return automaticFacetFilters;
  }

  public ConsequenceParams setAutomaticFacetFilters(
      List<AutomaticFacetFilter> automaticFacetFilters) {
    this.automaticFacetFilters = automaticFacetFilters;
    return this;
  }

  public List<AutomaticFacetFilter> getAutomaticOptionalFacetFilters() {
    return automaticOptionalFacetFilters;
  }

  public ConsequenceParams setAutomaticOptionalFacetFilters(
      List<AutomaticFacetFilter> automaticOptionalFacetFilters) {
    this.automaticOptionalFacetFilters = automaticOptionalFacetFilters;
    return this;
  }

  public RenderingContent getRenderingContent() {
    return renderingContent;
  }

  public ConsequenceParams setRenderingContent(
      RenderingContent renderingContent) {
    this.renderingContent = renderingContent;
    return this;
  }

  private RenderingContent renderingContent;
  private ConsequenceQuery query;

  @JsonDeserialize(using = AutomaticFacetFilterDeserializer.class)
  private List<AutomaticFacetFilter> automaticFacetFilters;

  @JsonDeserialize(using = AutomaticFacetFilterDeserializer.class)
  private List<AutomaticFacetFilter> automaticOptionalFacetFilters;
}
