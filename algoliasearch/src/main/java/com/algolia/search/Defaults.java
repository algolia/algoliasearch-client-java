package com.algolia.search;

import static com.fasterxml.jackson.core.JsonGenerator.Feature;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Defaults {

  public Defaults() {}

  private static class Holder {
    // Jackson Configuration
    private static final ObjectMapper DEFAULT_OBJECT_MAPPER =
        new ObjectMapper()
            .registerModule(new JavaTimeModule()) // Registering JavaTimeModule to handle JDK8 dates
            .enable(Feature.AUTO_CLOSE_JSON_CONTENT)
            .enable(
                DeserializationFeature
                    .ACCEPT_SINGLE_VALUE_AS_ARRAY) // Allow Jackson to deserialize List/Array with
            // one
            // element
            .disable(
                SerializationFeature
                    .WRITE_DATES_AS_TIMESTAMPS) // Disabling it to handle date serialization in
            // POJOS
            .disable(
                SerializationFeature
                    .WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS) // Nano seconds not supported by the
            // engine
            .disable(
                DeserializationFeature
                    .READ_DATE_TIMESTAMPS_AS_NANOSECONDS) // Nano seconds not supported by the
            // engine
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  public static ObjectMapper getObjectMapper() {
    return Holder.DEFAULT_OBJECT_MAPPER;
  }

  public static final long MAX_TIME_MS_TO_WAIT = 10000L;
  public static final int READ_TIMEOUT_MS = 5 * 1000; // 5 seconds
  public static final int WRITE_TIMEOUT_MS = 30 * 1000; // 30 seconds
  public static final int CONNECT_TIMEOUT_MS = 2 * 1000; // 2 seconds
}
