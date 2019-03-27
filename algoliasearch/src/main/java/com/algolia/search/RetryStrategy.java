package com.algolia.search;

import com.algolia.search.models.common.CallType;
import com.algolia.search.models.common.RetryOutcome;
import com.algolia.search.utils.HttpStatusCodeUtils;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/** Algolia's retry strategy in case of server error, timeouts... */
class RetryStrategy {

  /** Hosts that will be used by the strategy. Could be default hosts or custom hosts */
  private final List<StatefulHost> hosts;

  /**
   * Creates a new instance of the retry strategy. If custom hosts are provided in the configuration
   * it will override the default hosts.
   */
  RetryStrategy(AlgoliaConfigBase config) {
    hosts = (config.getCustomHosts() != null) ? config.getCustomHosts() : config.getDefaultHosts();
  }

  /**
   * Gives the available hosts.
   *
   * @param callType Algolia calltype.
   */
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

  /** Retry logic. Decide if an host is retryable or not regarding the following parameters. */
  RetryOutcome decide(StatefulHost tryableHost, int httpResponseCode, boolean isTimedOut) {

    synchronized (this) {
      if (!isTimedOut && HttpStatusCodeUtils.isSuccess(httpResponseCode)) {
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

  /**
   * Tells if the response is retryable or not depending on the http status code
   *
   * @param httpStatusCode The http status code
   */
  private boolean isRetryable(int httpStatusCode) {
    return httpStatusCode / 100 != 2 && httpStatusCode / 100 != 4;
  }

  /**
   * Reset the given hosts. Sets the retry count to 0 and set the last use to now.
   *
   * @param host The host to reset
   */
  private void reset(StatefulHost host) {
    host.setUp(true).setRetryCount(0).setLastUse(OffsetDateTime.now(ZoneOffset.UTC));
  }

  /** Reset all hosts down for more than 5 minutes. */
  private void resetExpiredHosts() {
    for (StatefulHost host : hosts) {
      if (!host.isUp()
          && Math.abs(
                  Duration.between(OffsetDateTime.now(ZoneOffset.UTC), host.getLastUse())
                      .getSeconds())
              > 5 * 60) {
        reset(host);
      }
    }
  }
}
