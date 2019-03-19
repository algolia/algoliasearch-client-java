package com.algolia.search.models.mcm;

import java.io.Serializable;
import java.util.List;

public class ListUserIdsResponse implements Serializable {

  private List<UserId> userIds;

  public List<UserId> getUserIds() {
    return userIds;
  }

  public ListUserIdsResponse setUserIds(List<UserId> userIds) {
    this.userIds = userIds;
    return this;
  }
}
