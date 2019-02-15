package com.algolia.search.transport;

import com.algolia.search.models.CallType;
import java.time.LocalDate;
import java.util.EnumSet;

public class StatefulHost {

  public StatefulHost(String url, boolean up, LocalDate lastUse, EnumSet<CallType> accept) {
    this.url = url;
    this.up = up;
    this.lastUse = lastUse;
    this.accept = accept;
  }

  public String getUrl() {
    return url;
  }

  public StatefulHost setUrl(String url) {
    this.url = url;
    return this;
  }

  public boolean isUp() {
    return up;
  }

  public StatefulHost setUp(boolean up) {
    this.up = up;
    return this;
  }

  public int getRetryCount() {
    return retryCount;
  }

  public StatefulHost setRetryCount(int retryCount) {
    this.retryCount = retryCount;
    return this;
  }

  public LocalDate getLastUse() {
    return lastUse;
  }

  public StatefulHost setLastUse(LocalDate lastUse) {
    this.lastUse = lastUse;
    return this;
  }

  public EnumSet<CallType> getAccept() {
    return accept;
  }

  public StatefulHost setAccept(EnumSet<CallType> accept) {
    this.accept = accept;
    return this;
  }

  private String url;
  private boolean up;
  private int retryCount;
  private LocalDate lastUse;
  private EnumSet<CallType> accept;
}
