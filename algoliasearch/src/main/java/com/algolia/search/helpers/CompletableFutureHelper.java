package com.algolia.search.helpers;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelper {
  public static <T> CompletableFuture<T> failedFuture(Throwable t) {
    final CompletableFuture<T> cf = new CompletableFuture<>();
    cf.completeExceptionally(t);
    return cf;
  }
}
