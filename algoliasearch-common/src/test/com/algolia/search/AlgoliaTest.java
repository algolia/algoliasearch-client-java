package com.algolia.search;

import org.junit.BeforeClass;

public abstract class AlgoliaTest {

  public abstract APIClientBuilder get(String applicationID, String apiKey);

  protected APIClient client;

  @BeforeClass
  public void beforeAll() {
    String applicationID = System.getenv("ALGOLIA_APPLICATION_ID");
    String apiKey = System.getenv("ALGOLIA_API_KEY");

    client = get(applicationID, apiKey).build();
  }

}
