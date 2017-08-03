package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;

public abstract class SyncAlgoliaIntegrationTest {

  public APIClient client;
  protected String APPLICATION_ID = System.getenv("APPLICATION_ID");
  protected String API_KEY = System.getenv("API_KEY");

  @Before
  public void checkEnvVariables() throws Exception {
    if (APPLICATION_ID == null || APPLICATION_ID.isEmpty()) {
      throw new Exception("APPLICATION_ID is not defined or empty");
    }
    if (API_KEY == null || API_KEY.isEmpty()) {
      throw new Exception("API_KEY is not defined or empty");
    }
    client = createInstance(APPLICATION_ID, API_KEY);
  }

  public abstract APIClient createInstance(String appId, String apiKey);

  public abstract APIClient createInstance(String appId, String apiKey, ObjectMapper objectMapper);

}
