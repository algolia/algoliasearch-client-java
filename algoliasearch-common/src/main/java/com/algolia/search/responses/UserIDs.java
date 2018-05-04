package com.algolia.search.responses;

import com.algolia.search.objects.UserID;
import java.io.Serializable;
import java.util.List;

public class UserIDs implements Serializable {

  private List<UserID> userIDs;

  private Integer page;

  private Integer hitsPerPage;

  public List<UserID> getUserIDs() {
    return userIDs;
  }

  public Integer getPage() {
    return page;
  }

  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public UserIDs setUserIDs(List<UserID> userIDs) {
    this.userIDs = userIDs;
    return this;
  }

  public UserIDs setPage(Integer page) {
    this.page = page;
    return this;
  }

  public UserIDs setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }
}
