package com.algolia.search.integration.client;

import static com.algolia.search.integration.TestHelpers.getMcmUserId;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.SearchClient;
import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.models.indexing.SearchResult;
import com.algolia.search.models.mcm.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public abstract class MultiClusterManagementTest {

  protected final SearchClient mcmClient;

  protected MultiClusterManagementTest(SearchClient mcmClient) {
    this.mcmClient = mcmClient;
  }

  @Test
  void mcmTest() throws ExecutionException, InterruptedException {
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

    ListUserIdsResponse listUserIds = mcmClient.listUserIDsAsync(0, 1000, null).get();
    assertThat(listUserIds.getUserIDs()).extracting(UserId::getUserID).contains(userID);

    TopUserIdResponse topUserIds = mcmClient.getTopUserIDAsync().get();
    assertThat(topUserIds.getTopUsers()).hasSizeGreaterThan(0);

    removeUserId(mcmClient, userID);

    removePastUserIDs(mcmClient);
  }

  void removePastUserIDs(SearchClient mcmClient) throws ExecutionException, InterruptedException {

    int page = 0;
    final int hitsPerPage = 100;
    List<UserId> userIDsToRemove = new ArrayList<>();

    while (true) {
      ListUserIdsResponse listUserIds = mcmClient.listUserIDsAsync(page, hitsPerPage, null).get();

      String today =
          DateTimeFormatter.ofPattern("yyyy-MM-dd-HH").format(ZonedDateTime.now(ZoneOffset.UTC));

      List<UserId> userIDsTemp =
          listUserIds.getUserIDs().stream()
              .filter(
                  r -> r.getUserID().contains("java-") && !r.getUserID().contains("java-" + today))
              .collect(Collectors.toList());

      userIDsToRemove.addAll(userIDsTemp);

      if (listUserIds.getUserIDs().size() < hitsPerPage) break;

      page++;
    }

    userIDsToRemove.forEach(r -> mcmClient.removeUserID(r.getUserID()));
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
