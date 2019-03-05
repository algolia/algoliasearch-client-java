package com.algolia.search.clients;

import com.algolia.search.models.CallType;
import com.algolia.search.transport.StatefulHost;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class InsightsConfig extends AlgoliaConfig {

  public InsightsConfig(String applicationID, String apiKey) {
    this(applicationID, apiKey, "us");
  }

  public InsightsConfig(String applicationID, String apiKey, String region) {
    super(applicationID, apiKey);

    List<StatefulHost> hosts =
        Collections.singletonList(
            new StatefulHost(
                "insights." + region + ".algolia.io",
                true,
                OffsetDateTime.now(ZoneOffset.UTC),
                EnumSet.of(CallType.READ, CallType.WRITE)));

    this.setDefaultHosts(hosts);
  }
}
