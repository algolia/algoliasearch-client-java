package com.algolia.search;

public interface SyncBaseIndex<T> extends AbstractIndex<T> {

  APIClient getApiClient();
}
