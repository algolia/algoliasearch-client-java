package com.algolia.search.util;

import com.algolia.search.models.HttpResponse;

public class HttpStatusCodeUtils {

  public static boolean isSuccess(HttpResponse response) {
    return isSuccess(response.getHttpStatusCode());
  }

  public static boolean isSuccess(int httpStatusCode) {
    return httpStatusCode / 100 == 2;
  }
}
