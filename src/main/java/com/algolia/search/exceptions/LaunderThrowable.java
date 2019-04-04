package com.algolia.search.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("WeakerAccess")
public class LaunderThrowable {

  /**
   * Performs a get() on the asynchronous method. Launders both Interrupted and Execution exception
   * to business exception
   *
   * @param f The CompletableFuture to wait On
   */
  public static <T> T unwrap(CompletableFuture<T> f) {
    try {
      return f.get();
    } catch (InterruptedException | ExecutionException e) {
      throw LaunderThrowable.launderThrowable(e);
    }
  }

  /** Launders both Interrupted and Execution exception to business exception */
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
