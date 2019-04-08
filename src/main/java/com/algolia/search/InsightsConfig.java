package com.algolia.search;

import com.algolia.search.models.common.CallType;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nonnull;

@SuppressWarnings({"unused"})
public final class InsightsConfig extends ConfigBase {

  public static class Builder extends ConfigBase.Builder<Builder> {

    /**
     * Creates an {@link InsightsConfig} with the default region ("us").
     *
     * @param applicationID The ApplicationID
     * @param apiKey The API Key
     */
    public Builder(@Nonnull String applicationID, @Nonnull String apiKey) {
      this(applicationID, apiKey, "us");
    }

    /**
     * Creates an {@link InsightsConfig} with a custom region.
     *
     * @param applicationID The ApplicationID
     * @param apiKey The API Key
     */
    public Builder(@Nonnull String applicationID, @Nonnull String apiKey, @Nonnull String region) {
      super(applicationID, apiKey);

      List<StatefulHost> hosts =
          Collections.singletonList(
              new StatefulHost(
                  "insights." + region + ".algolia.io", EnumSet.of(CallType.READ, CallType.WRITE)));

      this.setDefaultHosts(hosts);
    }

    /**
     * Creates an {@link InsightsConfig} for the search client with custom {@link StatefulHost}.
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

    public InsightsConfig build() {
      return new InsightsConfig(this);
    }
  }

  private InsightsConfig(Builder builder) {
    super(builder);
  }
}
