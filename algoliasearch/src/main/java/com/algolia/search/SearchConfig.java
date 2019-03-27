package com.algolia.search;

import com.algolia.search.models.common.CallType;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class SearchConfig extends AlgoliaConfigBase {

  /**
   * Creates a search config with the default hosts
   *
   * @param applicationID The ApplicationID
   * @param apiKey The API Key
   */
  public SearchConfig(@Nonnull String applicationID, @Nonnull String apiKey) {
    super(applicationID, apiKey);

    List<StatefulHost> hosts =
        Arrays.asList(
            new StatefulHost(
                String.format("%s-dsn.algolia.net", applicationID),
                true,
                OffsetDateTime.now(ZoneOffset.UTC),
                EnumSet.of(CallType.READ)),
            new StatefulHost(
                String.format("%s.algolia.net", applicationID),
                true,
                OffsetDateTime.now(ZoneOffset.UTC),
                EnumSet.of(CallType.WRITE)));

    List<StatefulHost> commonHosts =
        Arrays.asList(
            new StatefulHost(
                String.format("%s-1.algolianet.com", applicationID),
                true,
                OffsetDateTime.now(ZoneOffset.UTC),
                EnumSet.of(CallType.READ, CallType.WRITE)),
            new StatefulHost(
                String.format("%s-2.algolianet.com", applicationID),
                true,
                OffsetDateTime.now(ZoneOffset.UTC),
                EnumSet.of(CallType.READ, CallType.WRITE)),
            new StatefulHost(
                String.format("%s-3.algolianet.com", applicationID),
                true,
                OffsetDateTime.now(ZoneOffset.UTC),
                EnumSet.of(CallType.READ, CallType.WRITE)));

    Collections.shuffle(commonHosts, new Random());

    this.setDefaultHosts(
        Stream.concat(hosts.stream(), commonHosts.stream()).collect(Collectors.toList()));
  }

  /**
   * Creates a configuration for the search client with custom {@link StatefulHost}. Warning:
   * Defaults hosts are not set when setting custom {@link StatefulHost}.
   *
   * @param applicationID The ApplicationID
   * @param apiKey The API Key
   * @param customHosts List of custom hosts
   */
  public SearchConfig(
      @Nonnull String applicationID,
      @Nonnull String apiKey,
      @Nonnull List<StatefulHost> customHosts) {
    super(applicationID, apiKey);

    this.setCustomHosts(customHosts);
  }
}
