package com.algolia.search.responses;

import com.algolia.search.objects.UserID;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TopUserResult implements Serializable {
  private Map<String, List<UserID>> topUsers;

  public Map<String, List<UserID>> getTopUsers() {
    return topUsers;
  }

  public void setTopUsers(Map<String, List<UserID>> topUsers) {
    this.topUsers = topUsers;
  }
}
