package com.algolia.search;

import com.algolia.search.models.common.CallType;
import com.algolia.search.models.common.CompressionType;
import com.algolia.search.util.AlgoliaUtils;
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
      super(applicationID, apiKey, createDefaultHosts(region), CompressionType.NONE);
    }

    @Override
    public Builder getThis() {
      return this;
    }

    public InsightsConfig build() {
      return new InsightsConfig(this);
    }

    /** Create default hosts for the insights configuration */
    private static List<StatefulHost> createDefaultHosts(@Nonnull String region) {

      if (AlgoliaUtils.isEmptyWhiteSpace(region)) {
        throw new NullPointerException("The region can't be empty.");
      }

      return Collections.singletonList(
          new StatefulHost(
              "insights." + region + ".algolia.io", EnumSet.of(CallType.READ, CallType.WRITE)));
    }
  }

  private InsightsConfig(Builder builder) {
    super(builder);
  }
}
