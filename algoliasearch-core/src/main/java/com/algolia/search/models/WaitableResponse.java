package com.algolia.search.models;

/**
 * All write operations in Algolia are asynchronous by design.
 * https://www.algolia.com/doc/api-reference/api-methods/wait-task/
 */
public interface WaitableResponse {

  /**
   * Wait for a task to complete before executing the next line of code, to synchronize index
   * updates.
   */
  void waitTask();
}
