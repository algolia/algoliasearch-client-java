package com.algolia.search;

public interface AsyncBaseIndex<T> extends AbstractIndex<T> {

  AsyncAPIClient getApiClient();
}
