package com.algolia.search.integration.index;

import static com.algolia.search.integration.TestHelpers.getTestIndexName;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.integration.models.AlgoliaObject;
import org.junit.jupiter.api.Test;

public abstract class ExistTest {

  private final SearchIndex<AlgoliaObject> index;

  protected ExistTest(SearchClient searchClient) {
    this.index = searchClient.initIndex(getTestIndexName("exists"), AlgoliaObject.class);
  }

  @Test
  void testExist() {
    assertThat(index.exists()).isFalse();

    index.saveObject(new AlgoliaObject("one", "exist")).waitTask();

    assertThat(index.exists()).isTrue();
  }
}
