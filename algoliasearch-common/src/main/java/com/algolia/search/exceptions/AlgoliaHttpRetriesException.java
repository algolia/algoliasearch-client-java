package com.algolia.search.exceptions;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AlgoliaHttpRetriesException extends AlgoliaException {

  /**
   * List of exception if all retries failed
   */
  private List<IOException> ioExceptionList;

  public AlgoliaHttpRetriesException(String message, List<IOException> ioExceptionList) {
    super(message + ", exceptions: [" + String.join(",", ioExceptionList.stream().map(Throwable::getMessage).collect(Collectors.toList())) + "]");
    this.ioExceptionList = ioExceptionList;
  }

  @SuppressWarnings("unused")
  public List<IOException> getIoExceptionList() {
    return ioExceptionList;
  }
}
