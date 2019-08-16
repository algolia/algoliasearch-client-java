package com.algolia.search.models.indexing;

public class HitsWithPosition<T> {

  public HitsWithPosition() {}

  public HitsWithPosition(T hit, long page, long position) {
    this.hit = hit;
    this.page = page;
    this.position = position;
  }

  public T getHit() {
    return hit;
  }

  public HitsWithPosition<T> setHit(T hit) {
    this.hit = hit;
    return this;
  }

  public long getPage() {
    return page;
  }

  public HitsWithPosition<T> setPage(long page) {
    this.page = page;
    return this;
  }

  public long getPosition() {
    return position;
  }

  public HitsWithPosition<T> setPosition(long position) {
    this.position = position;
    return this;
  }

  private T hit;
  private long page;
  private long position;
}
