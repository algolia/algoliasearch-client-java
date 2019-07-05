package com.algolia.search;

import com.algolia.search.models.common.CallType;
import com.algolia.search.models.common.CompressionType;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

@SuppressWarnings({"unused"})
public final class SearchConfig extends ConfigBase {

  public static class Builder extends ConfigBase.Builder<Builder> {

    /** Builds a {@link SearchConfig} with the default hosts */
    public Builder(@Nonnull String applicationID, @Nonnull String apiKey) {
      super(applicationID, apiKey, createDefaultHosts(applicationID), CompressionType.GZIP);
    }

    @Override
    public Builder getThis() {
      return this;
    }

    public SearchConfig build() {
      return new SearchConfig(this);
    }

    /** Create default hosts for the search configuration */
    private static List<StatefulHost> createDefaultHosts(@Nonnull String applicationID) {

      List<StatefulHost> hosts =
          Arrays.asList(
              new StatefulHost(
                  String.format("%s-dsn.algolia.net", applicationID), EnumSet.of(CallType.READ)),
              new StatefulHost(
                  String.format("%s.algolia.net", applicationID), EnumSet.of(CallType.WRITE)));

      List<StatefulHost> commonHosts =
          Arrays.asList(
              new StatefulHost(
                  String.format("%s-1.algolianet.com", applicationID),
                  EnumSet.of(CallType.READ, CallType.WRITE)),
              new StatefulHost(
                  String.format("%s-2.algolianet.com", applicationID),
                  EnumSet.of(CallType.READ, CallType.WRITE)),
              new StatefulHost(
                  String.format("%s-3.algolianet.com", applicationID),
                  EnumSet.of(CallType.READ, CallType.WRITE)));

      Collections.shuffle(commonHosts, new Random());

      return Stream.concat(hosts.stream(), commonHosts.stream()).collect(Collectors.toList());
    }

    /** Enables compression for the SearchClient. See {@link CompressionType} */
    public Builder setCompressionType(@Nonnull CompressionType compressionType) {
      this.compressionType = compressionType;
      return this;
    }
  }

  private SearchConfig(Builder builder) {
    super(builder);
  }
}
