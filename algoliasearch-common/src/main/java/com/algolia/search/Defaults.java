package com.algolia.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("WeakerAccess")
public interface Defaults {
  String ALGOLIANET_COM = "algolianet.com";
  String ALGOLIA_NET = "algolia.net";

  ObjectMapper DEFAULT_OBJECT_MAPPER =
    new ObjectMapper()
      .setSerializationInclusion(JsonInclude.Include.NON_NULL)
      .enable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)
      .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

  int READ_TIMEOUT_MS = 2000;
  int CONNECT_TIMEOUT_MS = 2000;

}
