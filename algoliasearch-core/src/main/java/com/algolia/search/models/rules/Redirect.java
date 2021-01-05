package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Redirect implements Serializable {
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
}
