package com.algolia.search.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.IOException;

/** Algolia Exception if there was an IOError linked to a host */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgoliaIOException extends AlgoliaException {

  public AlgoliaIOException(String host, IOException e) {
    super("Failed to query host [" + host + "]: " + e.getMessage(), e);
  }
}
