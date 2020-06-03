package com.algolia.search;

import com.algolia.search.models.indexing.PartialUpdateOperation;

class RecordWithPartialUpdate {
  private String objectID;
  private PartialUpdateOperation<?> update;

  public RecordWithPartialUpdate() {}

  public String getObjectID() {
    return objectID;
  }

  public void setObjectID(String objectID) {
    this.objectID = objectID;
  }

  public PartialUpdateOperation<?> getUpdate() {
    return update;
  }

  public void setUpdate(PartialUpdateOperation<?> update) {
    this.update = update;
  }
}
