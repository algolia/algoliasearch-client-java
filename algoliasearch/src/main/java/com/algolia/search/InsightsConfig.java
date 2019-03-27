package com.algolia.search;

import com.algolia.search.models.StatefulHost;
import com.algolia.search.models.common.CallType;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public final class InsightsConfig extends AlgoliaConfigBase {

  /**
   * Creates an Insight client configuration with the default region ("us").
   *
   * @param applicationID The ApplicationID
   * @param apiKey The API Key
   */
  public InsightsConfig(String applicationID, String apiKey) {
    this(applicationID, apiKey, "us");
  }

  /**
   * Creates an Insight client configuration with a custom region.
   *
   * @param applicationID The ApplicationID
   * @param apiKey The API Key
   * @param region Region of the server. For example "us"
   */
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
