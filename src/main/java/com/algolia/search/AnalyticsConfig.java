package com.algolia.search;

import com.algolia.search.models.common.CallType;
import java.util.*;
import javax.annotation.Nonnull;

@SuppressWarnings({"unused"})
public final class AnalyticsConfig extends ConfigBase {

  public static class Builder extends ConfigBase.Builder<Builder> {

    /**
     * Creates an {@link AnalyticsConfig} with the default host
     *
     * @param applicationID The Application ID
     * @param apiKey The API Key
     */
    public Builder(@Nonnull String applicationID, @Nonnull String apiKey) {
      super(applicationID, apiKey);

      List<StatefulHost> hosts =
          Collections.singletonList(
              new StatefulHost("analytics.algolia.com", EnumSet.of(CallType.READ, CallType.WRITE)));

      this.setDefaultHosts(hosts);
    }

    /**
     * Creates a {@link AnalyticsConfig} for the search client with custom {@link StatefulHost}.
     * Warning: Defaults hosts are not set when setting custom {@link StatefulHost}.
     *
     * @param applicationID The ApplicationID
     * @param apiKey The API Key
     * @param customHosts List of custom hosts
     */
    public Builder(
        @Nonnull String applicationID,
        @Nonnull String apiKey,
        @Nonnull List<StatefulHost> customHosts) {
      super(applicationID, apiKey);
      this.setCustomHosts(customHosts);
    }

    @Override
    public Builder getThis() {
      return this;
    }

    public AnalyticsConfig build() {
      return new AnalyticsConfig(this);
    }
  }

  private AnalyticsConfig(Builder builder) {
    super(builder);
  }
}
