package com.algolia.search.http;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

public interface AlgoliaHttpResponse {

  int getStatusCode();

  Reader getBody() throws IOException;
}
