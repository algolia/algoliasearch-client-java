package com.algolia.search;

import com.algolia.search.models.common.CallType;
import com.algolia.search.models.common.CompressionType;
import com.algolia.search.util.AlgoliaUtils;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;

public class RecommendationConfig extends ConfigBase {

  public static class Builder extends ConfigBase.Builder<Builder> {
    /**
     * Creates an {@link RecommendationConfig} with the default hosts
     *
     * @param applicationID The Application ID
     * @param apiKey The API Key
     * @param region Region where your personalization data is stored and processed.
     */
    public Builder(@Nonnull String applicationID, @Nonnull String apiKey, @Nonnull String region) {
      super(applicationID, apiKey, createRecommendationHosts(region), CompressionType.NONE);
    }

    @Override
    public RecommendationConfig.Builder getThis() {
      return this;
    }

    public RecommendationConfig build() {
      return new RecommendationConfig(this);
    }

    /** Create hosts for the recommendation configuration */
    private static List<StatefulHost> createRecommendationHosts(@Nonnull String region) {

      Objects.requireNonNull(region, "The region can't be null");

      if (AlgoliaUtils.isEmptyWhiteSpace(region)) {
        throw new IllegalArgumentException("The region is required. It can't be empty.");
      }

      return Collections.singletonList(
          new StatefulHost(
              String.format("recommendation.%s.algolia.com", region),
              EnumSet.of(CallType.READ, CallType.WRITE)));
    }
  }

  private RecommendationConfig(Builder builder) {
    super(builder);
  }
}
