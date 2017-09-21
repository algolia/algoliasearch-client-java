package com.algolia.search.exceptions;

import java.io.IOException;

/** Algolia Exception if there was an IOError linked to a host */
public class AlgoliaIOException extends AlgoliaException {

  public AlgoliaIOException(String host, IOException e) {
    super("Failed to query host [" + host + "]: " + e.getMessage(), e);
  }
}
