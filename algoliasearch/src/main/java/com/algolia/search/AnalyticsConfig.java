package com.algolia.search;

import com.algolia.search.models.StatefulHost;
import com.algolia.search.models.common.CallType;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public final class AnalyticsConfig extends AlgoliaConfigBase {

  /**
   * Creates an analytic client configuration with the default host
   *
   * @param applicationID The Application ID
   * @param apiKey The API Key
   */
  public AnalyticsConfig(String applicationID, String apiKey) {
    super(applicationID, apiKey);

    List<StatefulHost> hosts =
        Collections.singletonList(
            new StatefulHost(
                "analytics.algolia.com",
                true,
                OffsetDateTime.now(ZoneOffset.UTC),
                EnumSet.of(CallType.READ, CallType.WRITE)));

    this.setDefaultHosts(hosts);
  }
}
