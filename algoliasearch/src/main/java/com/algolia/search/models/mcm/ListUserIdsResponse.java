package com.algolia.search.models.mcm;

import java.io.Serializable;
import java.util.List;

public class ListUserIdsResponse implements Serializable {

  private List<UserId> userIDs;

  public List<UserId> getUserIDs() {
    return userIDs;
  }

  public ListUserIdsResponse setUserIDs(List<UserId> userIDs) {
    this.userIDs = userIDs;
    return this;
  }
}
