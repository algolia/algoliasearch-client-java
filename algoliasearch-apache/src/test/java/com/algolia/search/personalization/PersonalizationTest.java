package com.algolia.search.personalization;

import com.algolia.search.DefaultPersonalizationClient;
import com.algolia.search.IntegrationTestExtension;
import com.algolia.search.PersonalizationClient;
import com.algolia.search.integration.TestHelpers;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({IntegrationTestExtension.class})
class PersonalizationTest
    extends com.algolia.search.integration.personalization.PersonalizationTest {

  private static final PersonalizationClient personalizationClient =
      DefaultPersonalizationClient.create(
          TestHelpers.ALGOLIA_APPLICATION_ID_1, TestHelpers.ALGOLIA_ADMIN_KEY_1, "eu");

  PersonalizationTest() {
    super(personalizationClient);
  }

  @AfterAll
  static void close() throws IOException {
    personalizationClient.close();
  }
}
