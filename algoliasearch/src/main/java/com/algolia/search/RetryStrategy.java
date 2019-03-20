package com.algolia.search;

import com.algolia.search.helpers.HttpStatusCodeHelper;
import com.algolia.search.models.StatefulHost;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.common.RetryOutcome;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

class RetryStrategy {
  private final List<StatefulHost> hosts;

  RetryStrategy(AlgoliaConfig config) {
    hosts = (config.getCustomHosts() != null) ? config.getCustomHosts() : config.getDefaultHosts();
  }

  List<StatefulHost> getTryableHosts(CallType callType) {
    synchronized (this) {
      resetExpiredHosts();
      if (hosts.stream().anyMatch(h -> h.isUp() && h.getAccept().contains(callType))) {
        return hosts.stream()
            .filter(h -> h.isUp() && h.getAccept().contains(callType))
            .collect(Collectors.toList());
      } else {
        for (StatefulHost host :
            hosts.stream()
                .filter(h -> h.getAccept().contains(callType))
                .collect(Collectors.toList())) {
          reset(host);
        }

        return hosts;
      }
    }
  }

  RetryOutcome decide(StatefulHost tryableHost, int httpResponseCode, boolean isTimedOut) {

    synchronized (this) {
      if (!isTimedOut && HttpStatusCodeHelper.isSuccess(httpResponseCode)) {
        tryableHost.setUp(true);
        tryableHost.setLastUse(OffsetDateTime.now(ZoneOffset.UTC));
        return RetryOutcome.SUCCESS;
      } else if (!isTimedOut && isRetryable(httpResponseCode)) {
        tryableHost.setUp(false);
        tryableHost.setLastUse(OffsetDateTime.now(ZoneOffset.UTC));
        return RetryOutcome.RETRY;
      } else if (isTimedOut) {
        tryableHost.setUp(true);
        tryableHost.setLastUse(OffsetDateTime.now(ZoneOffset.UTC));
        tryableHost.incrementRetryCount();
        return RetryOutcome.RETRY;
      }

      return RetryOutcome.FAILURE;
    }
  }

  private boolean isRetryable(int httpStatusCode) {
    return httpStatusCode / 100 != 2 && httpStatusCode / 100 != 4;
  }

  private void reset(StatefulHost host) {
    host.setUp(true).setRetryCount(0).setLastUse(OffsetDateTime.now(ZoneOffset.UTC));
  }

  private void resetExpiredHosts() {
    for (StatefulHost host : hosts) {
      if (!host.isUp()) { // add 5 minutes test
        reset(host);
      }
    }
  }
}
