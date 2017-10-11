package com.algolia.search.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.stream.Collectors;

/** Algolia Exception if all retries failed */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgoliaHttpRetriesException extends AlgoliaException {

  /** List of exception if all retries failed */
  private List<AlgoliaIOException> ioExceptionList;

  public AlgoliaHttpRetriesException(String message, List<AlgoliaIOException> ioExceptionList) {
    super(
        message
            + ", exceptions: ["
            + String.join(
                ",",
                ioExceptionList.stream().map(Throwable::getMessage).collect(Collectors.toList()))
            + "]");
    this.ioExceptionList = ioExceptionList;
  }

  @SuppressWarnings("unused")
  public List<AlgoliaIOException> getIoExceptionList() {
    return ioExceptionList;
  }
}
