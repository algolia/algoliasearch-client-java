package com.algolia.search.models.mcm;

import java.io.Serializable;
import java.util.List;

public class AssignUserIdsRequest implements Serializable {

  public AssignUserIdsRequest(String clusterName, List<String> users) {
    this.cluster = clusterName;
    this.users = users;
  }

  public String getCluster() {
    return cluster;
  }

  public AssignUserIdsRequest setCluster(String clusterName) {
    this.cluster = clusterName;
    return this;
  }

  public List<String> getUsers() {
    return users;
  }

  public AssignUserIdsRequest setUsers(List<String> users) {
    this.users = users;
    return this;
  }

  private String cluster;
  private List<String> users;
}
