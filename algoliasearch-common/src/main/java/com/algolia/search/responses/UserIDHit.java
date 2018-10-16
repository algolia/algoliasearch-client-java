package com.algolia.search.responses;

import java.io.Serializable;
import java.util.Map;

public class UserIDHit implements Serializable {

  private String userID;
  private String clusterName;
  private int nbRecords;
  private int dataSize;
  private String objectID;
  private Map<String, HighlightResult> _highlightResult;

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public int getNbRecords() {
    return nbRecords;
  }

  public void setNbRecords(int nbRecords) {
    this.nbRecords = nbRecords;
  }

  public int getDataSize() {
    return dataSize;
  }

  public void setDataSize(int dataSize) {
    this.dataSize = dataSize;
  }

  public String getObjectID() {
    return objectID;
  }

  public void setObjectID(String objectID) {
    this.objectID = objectID;
  }

  public Map<String, HighlightResult> get_highlightResult() {
    return _highlightResult;
  }

  public void set_highlightResult(Map<String, HighlightResult> _highlightResult) {
    this._highlightResult = _highlightResult;
  }
}
