package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.insights.InsightsEvent;
import com.algolia.search.inputs.insights.InsightsResult;
import com.algolia.search.objects.RequestOptions;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public class AsyncUserInsightsClient {

  private String userToken;
  private AsyncInsightsClient client;

  public AsyncUserInsightsClient(String userToken, AsyncInsightsClient client) {
    this.userToken = userToken;
    this.client = client;
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param filters Filters parameters
   * @throws AlgoliaException If APi Error
   */
  // click
  public CompletableFuture<InsightsResult> clickedFilters(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> filters)
      throws AlgoliaException {
    return clickedFilters(eventName, indexName, filters, new RequestOptions());
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param filters Filters parameters
   * @param requestOptions RequestOptions
   * @throws AlgoliaException If APi Error
   */
  public CompletableFuture<InsightsResult> clickedFilters(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> filters,
      @Nonnull RequestOptions requestOptions) {

    InsightsEvent event =
        new InsightsEvent()
            .setEventType("click")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setFilters(filters);

    return client.sendEvent(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   */
  public CompletableFuture<InsightsResult> clickedObjectIDs(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> objectIDs) {
    return clickedObjectIDs(eventName, indexName, objectIDs, new RequestOptions());
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param requestOptions RequestOptions
   */
  public CompletableFuture<InsightsResult> clickedObjectIDs(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull RequestOptions requestOptions) {

    InsightsEvent event =
        new InsightsEvent()
            .setEventType("click")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setObjectIDs(objectIDs);

    return client.sendEvent(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param positions List of position
   * @param queryID The query Id
   */
  public CompletableFuture<InsightsResult> clickedObjectIDsAfterSearch(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull List<Long> positions,
      @Nonnull String queryID) {
    return clickedObjectIDsAfterSearch(
        eventName, indexName, objectIDs, positions, queryID, new RequestOptions());
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param positions List of position
   * @param queryID The query Id
   * @param requestOptions RequestOptions
   */
  public CompletableFuture<InsightsResult> clickedObjectIDsAfterSearch(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull List<Long> positions,
      @Nonnull String queryID,
      @Nonnull RequestOptions requestOptions) {

    InsightsEvent event =
        new InsightsEvent()
            .setEventType("click")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setObjectIDs(objectIDs)
            .setPositions(positions)
            .setQueryId(queryID);

    return client.sendEvent(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   */
  public CompletableFuture<InsightsResult> convertedObjectIDs(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> objectIDs) {
    return convertedObjectIDs(eventName, indexName, objectIDs, new RequestOptions());
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param requestOptions RequestOptions
   */
  public CompletableFuture<InsightsResult> convertedObjectIDs(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull RequestOptions requestOptions) {
    InsightsEvent event =
        new InsightsEvent()
            .setEventType("conversion")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setObjectIDs(objectIDs);

    return client.sendEvent(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param queryID The query Id
   */
  public CompletableFuture<InsightsResult> convertedObjectIDsAfterSearch(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull String queryID) {
    return convertedObjectIDsAfterSearch(
        eventName, indexName, objectIDs, queryID, new RequestOptions());
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param queryID The query Id
   * @param requestOptions RequestOptions
   */
  public CompletableFuture<InsightsResult> convertedObjectIDsAfterSearch(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull String queryID,
      @Nonnull RequestOptions requestOptions) {
    InsightsEvent event =
        new InsightsEvent()
            .setEventType("conversion")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setObjectIDs(objectIDs)
            .setQueryId(queryID);

    return client.sendEvent(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The index name
   * @param filters List of filters
   */
  public CompletableFuture<InsightsResult> convertedFilters(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> filters) {
    InsightsEvent event =
        new InsightsEvent()
            .setEventType("conversion")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setFilters(filters);

    return client.sendEvent(event);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The index name
   * @param filters List of filters
   * @param requestOptions RequestOptions
   */
  public CompletableFuture<InsightsResult> convertedFilters(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> filters,
      @Nonnull RequestOptions requestOptions) {
    InsightsEvent event =
        new InsightsEvent()
            .setEventType("conversion")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setFilters(filters);

    return client.sendEvent(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param filters Filters parameters
   */

  // view
  public CompletableFuture<InsightsResult> viewedFilters(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> filters) {
    return viewedFilters(eventName, indexName, filters, new RequestOptions());
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param filters Filters parameters
   * @param requestOptions RequestOptions
   */
  public CompletableFuture<InsightsResult> viewedFilters(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> filters,
      @Nonnull RequestOptions requestOptions) {
    InsightsEvent event =
        new InsightsEvent()
            .setEventType("view")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setFilters(filters);

    return client.sendEvent(event, requestOptions);
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   */
  public CompletableFuture<InsightsResult> viewedObjectIDs(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> objectIDs) {
    return viewedObjectIDs(eventName, indexName, objectIDs, new RequestOptions());
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param requestOptions RequestOptions
   */
  public CompletableFuture<InsightsResult> viewedObjectIDs(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull RequestOptions requestOptions) {
    InsightsEvent event =
        new InsightsEvent()
            .setEventType("view")
            .setUserToken(userToken)
            .setEventName(eventName)
            .setIndex(indexName)
            .setObjectIDs(objectIDs);

    return client.sendEvent(event, requestOptions);
  }
}
