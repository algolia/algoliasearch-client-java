package com.algolia.search.responses;

import com.algolia.search.inputs.query_rules.Rule;

import java.util.List;

public class SearchRuleResult {

  private List<Rule> hits;
  private int nbHits;
  private int page;
  private int nbPages;

  public int getNbHits() {
    return nbHits;
  }

  public SearchRuleResult setNbHits(int nbHits) {
    this.nbHits = nbHits;
    return this;
  }

  public int getPage() {
    return page;
  }

  public SearchRuleResult setPage(int page) {
    this.page = page;
    return this;
  }

  public int getNbPages() {
    return nbPages;
  }

  public SearchRuleResult setNbPages(int nbPages) {
    this.nbPages = nbPages;
    return this;
  }

  public List<Rule> getHits() {
    return hits;
  }

  public SearchRuleResult setHits(List<Rule> hits) {
    this.hits = hits;
    return this;
  }
}
