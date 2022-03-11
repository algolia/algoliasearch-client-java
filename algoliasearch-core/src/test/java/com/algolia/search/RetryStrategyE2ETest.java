package com.algolia.search;

import com.algolia.search.integration.models.AlgoliaObject;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static com.algolia.search.integration.TestHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class RetryStrategyE2ETest {

  protected final SearchClient searchClient;
  protected final SearchConfig config;
  protected HttpRequester httpRequester;
  protected List<StatefulHost> customHosts;

  protected RetryStrategyE2ETest(SearchClient searchClient) {
    this.searchClient = searchClient;
    this.customHosts =
        Arrays.asList(
            new StatefulHost("expired.badssl.com", EnumSet.of(CallType.READ)),
            new StatefulHost("http.badssl.com", EnumSet.of(CallType.READ)),
            new StatefulHost(
                String.format("%s-dsn.algolia.net", ALGOLIA_APPLICATION_ID_1),
                EnumSet.of(CallType.READ)));
    this.config =
        new SearchConfig.Builder(ALGOLIA_APPLICATION_ID_1, ALGOLIA_SEARCH_KEY_1)
            .setHosts(customHosts)
            .build();
  }

  @Test
  void testSearch() {
    // Create a index with a valid client.
    String indexName = getTestIndexName("test_retry_e2e");
    SearchIndex<AlgoliaObject> index = searchClient.initIndex(indexName, AlgoliaObject.class);
    index.saveObject(new AlgoliaObject("one", "exist")).waitTask();

    // Bad host, will fail with:
    // * java-net: SecurityException
    // * apache: SSLException
    SearchClient client = new SearchClient(config, httpRequester);
    SearchIndex<AlgoliaObject> idx = client.initIndex(indexName, AlgoliaObject.class);

    // 1st host should fail; retry with the 2nd working host.
    SearchResult<?> result = idx.search(new Query(""));
    assertThat(result.getNbHits()).isEqualTo(1);
  }
}
