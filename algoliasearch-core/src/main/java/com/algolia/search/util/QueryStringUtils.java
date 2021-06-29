package com.algolia.search.util;

import com.algolia.search.Defaults;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.models.apikeys.SecuredApiKeyRestriction;
import com.algolia.search.models.indexing.SearchParameters;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

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
                        if (!tmpList.isEmpty() && tmpList.get(0) instanceof List<?>) {

                          List<List<Object>> listOfList = (List) e.getValue();

                          return "["
                              + listOfList.stream()
                                  .map(
                                      arr ->
                                          "["
                                              + arr.stream()
                                                  .map(QueryStringUtils::formatParameters)
                                                  .collect(Collectors.joining(","))
                                              + "]")
                                  .collect(Collectors.joining(","))
                              + "]";

                        } else {

                          // Handling around precision special case
                          if (e.getKey().equals("aroundPrecision")) {
                            try {
                              return Defaults.getObjectMapper().writeValueAsString(e.getValue());
                            } catch (JsonProcessingException ex) {
                              throw new AlgoliaRuntimeException(
                                  "Error while serializing the request", ex);
                            }
                          }

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

  static String buildRestrictionQueryString(@Nonnull final SecuredApiKeyRestriction restriction) {

    Map<String, String> restrictionMap =
        Defaults.getObjectMapper()
            .convertValue(restriction, new TypeReference<Map<String, String>>() {});

    if (restriction.getQuery() != null) {
      restrictionMap.remove("query");
      return buildQueryString(restrictionMap, true)
          + "&"
          + buildQueryAsQueryParams(restriction.getQuery());
    }

    return buildQueryString(restrictionMap, true);
  }

  private static String formatParameters(Object parameter) {

    if (parameter instanceof Float) {
      return parameter.toString();
    }

    return "\"" + parameter.toString() + "\"";
  }

  private static Optional<String> buildString(Map<String, String> map) {
    return map.entrySet().stream()
        .map(p -> urlEncodeUTF8(p.getKey()) + "=" + urlEncodeUTF8(p.getValue()))
        .reduce((p1, p2) -> p1 + "&" + p2);
  }
}
