package com.algolia.search.transport;

import com.algolia.search.models.CallType;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.objects.RequestOptions;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface IHttpTransport {

  /**
   * * Execute the given request asynchronously
   *
   * @param method The http method used for the request (Get,Post,etc.)
   * @param uri The URI of the endpoint
   * @param callType The Algolia call type of the request : read or write
   * @param data The data to send if any
   * @param returnClass The type that will be returned
   * @param <TResult> The type of the expected result
   * @param <TData> The type of the data sent
   */
  <TResult, TData> CompletableFuture<TResult> executeRequestAsync(
      @Nonnull HttpMethod method,
      @Nonnull String uri,
      @Nonnull CallType callType,
      TData data,
      Class<TResult> returnClass,
      RequestOptions requestOptions);

  /**
   * * Execute the given request synchronously
   *
   * @param method The http method used for the request (Get,Post,etc.)
   * @param uri The URI of the endpoint
   * @param callType The Algolia call type of the request : read or write
   * @param data The data to send if any
   * @param returnClass The type that will be returned
   * @param <TResult> The type of the expected result
   * @param <TData> The type of the data sent
   */
  <TResult, TData> TResult executeRequest(
      @Nonnull HttpMethod method,
      @Nonnull String uri,
      @Nonnull CallType callType,
      TData data,
      Class<TResult> returnClass,
      RequestOptions requestOptions)
      throws IOException;
}
