package com.algolia.search.models.mcm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class TopUserIdResponse implements Serializable {

  private HashMap<String, List<UserId>> topUsers;

  public HashMap<String, List<UserId>> getTopUsers() {
    return topUsers;
  }

  public TopUserIdResponse setTopUsers(HashMap<String, List<UserId>> topUsers) {
    this.topUsers = topUsers;
    return this;
  }
}
