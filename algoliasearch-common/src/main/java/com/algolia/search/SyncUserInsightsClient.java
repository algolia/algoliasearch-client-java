package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.insights.InsightsEvent;
import com.algolia.search.inputs.insights.InsightsResult;
import com.algolia.search.objects.RequestOptions;
import com.google.common.primitives.UnsignedInteger;
import java.util.List;
import javax.annotation.Nonnull;

public class SyncUserInsightsClient {

  private String userToken;
  private SyncInsightsClient client;

  public SyncUserInsightsClient(String userToken, SyncInsightsClient client) {
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
  public InsightsResult clickedFilters(
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
  public InsightsResult clickedFilters(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> filters,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {

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
   * @throws AlgoliaException If APi Error
   */
  public InsightsResult clickedObjectIDs(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> objectIDs)
      throws AlgoliaException {
    return clickedObjectIDs(eventName, indexName, objectIDs, new RequestOptions());
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param requestOptions RequestOptions
   * @throws AlgoliaException If APi Error
   */
  public InsightsResult clickedObjectIDs(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {

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
   * @throws AlgoliaException If APi Error
   */
  public InsightsResult clickedObjectIDsAfterSearch(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull List<UnsignedInteger> positions,
      @Nonnull String queryID)
      throws AlgoliaException {
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
   * @throws AlgoliaException If APi Error
   */
  public InsightsResult clickedObjectIDsAfterSearch(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull List<UnsignedInteger> positions,
      @Nonnull String queryID,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {

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
   * @throws AlgoliaException If APi Error
   */
  public InsightsResult convertedObjectIDs(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> objectIDs)
      throws AlgoliaException {
    return convertedObjectIDs(eventName, indexName, objectIDs, new RequestOptions());
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param requestOptions RequestOptions
   * @throws AlgoliaException If APi Error
   */
  public InsightsResult convertedObjectIDs(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
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
   * @throws AlgoliaException If APi Error
   */
  public InsightsResult convertedObjectIDsAfterSearch(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull String queryID)
      throws AlgoliaException {
    return convertedObjectIDsAfterSearch(
        eventName, indexName, objectIDs, queryID, new RequestOptions());
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param queryID The query Id
   * @param requestOptions RequestOptions
   * @throws AlgoliaException If APi Error
   */
  public InsightsResult convertedObjectIDsAfterSearch(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull String queryID,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
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
   * @throws AlgoliaException If API error
   */
  public InsightsResult convertedFilters(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> filters)
      throws AlgoliaException {
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
   * @throws AlgoliaException If API error
   */
  public InsightsResult convertedFilters(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> filters,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
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
   * @throws AlgoliaException If APi Error
   */
  // view
  public InsightsResult viewedFilters(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> filters)
      throws AlgoliaException {
    return viewedFilters(eventName, indexName, filters, new RequestOptions());
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param filters Filters parameters
   * @param requestOptions RequestOptions
   * @throws AlgoliaException If APi Error
   */
  public InsightsResult viewedFilters(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> filters,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
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
   * @throws AlgoliaException If APi Error
   */
  public InsightsResult viewedObjectIDs(
      @Nonnull String eventName, @Nonnull String indexName, @Nonnull List<String> objectIDs)
      throws AlgoliaException {
    return viewedObjectIDs(eventName, indexName, objectIDs, new RequestOptions());
  }

  /**
   * @param eventName The Event Name
   * @param indexName The Index Name
   * @param objectIDs List of objectId
   * @param requestOptions RequestOptions
   * @throws AlgoliaException If APi Error
   */
  public InsightsResult viewedObjectIDs(
      @Nonnull String eventName,
      @Nonnull String indexName,
      @Nonnull List<String> objectIDs,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
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
