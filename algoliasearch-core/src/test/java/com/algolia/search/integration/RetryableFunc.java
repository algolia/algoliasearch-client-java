package com.algolia.search.integration;

@FunctionalInterface
public interface RetryableFunc {
  boolean method();
}
