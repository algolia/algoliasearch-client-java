package com.algolia.search.models.mcm;

import java.io.Serializable;

public class AssignUserIdRequest implements Serializable {

  private String cluster;

  public AssignUserIdRequest(String cluster) {
    this.cluster = cluster;
  }

  public String getCluster() {
    return cluster;
  }

  public AssignUserIdRequest setCluster(String cluster) {
    this.cluster = cluster;
    return this;
  }

  @Override
  public String toString() {
    return "AssignUserIdRequest{" + "cluster='" + cluster + '\'' + '}';
  }
}
