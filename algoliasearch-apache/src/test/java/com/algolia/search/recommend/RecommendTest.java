package com.algolia.search.recommend;

import com.algolia.search.DefaultRecommendClient;
import com.algolia.search.IntegrationTestExtension;
import com.algolia.search.RecommendClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static com.algolia.search.integration.TestHelpers.ALGOLIA_ADMIN_KEY_1;
import static com.algolia.search.integration.TestHelpers.ALGOLIA_APPLICATION_ID_1;

@Disabled
@ExtendWith({IntegrationTestExtension.class})
class RecommendTest extends com.algolia.search.integration.recommend.RecommendTest {

  private static final RecommendClient recommendClient =
      DefaultRecommendClient.create(ALGOLIA_APPLICATION_ID_1, ALGOLIA_ADMIN_KEY_1);

  RecommendTest() {
    super(recommendClient);
  }

  @AfterAll
  static void close() throws IOException {
    recommendClient.close();
  }
}
