package com.algolia.search.http;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

public interface AlgoliaHttpResponse extends Closeable {

  int getStatusCode();

  Reader getBody() throws IOException;
}
