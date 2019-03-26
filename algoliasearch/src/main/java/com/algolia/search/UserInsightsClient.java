package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.insights.InsightsEvent;
import com.algolia.search.models.insights.InsightsResult;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

@SuppressWarnings("WeakerAccess")
public final class UserInsightsClient {

  private final String userToken;
  private final InsightsClient insightsClient;

  public UserInsightsClient(String userToken, InsightsClient insightsClient) {
    this.userToken = userToken;
    this.insightsClient = insightsClient;
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param filters Filters parameters
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> clickedFiltersAsync(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> filters) {
    return clickedFiltersAsync(eventName, indexName, filters, null);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param filters Filters parameters
   * @param requestOptions RequestOptions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> clickedFiltersAsync(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> filters,
      RequestOptions requestOptions) {

    InsightsEvent event =
        new InsightsEvent()
            .setEventType("click")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setFilters(filters);

    return insightsClient.sendEventsAsync(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> clickedObjectIDsAsync(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> objectIDs) {
    return clickedObjectIDsAsync(eventName, indexName, objectIDs, null);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param requestOptions RequestOptions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> clickedObjectIDsAsync(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      RequestOptions requestOptions) {

    InsightsEvent event =
        new InsightsEvent()
            .setEventType("click")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setObjectIDs(objectIDs);

    return insightsClient.sendEventsAsync(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param positions List of position
   * @param queryID The query Id
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> clickedObjectIDsAfterSearchAsync(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull List<Long> positions,
      @Nonnull String queryID) {
    return clickedObjectIDsAfterSearchAsync(
        eventName, indexName, objectIDs, positions, queryID, null);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param positions List of position
   * @param queryID The query Id
   * @param requestOptions RequestOptions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> clickedObjectIDsAfterSearchAsync(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull List<Long> positions,
      @Nonnull String queryID,
      RequestOptions requestOptions) {

    InsightsEvent event =
        new InsightsEvent()
            .setEventType("click")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setObjectIDs(objectIDs)
            .setPositions(positions)
            .setQueryID(queryID);

    return insightsClient.sendEventsAsync(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> convertedObjectIDsAsync(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> objectIDs) {
    return convertedObjectIDsAsync(eventName, indexName, objectIDs, null);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param requestOptions RequestOptions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> convertedObjectIDsAsync(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      RequestOptions requestOptions) {
    InsightsEvent event =
        new InsightsEvent()
            .setEventType("conversion")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setObjectIDs(objectIDs);

    return insightsClient.sendEventsAsync(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param queryID The query Id
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> convertedObjectIDsAfterSearchAsync(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull String queryID) {
    return convertedObjectIDsAfterSearchAsync(eventName, indexName, objectIDs, queryID, null);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param queryID The query Id
   * @param requestOptions RequestOptions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> convertedObjectIDsAfterSearchAsync(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull String queryID,
      RequestOptions requestOptions) {
    InsightsEvent event =
        new InsightsEvent()
            .setEventType("conversion")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setObjectIDs(objectIDs)
            .setQueryID(queryID);

    return insightsClient.sendEventsAsync(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The index name
   * @param filters List of filters
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> convertedFiltersAsync(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> filters) {
    InsightsEvent event =
        new InsightsEvent()
            .setEventType("conversion")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setFilters(filters);

    return insightsClient.sendEventsAsync(event, null);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The index name
   * @param filters List of filters
   * @param requestOptions RequestOptions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> convertedFiltersAsync(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> filters,
      RequestOptions requestOptions) {
    InsightsEvent event =
        new InsightsEvent()
            .setEventType("conversion")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setFilters(filters);

    return insightsClient.sendEventsAsync(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param filters Filters parameters
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> viewedFiltersAsync(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> filters) {
    return viewedFiltersAsync(eventName, indexName, filters, null);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param filters Filters parameters
   * @param requestOptions RequestOptions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> viewedFiltersAsync(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> filters,
      RequestOptions requestOptions) {
    InsightsEvent event =
        new InsightsEvent()
            .setEventType("view")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setFilters(filters);

    return insightsClient.sendEventsAsync(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> viewedObjectIDsAsync(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> objectIDs) {
    return viewedObjectIDsAsync(eventName, indexName, objectIDs, null);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param requestOptions RequestOptions
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  public CompletableFuture<InsightsResult> viewedObjectIDsAsync(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      RequestOptions requestOptions) {
    InsightsEvent event =
        new InsightsEvent()
            .setEventType("view")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setObjectIDs(objectIDs);

    return insightsClient.sendEventsAsync(event, requestOptions);
  }
}
