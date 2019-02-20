package com.algolia.search.transport;

import com.algolia.search.models.CallType;
import com.algolia.search.models.RetryOutcome;
import java.util.List;

interface IRetryStrategy {
  RetryOutcome decide(StatefulHost tryableHost, int httpResponseCode, boolean isTimedOut);

  List<StatefulHost> getTryableHosts(CallType callType);
}
