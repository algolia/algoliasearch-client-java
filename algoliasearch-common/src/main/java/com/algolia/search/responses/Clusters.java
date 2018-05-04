package com.algolia.search.responses;

import com.algolia.search.objects.Cluster;
import java.io.Serializable;
import java.util.List;

public class Clusters implements Serializable {

  private List<Cluster> clusters;

  public List<Cluster> getClusters() {
    return clusters;
  }

  public Clusters setClusters(List<Cluster> clusters) {
    this.clusters = clusters;
    return this;
  }
}
