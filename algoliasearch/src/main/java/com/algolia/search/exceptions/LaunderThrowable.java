package com.algolia.search.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class LaunderThrowable {

  public static <T> T unwrap(CompletableFuture<T> f) {
    try {
      return f.get();
    } catch (InterruptedException | ExecutionException e) {
      throw LaunderThrowable.launderThrowable(e);
    }
  }

  public static RuntimeException launderThrowable(Throwable t) {

    if (t.getCause() != null) {
      if (t.getCause() instanceof AlgoliaApiException) {
        throw (AlgoliaApiException) t.getCause();
      }

      if (t.getCause() instanceof AlgoliaRetryException) {
        throw (AlgoliaRetryException) t.getCause();
      }

      if (t.getCause() instanceof AlgoliaRuntimeException) {
        throw (AlgoliaRuntimeException) t.getCause();
      }
    }

    throw new AlgoliaRuntimeException(t);
  }
}
