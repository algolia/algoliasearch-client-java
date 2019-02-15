package com.algolia.search.clients;

import com.algolia.search.models.CallType;
import com.algolia.search.transport.StatefulHost;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchConfig extends AlgoliaConfig {

  public SearchConfig(String applicationID, String apiKey) {
    super(applicationID, apiKey);

    List<StatefulHost> hosts =
        Arrays.asList(
            new StatefulHost(
                String.format("%s-dsn.algolia.net", applicationID),
                true,
                LocalDate.now(ZoneOffset.UTC),
                EnumSet.of(CallType.READ)),
            new StatefulHost(
                String.format("%s.algolia.net", applicationID),
                true,
                LocalDate.now(ZoneOffset.UTC),
                EnumSet.of(CallType.WRITE)));

    List<StatefulHost> commonHosts =
        Arrays.asList(
            new StatefulHost(
                String.format("%s-1.algolianet.com", applicationID),
                true,
                LocalDate.now(ZoneOffset.UTC),
                EnumSet.of(CallType.READ, CallType.WRITE)),
            new StatefulHost(
                String.format("%s-2.algolianet.com", applicationID),
                true,
                LocalDate.now(ZoneOffset.UTC),
                EnumSet.of(CallType.READ, CallType.WRITE)),
            new StatefulHost(
                String.format("%s-3.algolianet.com", applicationID),
                true,
                LocalDate.now(ZoneOffset.UTC),
                EnumSet.of(CallType.READ, CallType.WRITE)));

    Collections.shuffle(commonHosts, new Random());

    this.setDefaultHost(
        Stream.concat(hosts.stream(), commonHosts.stream()).collect(Collectors.toList()));
  }
}
