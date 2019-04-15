package com.algolia.search.util;

public class HttpStatusCodeUtils {

  public static boolean isSuccess(int httpStatusCode) {
    return httpStatusCode / 100 == 2;
  }
}
