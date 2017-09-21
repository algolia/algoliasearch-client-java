package com.algolia.search;

import java.util.List;

public class AlgoliaObjectWithArray extends AlgoliaObject {

  private List<String> tags;

  public AlgoliaObjectWithArray() {}

  public List<String> getTags() {
    return tags;
  }

  @Override
  public AlgoliaObjectWithArray setAge(int age) {
    return (AlgoliaObjectWithArray) super.setAge(age);
  }

  public AlgoliaObjectWithArray setTags(List<String> tags) {
    this.tags = tags;
    return this;
  }
}
