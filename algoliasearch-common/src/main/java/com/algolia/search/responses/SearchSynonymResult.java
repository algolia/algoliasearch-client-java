package com.algolia.search.responses;

import com.algolia.search.inputs.synonym.AbstractSynonym;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class SearchSynonymResult implements Serializable {

  private int nbHits;
  private List<AbstractSynonym> hits;

  public List<AbstractSynonym> getHits() {
    return hits;
  }

  public SearchSynonymResult setHits(List<AbstractSynonym> hits) {
    this.hits = hits;
    return this;
  }

  public int getNbHits() {
    return nbHits;
  }

  public SearchSynonymResult setNbHits(int nbHits) {
    this.nbHits = nbHits;
    return this;
  }

  @Override
  public String toString() {
    return "SearchSynonymResult{" + "nbHits=" + nbHits + ", hits=" + hits + '}';
  }

  public static SearchSynonymResult empty() {
    return new SearchSynonymResult().setNbHits(0).setHits(Collections.emptyList());
  }
}
