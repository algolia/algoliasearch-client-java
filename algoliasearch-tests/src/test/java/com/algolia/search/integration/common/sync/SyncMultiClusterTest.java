package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Cluster;
import com.algolia.search.objects.UserID;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public abstract class SyncMultiClusterTest extends SyncAlgoliaIntegrationTest {

  private static List<String> userIDsToDeleteAfterTheTests = new ArrayList<>();

  private static String createUniqueUserID() {
    String uniqueUserID = "java2-tests-" + UUID.randomUUID().toString();
    userIDsToDeleteAfterTheTests.add(uniqueUserID);
    return uniqueUserID;
  }

  @Before
  public void checkEnvVariables() throws Exception {
    ALGOLIA_APPLICATION_ID_1 = System.getenv("ALGOLIA_APPLICATION_ID_MCM");
    ALGOLIA_API_KEY_1 = System.getenv("ALGOLIA_API_KEY_MCM");

    super.checkEnvVariables();
  }

  @Test
  public void testMCM() throws AlgoliaException, InterruptedException {
    String userIDPrefix = "java-client-" + this.getClass().getSimpleName() + "-";

    // Make sure we have at least 2 clusters and retrieve the first one
    List<Cluster> clusters = client.listClusters();
    assertThat(clusters.size() > 1);
    Cluster cluster = clusters.get(0);

    // Delete any preexisting user
    List<UserID> existingUserIDs =
        client
            .listUserIDs()
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
      found = client.searchUserIDs(userID, cluster.getClusterName()).getNbHits() != 0;
      Thread.sleep(1000);
    }
  }
}
