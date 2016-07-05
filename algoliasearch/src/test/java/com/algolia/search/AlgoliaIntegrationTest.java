package com.algolia.search;

import org.junit.Before;

public class AlgoliaIntegrationTest {

  protected static String APPLICATION_ID = System.getenv("APPLICATION_ID");
  protected static String API_KEY = System.getenv("API_KEY");
  protected static APIClient client = new ApacheAPIClientBuilder(APPLICATION_ID, API_KEY).build();

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
