package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.indexing.SearchResult;
import com.algolia.search.models.mcm.*;
import com.algolia.search.utils.QueryStringUtils;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface SearchClientMcm extends SearchClientBase {

  /** List the clusters available in a multi-clusters setup for a single appID */
  default ListClustersResponse listClusters() {
    return LaunderThrowable.unwrap(listClustersAsync());
  }

  /**
   * List the clusters available in a multi-clusters setup for a single appID
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default ListClustersResponse listClusters(RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(listClustersAsync(requestOptions));
  }

  /** List the clusters available in a multi-clusters setup for a single appID */
  default CompletableFuture<ListClustersResponse> listClustersAsync() {
    return listClustersAsync(null);
  }

  /**
   * List the clusters available in a multi-clusters setup for a single appID
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<ListClustersResponse> listClustersAsync(RequestOptions requestOptions) {
    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/clusters",
            CallType.READ,
            ListClustersResponse.class,
            requestOptions);
  }

  /**
   * Search for userIDs The data returned will usually be a few seconds behind real-time, because
   * userID usage may take up to a few seconds propagate to the different cluster
   *
   * @param query The query to search for userIDs
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SearchResult<UserId> searchUserIDs(@Nonnull SearchUserIdsRequest query) {
    return LaunderThrowable.unwrap(searchUserIDsAsync(query));
  }

  /**
   * Search for userIDs The data returned will usually be a few seconds behind real-time, because
   * userID usage may take up to a few seconds propagate to the different cluster
   *
   * @param query The query to search for userIDs
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SearchResult<UserId> searchUserIDs(
      @Nonnull SearchUserIdsRequest query, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(searchUserIDsAsync(query, requestOptions));
  }

  /**
   * Search for userIDs The data returned will usually be a few seconds behind real-time, because
   * userID usage may take up to a few seconds propagate to the different cluster
   *
   * @param query The query to search for userIDs
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SearchResult<UserId>> searchUserIDsAsync(
      @Nonnull SearchUserIdsRequest query) {
    return searchUserIDsAsync(query, null);
  }

  /**
   * Search for userIDs The data returned will usually be a few seconds behind real-time, because
   * userID usage may take up to a few seconds propagate to the different cluster
   *
   * @param query The query to search for userIDs
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  @SuppressWarnings("unchecked")
  default CompletableFuture<SearchResult<UserId>> searchUserIDsAsync(
      @Nonnull SearchUserIdsRequest query, RequestOptions requestOptions) {

    Objects.requireNonNull(query, "A query is required");

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/clusters/mapping/search",
            CallType.READ,
            query,
            SearchResult.class,
            UserId.class,
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<SearchResult<UserId>> r = new CompletableFuture<>();
              r.complete(resp);
              return r;
            },
            getConfig().getExecutor());
  }

  /**
   * List the userIDs assigned to a multi-clusters appID.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default ListUserIdsResponse listUserIDs() {
    return LaunderThrowable.unwrap(listUserIDsAsync());
  }

  /**
   * List the userIDs assigned to a multi-clusters appID.
   *
   * @param page The page number to request
   * @param hitsPerPage Number of hits per page
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default ListUserIdsResponse listUserIDs(
      int page, int hitsPerPage, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(listUserIDsAsync(page, hitsPerPage, requestOptions));
  }

  /**
   * List the userIDs assigned to a multi-clusters appID.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default ListUserIdsResponse listUserIDs(RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(listUserIDsAsync(requestOptions));
  }

  /**
   * List the userIDs assigned to a multi-clusters appID.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<ListUserIdsResponse> listUserIDsAsync() {
    return listUserIDsAsync(0, 10, null);
  }

  /**
   * List the userIDs assigned to a multi-clusters appID.
   *
   * @param page The page number to request
   * @param hitsPerPage Number of hits per page
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<ListUserIdsResponse> listUserIDsAsync(
      int page, int hitsPerPage, RequestOptions requestOptions) {

    if (requestOptions == null) {
      requestOptions = new RequestOptions();
    }

    requestOptions.addExtraQueryParameters("page", String.valueOf(page));
    requestOptions.addExtraQueryParameters("hitsPerPage", String.valueOf(hitsPerPage));

    return listUserIDsAsync(requestOptions);
  }

  /**
   * List the userIDs assigned to a multi-clusters appID.
   *
   * @param requestOptions Options to pass to this request
   */
  default CompletableFuture<ListUserIdsResponse> listUserIDsAsync(RequestOptions requestOptions) {
    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/clusters/mapping",
            CallType.READ,
            ListUserIdsResponse.class,
            requestOptions);
  }

  /**
   * Returns the userID data stored in the mapping.
   *
   * @param userID The userID in the mapping
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default UserId getUserID(@Nonnull String userID) {
    return LaunderThrowable.unwrap(getUserIDAsync(userID));
  }

  /**
   * Returns the userID data stored in the mapping.
   *
   * @param userID The userID in the mapping
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default UserId getUserID(@Nonnull String userID, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(getUserIDAsync(userID, requestOptions));
  }

  /**
   * Returns the userID data stored in the mapping.
   *
   * @param userID The userID in the mapping
   */
  default CompletableFuture<UserId> getUserIDAsync(@Nonnull String userID) {
    Objects.requireNonNull(userID, "The userID is required.");
    return getUserIDAsync(userID, null);
  }

  /**
   * Returns the userID data stored in the mapping.
   *
   * @param userID The userID in the mapping
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<UserId> getUserIDAsync(
      @Nonnull String userID, RequestOptions requestOptions) {
    Objects.requireNonNull(userID, "The userID is required.");

    if (userID.trim().length() == 0) {
      throw new AlgoliaRuntimeException("userID must not be empty.");
    }

    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/clusters/mapping/" + QueryStringUtils.urlEncodeUTF8(userID),
            CallType.READ,
            UserId.class,
            requestOptions);
  }

  /**
   * Get the top 10 userIDs with the highest number of records per cluster. The data returned will
   * usually be a few seconds behind real-time, because userID usage may take up to a few seconds to
   * propagate to the different clusters.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default TopUserIdResponse getTopUserID() {
    return LaunderThrowable.unwrap(getTopUserIDAsync());
  }

  /**
   * Get the top 10 userIDs with the highest number of records per cluster. The data returned will
   * usually be a few seconds behind real-time, because userID usage may take up to a few seconds to
   * propagate to the different clusters.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default TopUserIdResponse getTopUserID(RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(getTopUserIDAsync(requestOptions));
  }

  /**
   * Get the top 10 userIDs with the highest number of records per cluster. The data returned will
   * usually be a few seconds behind real-time, because userID usage may take up to a few seconds to
   * propagate to the different clusters.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<TopUserIdResponse> getTopUserIDAsync() {
    return getTopUserIDAsync(null);
  }

  /**
   * Get the top 10 userIDs with the highest number of records per cluster. The data returned will
   * usually be a few seconds behind real-time, because userID usage may take up to a few seconds to
   * propagate to the different clusters.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<TopUserIdResponse> getTopUserIDAsync(RequestOptions requestOptions) {
    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/clusters/mapping/top",
            CallType.READ,
            TopUserIdResponse.class,
            requestOptions);
  }

  /**
   * Remove a userID and its associated data from the multi-clusters
   *
   * @param userId The userID to remove
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default RemoveUserIdResponse removeUserID(@Nonnull String userId) {
    return LaunderThrowable.unwrap(removeUserIDAsync(userId, null));
  }

  /**
   * Remove a userID and its associated data from the multi-clusters.
   *
   * @param userId userID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default RemoveUserIdResponse removeUserID(@Nonnull String userId, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(removeUserIDAsync(userId, requestOptions));
  }

  /**
   * Remove a userID and its associated data from the multi-clusters.
   *
   * @param userId userID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<RemoveUserIdResponse> removeUserIDAsync(@Nonnull String userId) {
    return removeUserIDAsync(userId, null);
  }

  /**
   * Remove a userID and its associated data from the multi-clusters.
   *
   * @param userId userID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<RemoveUserIdResponse> removeUserIDAsync(
      @Nonnull String userId, RequestOptions requestOptions) {
    Objects.requireNonNull(userId, "userId key is required.");

    if (userId.trim().length() == 0) {
      throw new AlgoliaRuntimeException("userId must not be empty.");
    }

    if (requestOptions == null) {
      requestOptions = new RequestOptions();
    }

    requestOptions.addExtraHeader(Defaults.ALGOLIA_USER_ID_HEADER, userId);

    return getTransport()
        .executeRequestAsync(
            HttpMethod.DELETE,
            "/1/clusters/mapping",
            CallType.WRITE,
            RemoveUserIdResponse.class,
            requestOptions);
  }

  /**
   * Assign or Move a userID to a cluster. The time it takes to migrate (move) a user is
   * proportional to the amount of data linked to the userID.
   *
   * @param userId The userID
   * @param clusterName The name of the cluster
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default AssignUserIdResponse assignUserID(@Nonnull String userId, @Nonnull String clusterName) {
    return LaunderThrowable.unwrap(assignUserIDAsync(userId, clusterName));
  }

  /**
   * Assign or Move a userID to a cluster. The time it takes to migrate (move) a user is
   * proportional to the amount of data linked to the userID.
   *
   * @param userId The userID
   * @param clusterName The name of the cluster
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default AssignUserIdResponse assignUserID(
      @Nonnull String userId, @Nonnull String clusterName, RequestOptions requestOptions) {
    return LaunderThrowable.unwrap(assignUserIDAsync(userId, clusterName, requestOptions));
  }

  /**
   * Assign or Move a userID to a cluster. The time it takes to migrate (move) a user is
   * proportional to the amount of data linked to the userID.
   *
   * @param userId The userID
   * @param clusterName The name of the cluster
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<AssignUserIdResponse> assignUserIDAsync(
      @Nonnull String userId, @Nonnull String clusterName) {
    return assignUserIDAsync(userId, clusterName, null);
  }

  /**
   * Assign or Move a userID to a cluster. The time it takes to migrate (move) a user is
   * proportional to the amount of data linked to the userID.
   *
   * @param userId The userID
   * @param clusterName The name of the cluster
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<AssignUserIdResponse> assignUserIDAsync(
      @Nonnull String userId, @Nonnull String clusterName, RequestOptions requestOptions) {
    Objects.requireNonNull(userId, "userId key is required.");
    Objects.requireNonNull(clusterName, "clusterName key is required.");

    if (userId.trim().length() == 0) {
      throw new AlgoliaRuntimeException("userId must not be empty.");
    }

    if (clusterName.trim().length() == 0) {
      throw new AlgoliaRuntimeException("clusterName must not be empty.");
    }

    if (requestOptions == null) {
      requestOptions = new RequestOptions();
    }

    requestOptions.addExtraHeader(Defaults.ALGOLIA_USER_ID_HEADER, userId);

    AssignUserIdRequest request = new AssignUserIdRequest(clusterName);

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/clusters/mapping",
            CallType.WRITE,
            request,
            AssignUserIdResponse.class,
            requestOptions);
  }
}
