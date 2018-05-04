package com.algolia.search.objects;

import java.io.Serializable;

public class UserID implements Serializable {

  private String userID;

  private String clusterName;

  private Long nbRecords;

  private Long dataSize;

  public String getUserID() {
    return userID;
  }

  public String getClusterName() {
    return clusterName;
  }

  public Long getNbRecords() {
    return nbRecords;
  }

  public Long getDataSize() {
    return dataSize;
  }

  public UserID setUserID(String id) {
    this.userID = id;
    return this;
  }

  public UserID setClusterName(String clusterName) {
    this.clusterName = clusterName;
    return this;
  }

  public UserID setNbRecords(Long nbRecords) {
    this.nbRecords = nbRecords;
    return this;
  }

  public UserID setDataSize(Long dataSize) {
    this.dataSize = dataSize;
    return this;
  }

  public String toString() {
    return "UserID{"
        + "userID='"
        + userID
        + '\''
        + ", clusterName='"
        + clusterName
        + '\''
        + ", nbRecords="
        + nbRecords
        + '\''
        + ", dataSize="
        + dataSize
        + '}';
  }
}
