package com.algolia.search.integration.client;

import static com.algolia.search.integration.IntegrationTestExtension.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SearchClient;
import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.integration.IntegrationTestExtension;
import com.algolia.search.models.indexing.SearchResult;
import com.algolia.search.models.mcm.*;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class MultiClusterManagementTest {
  @Test
  void mcmTest() throws ExecutionException, InterruptedException, IOException {
    SearchClient mcmClient = new SearchClient(ALGOLIA_APPLICATION_ID_MCM, ALGOLIA_ADMIN_KEY_MCM);

    ListClustersResponse listClusters = mcmClient.listClustersAsync().get();
    assertThat(listClusters.getClusters().size()).isEqualTo(2);

    String userID = getMcmUserId();
    String clusterName = listClusters.getClusters().get(0).getClusterName();

    mcmClient.assignUserIDAsync(userID, clusterName).get();
    waitUserID(mcmClient, userID);

    SearchResult<UserId> searchResponse =
        mcmClient
            .searchUserIDsAsync(new SearchUserIdsRequest().setQuery(userID).setCluster(clusterName))
            .get();
    assertThat(searchResponse.getHits()).hasSize(1);

    ListUserIdsResponse listUserIds = mcmClient.listUserIDsAsync().get();
    assertThat(listUserIds.getUserIDs()).extracting(UserId::getUserID).contains(userID);

    TopUserIdResponse topUserIds = mcmClient.getTopUserIDAsync().get();
    assertThat(topUserIds.getTopUsers()).hasSizeGreaterThan(0);

    removeUserId(mcmClient, userID);

    ListUserIdsResponse listUserIds2 = mcmClient.listUserIDsAsync().get();

    String yesterday =
        DateTimeFormatter.ofPattern("yyyy-MM-dd-HH")
            .format(ZonedDateTime.now(ZoneOffset.UTC).minusDays(1));

    List<UserId> userIDsToRemove =
        listUserIds2.getUserIDs().stream()
            .filter(r -> r.getUserID().contains("java-" + yesterday))
            .collect(Collectors.toList());

    userIDsToRemove.forEach(r -> mcmClient.removeUserID(r.getUserID()));

    mcmClient.close();
  }

  void waitUserID(SearchClient client, String userID) throws InterruptedException {
    try {
      client.getUserID(userID);
    } catch (AlgoliaApiException e) {
      if (e.getHttpErrorCode() == 404
          && e.getMessage().contains("Mapping does not exist for this userID")) {
        Thread.sleep(1000);
        waitUserID(client, userID);
      }
    }
  }

  void removeUserId(SearchClient client, String userID) throws InterruptedException {
    try {
      client.removeUserID(userID);
    } catch (AlgoliaApiException e) {
      if (e.getHttpErrorCode() == 400
          && e.getMessage()
              .contains("Another mapping operation is already running for this userID")) {
        Thread.sleep(1000);
        removeUserId(client, userID);
      }
    }
  }
}
