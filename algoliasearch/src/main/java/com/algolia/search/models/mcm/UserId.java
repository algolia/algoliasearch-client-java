package com.algolia.search.models.mcm;

import java.io.Serializable;

public class UserId implements Serializable {
  public String getUserID() {
    return userID;
  }

  public UserId setUserID(String userID) {
    this.userID = userID;
    return this;
  }

  public String getClusterName() {
    return clusterName;
  }

  public UserId setClusterName(String clusterName) {
    this.clusterName = clusterName;
    return this;
  }

  public Integer getNbRecords() {
    return nbRecords;
  }

  public UserId setNbRecords(Integer nbRecords) {
    this.nbRecords = nbRecords;
    return this;
  }

  public Integer getDataSize() {
    return dataSize;
  }

  public UserId setDataSize(Integer dataSize) {
    this.dataSize = dataSize;
    return this;
  }

  private String userID;
  private String clusterName;
  private Integer nbRecords;
  private Integer dataSize;
}
