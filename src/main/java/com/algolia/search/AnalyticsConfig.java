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
