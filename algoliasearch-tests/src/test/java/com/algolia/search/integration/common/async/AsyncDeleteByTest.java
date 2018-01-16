package com.algolia.search.integration.common.async;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.SearchResult;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

public abstract class AsyncDeleteByTest extends AsyncAlgoliaIntegrationTest {

  @Test
  public void deleteBy() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    List<AlgoliaObject> objects =
        IntStream.rangeClosed(1, 10)
            .mapToObj(i -> new AlgoliaObject("name" + i, i))
            .collect(Collectors.toList());
    waitForCompletion(index.addObjects(objects));

    waitForCompletion(index.deleteBy(new Query().setTagFilters(Collections.singletonList("a"))));

    SearchResult<AlgoliaObject> search = index.search(new Query("")).get();

    assertThat(search.getHits()).hasSize(10);
  }
}
