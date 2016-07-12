package com.algolia.search.integration;

import com.algolia.search.APIClient;
import com.algolia.search.AppEngineAPIClientBuilder;
import com.algolia.search.integration.sync.SyncSettingsTest;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalURLFetchServiceTestConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class AppEngineSettingsTest extends SyncSettingsTest {

  private static final LocalServiceTestHelper helper =
    new LocalServiceTestHelper(new LocalURLFetchServiceTestConfig());

  @BeforeClass
  public static void before() {
    helper.setUp();
  }

  @AfterClass
  public static void after() {
    helper.tearDown();
  }

  @Override
  public APIClient createInstance(String appId, String apiKey) {
    return new AppEngineAPIClientBuilder(appId, apiKey).build();
  }

}
