package com.algolia.search.helpers;

public class HttpStatusCodeHelper {

  public static boolean isSuccess(int httpStatusCode) {
    return httpStatusCode / 100 == 2;
  }
}
