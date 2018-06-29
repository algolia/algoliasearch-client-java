package com.algolia.search;

import static com.fasterxml.jackson.core.JsonGenerator.Feature;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("WeakerAccess")
public interface Defaults {
  String ALGOLIANET_COM = "algolianet.com";
  String ALGOLIA_NET = "algolia.net";
  String ANALYTICS_HOST = "analytics.algolia.com";
  long MAX_TIME_MS_TO_WAIT = 10000L;

  Feature OBJECT_MAPPER_DEFAULT_FEATURE = Feature.AUTO_CLOSE_JSON_CONTENT;

  DeserializationFeature OBJECT_MAPPER_DEFAULT_DESERIALIZATION_FEATURE =
      DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

  ObjectMapper DEFAULT_OBJECT_MAPPER =
      new ObjectMapper()
          .enable(OBJECT_MAPPER_DEFAULT_FEATURE)
          .disable(OBJECT_MAPPER_DEFAULT_DESERIALIZATION_FEATURE);

  int READ_TIMEOUT_MS = 2 * 1000; // 2 seconds
  int CONNECT_TIMEOUT_MS = 2 * 1000; // 2 seconds
  int HOST_DOWN_TIMEOUT_MS = 5 * 60 * 1000; // 5 minutes
}
