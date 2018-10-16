package com.algolia.search.responses;

import java.io.Serializable;
import java.util.List;

public class SearchUserIDs implements Serializable {

  private List<UserIDHit> hits;
  private int nbHits;
  private int page;
  private int hitsPerPage;
  private int updatedAt;

  public List<UserIDHit> getHits() {
    return hits;
  }

  public void setHits(List<UserIDHit> hits) {
    this.hits = hits;
  }

  public int getNbHits() {
    return nbHits;
  }

  public void setNbHits(int nbHits) {
    this.nbHits = nbHits;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getHitsPerPage() {
    return hitsPerPage;
  }

  public void setHitsPerPage(int hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
  }

  public int getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(int updatedAt) {
    this.updatedAt = updatedAt;
  }
}
