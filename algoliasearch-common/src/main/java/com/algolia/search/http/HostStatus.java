package com.algolia.search.http;

import java.time.Instant;

class HostStatus {

  private final boolean isUp;
  private final long lastModifiedTimestamp;
  private final long hostDownTimeout;

  HostStatus(long hostDownTimeout, boolean isUp, Instant lastModified) {
    this.hostDownTimeout = hostDownTimeout;
    this.isUp = isUp;
    this.lastModifiedTimestamp = lastModified.toEpochMilli();
  }

  boolean isUpOrCouldBeRetried(Instant now) {
    return isUp || Math.abs(now.toEpochMilli() - lastModifiedTimestamp) >= hostDownTimeout;
  }
}
