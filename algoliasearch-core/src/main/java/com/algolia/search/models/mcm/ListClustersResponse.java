package com.algolia.search.models.mcm;

import java.io.Serializable;
import java.util.List;

public class ListClustersResponse implements Serializable {

  private List<Cluster> clusters;

  public List<Cluster> getClusters() {
    return clusters;
  }

  public ListClustersResponse setClusters(List<Cluster> clusters) {
    this.clusters = clusters;
    return this;
  }
}
