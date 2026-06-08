package com.algolia.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.TaskStatusResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskUtilsTest {

  private static TaskStatusResponse status(String status) {
    return new TaskStatusResponse().setStatus(status);
  }

  @Test
  @DisplayName("waitTask surfaces polling failures instead of silently exiting")
  void propagatesExecutionException() {
    AlgoliaApiException cause = new AlgoliaApiException("boom", 500);
    BiFunction<Long, RequestOptions, CompletableFuture<TaskStatusResponse>> failing =
        (taskId, requestOptions) -> {
          CompletableFuture<TaskStatusResponse> future = new CompletableFuture<>();
          future.completeExceptionally(cause);
          return future;
        };

    // Before the fix this returned void (the exception was swallowed by a `break`), which let
    // callers such as replaceAllObjects proceed as if the task had completed. It must now throw
    // the unwrapped exception so the caller can abort.
    assertThatThrownBy(() -> TaskUtils.waitTask(1L, 1L, null, failing))
        .isInstanceOf(AlgoliaApiException.class)
        .isSameAs(cause);
  }

  @Test
  @DisplayName("waitTask restores the interrupt flag and throws when the thread is interrupted")
  void restoresInterruptFlagOnInterruption() {
    BiFunction<Long, RequestOptions, CompletableFuture<TaskStatusResponse>> neverCompletes =
        (taskId, requestOptions) -> new CompletableFuture<>();

    Thread.currentThread().interrupt();
    try {
      assertThatThrownBy(() -> TaskUtils.waitTask(1L, 1L, null, neverCompletes))
          .isInstanceOf(AlgoliaRuntimeException.class);
      assertThat(Thread.currentThread().isInterrupted()).isTrue();
    } finally {
      // Clear the flag so it does not leak into other tests.
      Thread.interrupted();
    }
  }

  @Test
  @DisplayName("waitTask keeps polling until the task is published")
  void retriesUntilPublished() {
    AtomicInteger calls = new AtomicInteger();
    BiFunction<Long, RequestOptions, CompletableFuture<TaskStatusResponse>> eventually =
        (taskId, requestOptions) ->
            CompletableFuture.completedFuture(
                status(calls.incrementAndGet() < 3 ? "notPublished" : "published"));

    assertThatCode(() -> TaskUtils.waitTask(1L, 1L, null, eventually)).doesNotThrowAnyException();
    assertThat(calls.get()).isEqualTo(3);
  }
}
