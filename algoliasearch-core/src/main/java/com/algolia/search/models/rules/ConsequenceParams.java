package com.algolia.search.models.rules;

import com.algolia.search.models.indexing.QueryBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

/**
 * Consequence parameter. More information:
 *
 * @see <a href="https://www.algolia.com/doc/api-client/methods/query-rules">Algolia.com</a>
 */
@SuppressWarnings({"unused"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsequenceParams extends QueryBase<ConsequenceParams> {

  private ConsequenceQuery query;

  @JsonDeserialize(using = AutomaticFacetFilterDeserializer.class)
  private List<AutomaticFacetFilter> automaticFacetFilters;

  @JsonDeserialize(using = AutomaticFacetFilterDeserializer.class)
  private List<AutomaticFacetFilter> automaticOptionalFacetFilters;

  public ConsequenceParams() {}

  @JsonProperty("query")
  public ConsequenceQuery getConsequenceQuery() {
    return query;
  }

  @JsonProperty("query")
  public ConsequenceParams setConsequenceQuery(ConsequenceQuery consequenceQuery) {
    this.query = consequenceQuery;
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

  @Override
  @JsonIgnore
  public ConsequenceParams getThis() {
    return this;
  }
}
