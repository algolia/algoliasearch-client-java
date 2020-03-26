package com.algolia.search;

import com.algolia.search.models.common.CallType;
import com.algolia.search.util.AlgoliaUtils;
import java.time.OffsetDateTime;
import java.util.EnumSet;

@SuppressWarnings("WeakerAccess")
public class StatefulHost {

  public StatefulHost(String url, EnumSet<CallType> accept) {
    this.url = url;
    this.accept = accept;
  }

  public String getUrl() {
    return url;
  }

  StatefulHost setUrl(String url) {
    this.url = url;
    return this;
  }

  public boolean isUp() {
    return up;
  }

  StatefulHost setUp(boolean up) {
    this.up = up;
    return this;
  }

  public int getRetryCount() {
    return retryCount;
  }

  StatefulHost setRetryCount(int retryCount) {
    this.retryCount = retryCount;
    return this;
  }

  void incrementRetryCount() {
    this.retryCount++;
  }

  public OffsetDateTime getLastUse() {
    return lastUse;
  }

  StatefulHost setLastUse(OffsetDateTime lastUse) {
    this.lastUse = lastUse;
    return this;
  }

  public EnumSet<CallType> getAccept() {
    return accept;
  }

  StatefulHost setAccept(EnumSet<CallType> accept) {
    this.accept = accept;
    return this;
  }

  private String url;
  private boolean up = true;
  private int retryCount;
  private OffsetDateTime lastUse = AlgoliaUtils.nowUTC();
  private EnumSet<CallType> accept;
}
