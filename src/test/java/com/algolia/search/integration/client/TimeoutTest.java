package com.algolia.search.integration.client;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.algolia.search.SearchClient;
import com.algolia.search.SearchConfig;
import com.algolia.search.exceptions.AlgoliaRetryException;
import java.io.IOException;
import org.junit.jupiter.api.Disabled;

public abstract class TimeoutTest {

  protected abstract SearchConfig.Builder createBuilder();

  protected abstract SearchClient createClient(SearchConfig config);

  @Disabled
  void testUnreachableHostExceptions() {
    try (SearchClient client =
        createClient(createBuilder().setConnectTimeOut(2).setReadTimeOut(2).build())) {
      assertThatThrownBy(() -> client.getLogsAsync().get())
          .hasCauseInstanceOf(AlgoliaRetryException.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
