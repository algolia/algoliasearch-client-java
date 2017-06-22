package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleQuery {

  private String query;
  private String anchoring = null;
  private String context = null;

  private Integer page = null;
  private Integer hitsPerPage = null;

  public RuleQuery(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

  /**
   * Set the full text query
   */
  public RuleQuery setQuery(String query) {
    this.query = query;
    return this;
  }

  public String getAnchoring() {
    return anchoring;
  }

  /**
   * Set the anchoring, restricts the search to rules with a specific anchoring type
   */
  public RuleQuery setAnchoring(String anchoring) {
    this.anchoring = anchoring;
    return this;
  }

  public String getContext() {
    return context;
  }

  /**
   * Set the context, restricts the search to rules with a specific context (exact match)
   */
  public RuleQuery setContext(String context) {
    this.context = context;
    return this;
  }

  public Integer getPage() {
    return page;
  }

  /**
   * Set the page to retrieve (zero base). Defaults to 0.
   */
  public RuleQuery setPage(Integer page) {
    this.page = page;
    return this;
  }

  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  /**
   * Set the number of hits per page. Defaults to 10.
   */
  public RuleQuery setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

}
