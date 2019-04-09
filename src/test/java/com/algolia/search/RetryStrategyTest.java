package com.algolia.search;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.models.common.CallType;
import com.algolia.search.models.common.RetryOutcome;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RetryStrategyTest {

  @ParameterizedTest
  @CsvSource({"500, READ", "500, WRITE", "300, READ", "500, WRITE"})
  void testRetryStrategyRetryableFailure(int httpCode, CallType callType) {
    SearchConfig config = new SearchConfig.Builder("appID", "apiKEY").build();
    RetryStrategy retryStrategy = new RetryStrategy(config);

    List<StatefulHost> hosts = retryStrategy.getTryableHosts(callType);
    assertThat(hosts).filteredOn(StatefulHost::isUp).hasSize(4);

    RetryOutcome decision = retryStrategy.decide(hosts.get(0), httpCode, false);
    assertThat(decision).isEqualTo(RetryOutcome.RETRY);

    List<StatefulHost> updatedHosts = retryStrategy.getTryableHosts(callType);
    assertThat(updatedHosts).filteredOn(StatefulHost::isUp).hasSize(3);
  }

  @ParameterizedTest
  @CsvSource({"400, READ", "400, WRITE", "404, READ", "404, WRITE"})
  void testRetryStrategyFailureDecision(int httpCode, CallType callType) {
    SearchConfig config = new SearchConfig.Builder("appID", "apiKEY").build();
    RetryStrategy retryStrategy = new RetryStrategy(config);

    List<StatefulHost> hosts = retryStrategy.getTryableHosts(callType);
    assertThat(hosts).filteredOn(StatefulHost::isUp).hasSize(4);

    RetryOutcome decision = retryStrategy.decide(hosts.get(0), httpCode, false);
    assertThat(decision).isEqualTo(RetryOutcome.FAILURE);
  }

  @ParameterizedTest
  @CsvSource({"READ", "WRITE"})
  void testTimeOutCome(CallType callType) {
    SearchConfig config = new SearchConfig.Builder("appID", "apiKEY").build();
    RetryStrategy retryStrategy = new RetryStrategy(config);

    List<StatefulHost> hosts = retryStrategy.getTryableHosts(callType);
    assertThat(hosts).filteredOn(StatefulHost::isUp).hasSize(4);

    RetryOutcome decision = retryStrategy.decide(hosts.get(0), 0, true);
    assertThat(decision).isEqualTo(RetryOutcome.RETRY);
  }

  @ParameterizedTest
  @CsvSource({"READ", "WRITE"})
  void testResetExpiredHost(CallType callType) {
    SearchConfig config =
        new SearchConfig.Builder("appID", "apiKEY")
            .setHosts(
                Collections.singletonList(new StatefulHost("Algolia", EnumSet.of(CallType.READ))))
            .build();

    RetryStrategy retryStrategy = new RetryStrategy(config);

    // The host is down and not used for more than 5 minutes.
    // The retry should reset it.
    List<StatefulHost> hosts = retryStrategy.getTryableHosts(callType);
    assertThat(hosts).filteredOn(StatefulHost::isUp).hasSize(1);
  }
}
