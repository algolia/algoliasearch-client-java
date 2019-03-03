package com.algolia.search.integration;

import com.algolia.search.clients.AnalyticsClient;
import com.algolia.search.clients.SearchClient;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AlgoliaBaseIntegrationTest {

  protected static SearchClient searchClient;
  protected static AnalyticsClient analyticsClient;

  private static String ALGOLIA_APPLICATION_ID_1 = System.getenv("ALGOLIA_APPLICATION_ID_1");
  private static String ALGOLIA_API_KEY_1 = System.getenv("ALGOLIA_ADMIN_KEY_1");
  private static String ALGOLIA_SEARCH_KEY_1 = System.getenv("ALGOLIA_SEARCH_KEY_1");
  private static String ALGOLIA_APPLICATION_ID_2 = System.getenv("ALGOLIA_APPLICATION_ID_2");
  private static String ALGOLIA_API_KEY_2 = System.getenv("ALGOLIA_ADMIN_KEY_2");
  private static String ALGOLIA_APPLICATION_ID_MCM = System.getenv("ALGOLIA_APPLICATION_ID_MCM");
  private static String ALGOLIA_ADMIN_KEY_MCM = System.getenv("ALGOLIA_ADMIN_KEY_MCM");

  private static String osName = System.getProperty("os.name").trim();
  private static String userName = System.getProperty("user.name");
  private static String javaVersion = System.getProperty("java.version");

  @BeforeAll
  static void globalInit() throws Exception {
    checkEnvironmentVariable();
    searchClient = new SearchClient(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1);
    analyticsClient = new AnalyticsClient(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1);
  }

  @AfterAll
  static void globalTearDown() {}

  protected static String getTestIndexName(String indexName) {
    OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
    return String.format("java_jvm_%s_%s_%s_%s_%s", javaVersion, utc, osName, userName, indexName);
  }

  private static void checkEnvironmentVariable() throws Exception {
    if (ALGOLIA_APPLICATION_ID_1 == null || ALGOLIA_APPLICATION_ID_1.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID is not defined or empty");
    }

    if (ALGOLIA_API_KEY_1 == null || ALGOLIA_API_KEY_1.isEmpty()) {
      throw new Exception("ALGOLIA_API_KEY is not defined or empty");
    }

    if (ALGOLIA_SEARCH_KEY_1 == null || ALGOLIA_SEARCH_KEY_1.isEmpty()) {
      throw new Exception("ALGOLIA_SEARCH_KEY_1 is not defined or empty");
    }

    if (ALGOLIA_APPLICATION_ID_2 == null || ALGOLIA_APPLICATION_ID_2.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID_2 is not defined or empty");
    }

    if (ALGOLIA_API_KEY_2 == null || ALGOLIA_API_KEY_2.isEmpty()) {
      throw new Exception("ALGOLIA_API_KEY_2 is not defined or empty");
    }

    if (ALGOLIA_APPLICATION_ID_MCM == null || ALGOLIA_APPLICATION_ID_MCM.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID_MCM is not defined or empty");
    }

    if (ALGOLIA_ADMIN_KEY_MCM == null || ALGOLIA_ADMIN_KEY_MCM.isEmpty()) {
      throw new Exception("ALGOLIA_ADMIN_KEY_MCM is not defined or empty");
    }
  }
}
