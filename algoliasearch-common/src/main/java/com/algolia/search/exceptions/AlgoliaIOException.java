package com.algolia.search.exceptions;

import java.io.IOException;

public class AlgoliaIOException extends AlgoliaException {

  public AlgoliaIOException(String host, IOException e) {
    super("Failed to query host [" + host + "]: " + e.getMessage(), e);
  }

}
