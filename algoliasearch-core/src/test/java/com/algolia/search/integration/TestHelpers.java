package com.algolia.search.integration;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TestHelpers {

  public static String ALGOLIA_APPLICATION_ID_1 = System.getenv("ALGOLIA_APPLICATION_ID_1");
  public static String ALGOLIA_ADMIN_KEY_1 = System.getenv("ALGOLIA_ADMIN_KEY_1");
  public static String ALGOLIA_SEARCH_KEY_1 = System.getenv("ALGOLIA_SEARCH_KEY_1");
  public static String ALGOLIA_APPLICATION_ID_2 = System.getenv("ALGOLIA_APPLICATION_ID_2");
  public static String ALGOLIA_ADMIN_KEY_2 = System.getenv("ALGOLIA_ADMIN_KEY_2");
  public static String ALGOLIA_APPLICATION_ID_MCM = System.getenv("ALGOLIA_APPLICATION_ID_MCM");
  public static String ALGOLIA_ADMIN_KEY_MCM = System.getenv("ALGOLIA_ADMIN_KEY_MCM");

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

  public static void checkEnvironmentVariable() throws Exception {
    if (ALGOLIA_APPLICATION_ID_1 == null || ALGOLIA_APPLICATION_ID_1.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID_1 is not defined or empty");
    }

    if (ALGOLIA_ADMIN_KEY_1 == null || ALGOLIA_ADMIN_KEY_1.isEmpty()) {
      throw new Exception("ALGOLIA_ADMIN_KEY_1 is not defined or empty");
    }

    if (ALGOLIA_SEARCH_KEY_1 == null || ALGOLIA_SEARCH_KEY_1.isEmpty()) {
      throw new Exception("ALGOLIA_SEARCH_KEY_1 is not defined or empty");
    }

    if (ALGOLIA_APPLICATION_ID_2 == null || ALGOLIA_APPLICATION_ID_2.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID_2 is not defined or empty");
    }

    if (ALGOLIA_ADMIN_KEY_2 == null || ALGOLIA_ADMIN_KEY_2.isEmpty()) {
      throw new Exception("ALGOLIA_ADMIN_KEY_2 is not defined or empty");
    }

    if (ALGOLIA_APPLICATION_ID_MCM == null || ALGOLIA_APPLICATION_ID_MCM.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID_MCM is not defined or empty");
    }

    if (ALGOLIA_ADMIN_KEY_MCM == null || ALGOLIA_ADMIN_KEY_MCM.isEmpty()) {
      throw new Exception("ALGOLIA_ADMIN_KEY_MCM is not defined or empty");
    }
  }

  public static void retry(RetryableFunc f, Duration delay, int maxNbRetries) throws Exception {
    for (int i = 0; i < maxNbRetries; i++) {
      boolean shouldRetry = f.method();
      if (!shouldRetry) {
        return;
      }
      Thread.sleep(delay.toMillis());
    }
    throw new Exception("reached the maximum number of retries");
  }
}
