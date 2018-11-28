package com.algolia.search.integration.common.async;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Cluster;
import com.algolia.search.objects.UserID;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public abstract class AsyncMultiClusterTest extends AsyncAlgoliaIntegrationTest {

  @Before
  public void checkEnvVariables() throws Exception {
    ALGOLIA_APPLICATION_ID_1 = System.getenv("ALGOLIA_APPLICATION_ID_MCM");
    ALGOLIA_API_KEY_1 = System.getenv("ALGOLIA_API_KEY_MCM");

    super.checkEnvVariables();
  }

  @Test
  public void testMCM()
      throws AlgoliaException, InterruptedException, TimeoutException, ExecutionException {
    String userIDPrefix = "java-client-" + this.getClass().getSimpleName() + "-";

    // Make sure we have at least 2 clusters and retrieve the first one
    List<Cluster> clusters = client.listClusters().get(WAIT_TIME_IN_SECONDS, SECONDS);
    assertThat(clusters.size() > 1);
    Cluster cluster = clusters.get(0);

    // Delete any preexisting user
    List<UserID> existingUserIDs =
        client
            .listUserIDs()
            .get(WAIT_TIME_IN_SECONDS, SECONDS)
            .getUserIDs()
            .stream()
            .filter(u -> u.getUserID().startsWith(userIDPrefix))
            .collect(Collectors.toList());

    for (UserID u : existingUserIDs) {
      client.removeUserID(u.getUserID());
    }

    while (!existingUserIDs.isEmpty()) {
      existingUserIDs =
          client
              .listUserIDs()
              .get(WAIT_TIME_IN_SECONDS, SECONDS)
              .getUserIDs()
              .stream()
              .filter(u -> u.getUserID().startsWith(userIDPrefix))
              .collect(Collectors.toList());
      Thread.sleep(1000);
    }

    // Assign one user to the first cluster and make sure it is assigned
    String travisBuildID = System.getenv("TRAVIS_BUILD_NUMBER");
    String userIDSuffix = "-" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    String userIDInstance = ((travisBuildID == null) ? "local" : travisBuildID);
    String userID = userIDPrefix + userIDInstance + userIDSuffix;
    client.assignUserID(userID, cluster.getClusterName());

    Boolean found = false;
    while (!found) {
      found = client.searchUserIDs(userID, cluster.getClusterName()).get().getNbHits() != 0;
      Thread.sleep(1000);
    }
  }
}
