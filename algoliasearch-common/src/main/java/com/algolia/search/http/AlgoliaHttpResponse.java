package com.algolia.search.http;

import java.io.IOException;
import java.lang.reflect.Type;

public interface AlgoliaHttpResponse {

  int getStatusCode();

  <T> T parseAs(Class<T> klass) throws IOException;

  Object parseAs(Type type) throws IOException;
}
