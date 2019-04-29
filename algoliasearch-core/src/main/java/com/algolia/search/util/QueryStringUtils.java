package com.algolia.search.util;

import com.algolia.search.Defaults;
import com.algolia.search.models.apikeys.SecuredApiKeyRestriction;
import com.algolia.search.models.indexing.SearchParameters;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

public class QueryStringUtils {

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
   * Build a query string from its input
   *
   * @param map The map to convert to a query string
   * @param withoutLeadingMark Tells whether or not the leading interrogation mark should be used as
   *     a prefix of the query string.
   */
  public static String buildQueryString(Map<String, String> map, boolean withoutLeadingMark) {
    return withoutLeadingMark ? buildString(map).orElse("") : buildQueryString(map);
  }

  /**
   * Build a query string from its input
   *
   * @param map The map to convert to a query string
   */
  public static String buildQueryString(Map<String, String> map) {
    return buildString(map).map(s -> "?" + s).orElse("");
  }

  @SuppressWarnings("unchecked")
  public static String buildQueryAsQueryParams(SearchParameters query) {

    // This could be improved
    // We need to create a Map<String, Object> to keep track of the List<List<?>>
    Map<String, Object> map =
        Defaults.getObjectMapper().convertValue(query, new TypeReference<Map<String, Object>>() {});

    // Then creating a Map<String, String> to send query String builder
    Map<String, String> newMap =
        map.entrySet().stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    e -> {
                      if (e.getValue() instanceof List<?>) {

                        List<Object> tmpList = (List) e.getValue();

                        // Work around for nested List<List<?>> could be improved
                        if (tmpList.get(0) != null && tmpList.get(0) instanceof List<?>) {

                          List<List<?>> listOfList = (List) e.getValue();

                          List<?> flat =
                              listOfList.stream()
                                  .flatMap(List::stream)
                                  .collect(Collectors.toList());

                          return String.join(",", (List) flat);

                        } else {
                          // Handling List<?>
                          return String.join(",", (List) e.getValue());
                        }
                      } else {
                        // Handling other types Int,Float,String,Boolean etc..
                        return String.valueOf(e.getValue());
                      }
                    }));

    return buildQueryString(newMap, true);
  }

  static String buildRestrictionQueryString(SecuredApiKeyRestriction restriction) {
    Map<String, String> map =
        Defaults.getObjectMapper()
            .convertValue(restriction, new TypeReference<Map<String, String>>() {});
    return buildQueryString(map, true);
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
