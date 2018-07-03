package com.algolia.search.http;

import com.algolia.search.exceptions.AlgoliaException;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public abstract class AsyncAlgoliaHttpClient {

  public abstract <T> CompletableFuture<T> requestWithRetry(@Nonnull AlgoliaRequest<T> request);

  public abstract <T> CompletableFuture<T> requestAnalytics(@Nonnull AlgoliaRequest<T> request);

  public abstract void close() throws AlgoliaException;
}
