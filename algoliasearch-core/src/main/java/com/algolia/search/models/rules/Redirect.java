package com.algolia.search.models.rules;

import java.util.Objects;

public class Redirect {

  private String url;

  public Redirect() {}

  public Redirect(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public Redirect setUrl(String url) {
    this.url = url;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Redirect)) return false;
    Redirect redirect = (Redirect) o;
    return Objects.equals(url, redirect.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url);
  }

  @Override
  public String toString() {
    return "Redirect{" + "url='" + url + '\'' + '}';
  }
}
