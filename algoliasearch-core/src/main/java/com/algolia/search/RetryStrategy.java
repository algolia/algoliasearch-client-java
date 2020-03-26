package com.algolia.search;

import com.algolia.search.models.HttpResponse;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.common.RetryOutcome;
import com.algolia.search.util.AlgoliaUtils;
import com.algolia.search.util.HttpStatusCodeUtils;
import java.time.Duration;
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
  RetryStrategy(ConfigBase config) {
    hosts = config.getHosts();
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
  RetryOutcome decide(StatefulHost tryableHost, HttpResponse response) {

    synchronized (this) {
      if (!response.isTimedOut() && HttpStatusCodeUtils.isSuccess(response)) {
        tryableHost.setUp(true);
        tryableHost.setLastUse(AlgoliaUtils.nowUTC());
        return RetryOutcome.SUCCESS;
      } else if (!response.isTimedOut() && isRetryable(response)) {
        tryableHost.setUp(false);
        tryableHost.setLastUse(AlgoliaUtils.nowUTC());
        return RetryOutcome.RETRY;
      } else if (response.isTimedOut()) {
        tryableHost.setUp(true);
        tryableHost.setLastUse(AlgoliaUtils.nowUTC());
        tryableHost.incrementRetryCount();
        return RetryOutcome.RETRY;
      }

      return RetryOutcome.FAILURE;
    }
  }

  /**
   * Tells if the response is retryable or not depending on the http status code
   *
   * @param response Algolia's API response
   */
  private boolean isRetryable(HttpResponse response) {
    boolean isRetryableHttpCode =
        response.getHttpStatusCode() / 100 != 2 && response.getHttpStatusCode() / 100 != 4;

    return isRetryableHttpCode || response.isNetworkError();
  }

  /**
   * Reset the given hosts. Sets the retry count to 0 and set the last use to now.
   *
   * @param host The host to reset
   */
  private void reset(StatefulHost host) {
    host.setUp(true).setRetryCount(0).setLastUse(AlgoliaUtils.nowUTC());
  }

  /** Reset all hosts down for more than 5 minutes. */
  private void resetExpiredHosts() {
    for (StatefulHost host : hosts) {
      if (!host.isUp()
          && Math.abs(
                  Duration.between(AlgoliaUtils.nowUTC(), host.getLastUse())
                      .getSeconds())
              > 5 * 60) {
        reset(host);
      }
    }
  }
}
