package com.algolia.search;

import com.algolia.search.models.common.CallType;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nonnull;

@SuppressWarnings({"WeakerAccess", "unused"})
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
            new StatefulHost("analytics.algolia.com", EnumSet.of(CallType.READ, CallType.WRITE)));

    this.setDefaultHosts(hosts);
  }

  /**
   * Creates a configuration for the search client with custom {@link StatefulHost}. Warning:
   * Defaults hosts are not set when setting custom {@link StatefulHost}.
   *
   * @param applicationID The ApplicationID
   * @param apiKey The API Key
   * @param customHosts List of custom hosts
   */
  public AnalyticsConfig(
      @Nonnull String applicationID,
      @Nonnull String apiKey,
      @Nonnull List<StatefulHost> customHosts) {
    super(applicationID, apiKey);

    this.setCustomHosts(customHosts);
  }
}
