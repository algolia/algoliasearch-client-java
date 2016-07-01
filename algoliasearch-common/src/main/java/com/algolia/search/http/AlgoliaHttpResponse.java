package com.algolia.search.http;

import java.io.IOException;
import java.lang.reflect.Type;

public interface AlgoliaHttpResponse {

  public int getStatusCode();

  public String getBody() throws IOException;
}
