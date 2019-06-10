package com.algolia.search.models.mcm;

import java.io.Serializable;

@SuppressWarnings("WeakerAccess")
public class Cluster implements Serializable {

  private String clusterName;

  private Long nbRecords;

  private Integer nbUserIDs;

  private Long dataSize;

  public String getClusterName() {
    return clusterName;
  }

  public Long getNbRecords() {
    return nbRecords;
  }

  public Integer getNbUserIDs() {
    return nbUserIDs;
  }

  public Long getDataSize() {
    return dataSize;
  }

  public String toString() {
    return "Cluster{"
        + "clusterName='"
        + clusterName
        + '\''
        + ", nbRecords="
        + nbRecords
        + '\''
        + ", nbUserIDs="
        + nbUserIDs
        + '\''
        + ", dataSize="
        + dataSize
        + '}';
  }
}
