package com.algolia.search.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class QueryStringHelper {

  /**
   * Encode the given string
   *
   * @param s The string to encode
   * @return URL encoded string
   */
  public static String urlEncodeUTF8(String s) {
    try {
      return URLEncoder.encode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new UnsupportedOperationException(e);
    }
  }

  /**
   * Build a query string from a map Encodes the key and the value of the map Output a=3&b=2
   *
   * @param map The map to convert to a query string
   */
  public static String buildQueryString(Map<String, String> map, boolean withoutLeadingMark) {
    return withoutLeadingMark ? buildString(map).orElse("") : buildQueryString(map);
  }

  /**
   * Build a query string from a map Encodes the key and the value of the map Output ?a=3&b=2
   *
   * @param map The map to convert to a query string
   */
  public static String buildQueryString(Map<String, String> map) {
    return buildString(map).map(s -> "?" + s).orElse("");
  }

  private static Optional<String> buildString(Map<String, String> map) {
    return map.entrySet().stream()
        .map(p -> urlEncodeUTF8(p.getKey()) + "=" + urlEncodeUTF8(p.getValue()))
        .reduce((p1, p2) -> p1 + "&" + p2);
  }

  private static String buildString(Map<String, String> map, String... ignoreList) {
    HashMap<String, String> copy = new HashMap<>(map);
    copy.keySet().removeAll(Arrays.asList(ignoreList));
    return buildQueryString(copy);
  }
}
