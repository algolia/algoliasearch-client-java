package com.algolia.search.integration;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TestHelpers {
  public static String userName = System.getProperty("user.name");
  private static String osName = System.getProperty("os.name").trim();
  private static String javaVersion = System.getProperty("java.version");

  public static String getTestIndexName(String indexName) {
    ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
    return String.format("java_jvm_%s_%s_%s_%s_%s", javaVersion, utc, osName, userName, indexName);
  }

  public static String getMcmUserId() {
    ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
    return String.format(
        "java-%s-%s", DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").format(utc), userName);
  }
}
