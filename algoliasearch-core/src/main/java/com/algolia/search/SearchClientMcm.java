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
import com.algolia.search.util.AlgoliaUtils;
import com.algolia.search.util.QueryStringUtils;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface SearchClientMcm extends SearchClientBase {

  /** List the clusters available in a multi-clusters setup for a single appID */
  default ListClustersResponse listClusters() {
    return LaunderThrowable.await(listClustersAsync());
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
    return LaunderThrowable.await(listClustersAsync(requestOptions));
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
    return LaunderThrowable.await(searchUserIDsAsync(query));
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
    return LaunderThrowable.await(searchUserIDsAsync(query, requestOptions));
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
    return LaunderThrowable.await(listUserIDsAsync());
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
    return LaunderThrowable.await(listUserIDsAsync(page, hitsPerPage, requestOptions));
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
    return LaunderThrowable.await(listUserIDsAsync(requestOptions));
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
    return LaunderThrowable.await(getUserIDAsync(userID));
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
    return LaunderThrowable.await(getUserIDAsync(userID, requestOptions));
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

    if (AlgoliaUtils.isEmptyWhiteSpace(userID)) {
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
    return LaunderThrowable.await(getTopUserIDAsync());
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
    return LaunderThrowable.await(getTopUserIDAsync(requestOptions));
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
    return LaunderThrowable.await(removeUserIDAsync(userId, null));
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
    return LaunderThrowable.await(removeUserIDAsync(userId, requestOptions));
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

    if (AlgoliaUtils.isEmptyWhiteSpace(userId)) {
      throw new AlgoliaRuntimeException("userId must not be empty or white spaces.");
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
    return LaunderThrowable.await(assignUserIDAsync(userId, clusterName));
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
    return LaunderThrowable.await(assignUserIDAsync(userId, clusterName, requestOptions));
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

    if (AlgoliaUtils.isEmptyWhiteSpace(userId)) {
      throw new AlgoliaRuntimeException("userId must not be empty.");
    }

    if (AlgoliaUtils.isEmptyWhiteSpace(clusterName)) {
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

  /**
   * Assign or Move a userIDs to a cluster. The time it takes to migrate (move) a user is
   * proportional to the amount of data linked to each userID.
   *
   * @param userIds List of UserIDs to map
   * @param clusterName The name of the cluster
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default AssignUserIdsResponse assignUserIDs(
      @Nonnull List<String> userIds, @Nonnull String clusterName) {
    return LaunderThrowable.await(assignUserIDsAsync(userIds, clusterName));
  }

  /**
   * Assign or Move a userIDs to a cluster. The time it takes to migrate (move) a user is
   * proportional to the amount of data linked to each userID.
   *
   * @param userIds List of UserIDs to map
   * @param clusterName The name of the cluster
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default AssignUserIdsResponse assignUserIDs(
      @Nonnull List<String> userIds, @Nonnull String clusterName, RequestOptions requestOptions) {
    return LaunderThrowable.await(assignUserIDsAsync(userIds, clusterName, requestOptions));
  }

  /**
   * Assign or Move a userIDs to a cluster. The time it takes to migrate (move) a user is
   * proportional to the amount of data linked to each userID.
   *
   * @param userIds List of UserIDs to map
   * @param clusterName The name of the cluster
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<AssignUserIdsResponse> assignUserIDsAsync(
      @Nonnull List<String> userIds, @Nonnull String clusterName) {
    return assignUserIDsAsync(userIds, clusterName, null);
  }

  /**
   * Assign or Move a userIDs to a cluster. The time it takes to migrate (move) a user is
   * proportional to the amount of data linked to each userID.
   *
   * @param userIds List of UserIDs to map
   * @param clusterName The name of the cluster
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<AssignUserIdsResponse> assignUserIDsAsync(
      @Nonnull List<String> userIds, @Nonnull String clusterName, RequestOptions requestOptions) {

    Objects.requireNonNull(userIds, "userIds are required required.");
    Objects.requireNonNull(clusterName, "clusterName key is required.");

    if (userIds.stream().anyMatch(AlgoliaUtils::isEmptyWhiteSpace)) {
      throw new AlgoliaRuntimeException("userId must not be empty.");
    }

    if (AlgoliaUtils.isEmptyWhiteSpace(clusterName)) {
      throw new AlgoliaRuntimeException("clusterName must not be empty.");
    }

    if (requestOptions == null) {
      requestOptions = new RequestOptions();
    }

    AssignUserIdsRequest request = new AssignUserIdsRequest(clusterName, userIds);

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/clusters/mapping/batch",
            CallType.WRITE,
            request,
            AssignUserIdsResponse.class,
            requestOptions);
  }

  /**
   * Get cluster pending (migrating, creating, deleting) mapping state. Query cluster pending
   * mapping status and get cluster mappings.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default HasPendingMappingsResponse hasPendingMappings() {
    return LaunderThrowable.await(hasPendingMappingsAsync());
  }

  /**
   * Get cluster pending (migrating, creating, deleting) mapping state. Query cluster pending
   * mapping status, and optionally get cluster mappings.
   *
   * @param retrieveMappings Whether or not the cluster mappings should be retrieved
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default HasPendingMappingsResponse hasPendingMappings(@Nonnull Boolean retrieveMappings) {
    return LaunderThrowable.await(hasPendingMappingsAsync(retrieveMappings));
  }

  /**
   * Get cluster pending (migrating, creating, deleting) mapping state. Query cluster pending
   * mapping status and get cluster mappings.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default HasPendingMappingsResponse hasPendingMappings(RequestOptions requestOptions) {
    return LaunderThrowable.await(hasPendingMappingsAsync(requestOptions));
  }

  /**
   * Get cluster pending (migrating, creating, deleting) mapping state. Query cluster pending
   * mapping status, and optionally get cluster mappings.
   *
   * @param retrieveMappings Whether or not the cluster mappings should be retrieved
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default HasPendingMappingsResponse hasPendingMappings(
      @Nonnull Boolean retrieveMappings, RequestOptions requestOptions) {
    return LaunderThrowable.await(hasPendingMappingsAsync(retrieveMappings, requestOptions));
  }

  /**
   * Get cluster pending (migrating, creating, deleting) mapping state. Query cluster pending
   * mapping status and get cluster mappings.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<HasPendingMappingsResponse> hasPendingMappingsAsync() {
    return hasPendingMappingsAsync(false, null);
  }

  /**
   * Get cluster pending (migrating, creating, deleting) mapping state. Query cluster pending
   * mapping status, and optionally get cluster mappings.
   *
   * @param retrieveMappings Whether or not the cluster mappings should be retrieved
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<HasPendingMappingsResponse> hasPendingMappingsAsync(
      @Nonnull Boolean retrieveMappings) {
    return hasPendingMappingsAsync(retrieveMappings, null);
  }

  /**
   * Get cluster pending (migrating, creating, deleting) mapping state. Query cluster pending
   * mapping status and get cluster mappings.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<HasPendingMappingsResponse> hasPendingMappingsAsync(
      RequestOptions requestOptions) {
    return hasPendingMappingsAsync(false, requestOptions);
  }

  /**
   * Get cluster pending (migrating, creating, deleting) mapping state. Query cluster pending
   * mapping status, and optionally get cluster mappings.
   *
   * @param retrieveMappings Whether or not the cluster mappings should be retrieved
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<HasPendingMappingsResponse> hasPendingMappingsAsync(
      @Nonnull Boolean retrieveMappings, RequestOptions requestOptions) {

    Objects.requireNonNull(retrieveMappings, "retrieveMappings is required.");

    if (requestOptions == null) {
      requestOptions = new RequestOptions();
    }

    requestOptions.addExtraQueryParameters("getClusters", retrieveMappings.toString());

    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/clusters/mapping/pending",
            CallType.READ,
            HasPendingMappingsResponse.class,
            requestOptions);
  }
}
