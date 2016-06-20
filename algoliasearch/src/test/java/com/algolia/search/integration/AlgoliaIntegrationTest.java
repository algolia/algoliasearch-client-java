package com.algolia.search.integration;

import com.algolia.search.APIClient;
import com.algolia.search.ApacheAPIClientBuilder;
import org.junit.Before;

class AlgoliaIntegrationTest {

  private static String APPLICATION_ID = System.getenv("APPLICATION_ID");
  private static String API_KEY = System.getenv("API_KEY");
  static APIClient client = new ApacheAPIClientBuilder(APPLICATION_ID, API_KEY).build();

  @Before
  public void checkEnvVariables() throws Exception {
    if (APPLICATION_ID == null || APPLICATION_ID.isEmpty()) {
      throw new Exception("APPLICATION_ID is not defined or empty");
    }
    if (API_KEY == null || API_KEY.isEmpty()) {
      throw new Exception("API_KEY is not defined or empty");
    }
  }

}
