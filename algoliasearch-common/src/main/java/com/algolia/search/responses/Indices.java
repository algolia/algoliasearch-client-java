package com.algolia.search.responses;

import com.algolia.search.Index;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Indices implements Serializable {

  private List<Index.Attributes> items;

  public List<Index.Attributes> getItems() {
    return items == null ? new ArrayList<>() : items;
  }

  @SuppressWarnings("unused")
  public Indices setItems(List<Index.Attributes> items) {
    this.items = items;
    return this;
  }

  @Override
  public String toString() {
    return "Indices{" + "items=" + items + '}';
  }
}
