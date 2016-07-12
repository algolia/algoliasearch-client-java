package com.algolia.search.http;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public abstract class AsyncAlgoliaHttpClient {

  public abstract <T> CompletableFuture<T> requestWithRetry(@Nonnull AlgoliaRequest<T> request);

}
