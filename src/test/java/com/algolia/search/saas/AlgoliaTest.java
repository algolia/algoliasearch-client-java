package com.algolia.search.saas;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;

public class AlgoliaTest {

  protected static final String indexName = safe_name("àlgol?à-java");

  protected static APIClient client;
  protected static Index index;

  static String safe_name(String name) {
    if (System.getenv("TRAVIS") != null) {
      String[] id = System.getenv("TRAVIS_JOB_NUMBER").split("\\.");
      return name + "_travis" + id[id.length - 1];
    }
    return name;

  }

  static boolean isPresent(JSONArray array, String search, String attr) throws JSONException {
    boolean isPresent = false;
    for (int i = 0; i < array.length(); ++i) {
      isPresent = isPresent || array.getJSONObject(i).getString(attr).equals(search);
    }
    return isPresent;
  }

  protected void waitForIt() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException ignored) {
    }
  }

  @BeforeClass
  public static void init() {
    String applicationID = System.getenv("ALGOLIA_APPLICATION_ID");
    String apiKey = System.getenv("ALGOLIA_API_KEY");
    Assume.assumeFalse("You must set environement variables ALGOLIA_APPLICATION_ID and ALGOLIA_API_KEY to run the tests.", applicationID == null || apiKey == null);
    client = new APIClient(applicationID, apiKey);
    index = client.initIndex(indexName);
  }

  @AfterClass
  public static void dispose() {
    try {
      client.deleteIndex(indexName);
    } catch (AlgoliaException e) {
      // Not fatal
    }
  }

  @Before
  public void eachInit() {
    try {
      index.clearIndex();
    } catch (AlgoliaException e) {
      //Normal
    }
  }
}
