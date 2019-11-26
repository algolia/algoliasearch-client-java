package com.algolia.search.models.mcm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class HasPendingMappingsResponse implements Serializable {

  public Boolean getPending() {
    return pending;
  }

  public HasPendingMappingsResponse setPending(Boolean pending) {
    this.pending = pending;
    return this;
  }

  public Map<String, List<String>> getClusters() {
    return clusters;
  }

  public HasPendingMappingsResponse setClusters(Map<String, List<String>> clusters) {
    this.clusters = clusters;
    return this;
  }

  private Boolean pending;
  private Map<String, List<String>> clusters;
}
