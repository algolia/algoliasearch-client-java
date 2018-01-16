package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Query;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

public abstract class SyncDeleteByTest extends SyncAlgoliaIntegrationTest {

  @SuppressWarnings("deprecation")
  @Test
  public void deleteByQuery() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    List<AlgoliaObject> objects =
        IntStream.rangeClosed(1, 10)
            .mapToObj(i -> new AlgoliaObject("name" + i, i))
            .collect(Collectors.toList());
    index.addObjects(objects).waitForCompletion();

    index.deleteByQuery(new Query(""));

    assertThat(index.search(new Query("")).getHits()).isEmpty();
  }

  @SuppressWarnings("deprecation")
  @Test
  public void deleteByQueryBatchSize2() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    List<AlgoliaObject> objects =
        IntStream.rangeClosed(1, 10)
            .mapToObj(i -> new AlgoliaObject("name" + i, i))
            .collect(Collectors.toList());
    index.addObjects(objects).waitForCompletion();

    index.deleteByQuery(new Query(""), 2);

    assertThat(index.search(new Query("")).getHits()).isEmpty();
  }

  @Test
  public void deleteBy() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    List<AlgoliaObject> objects =
        IntStream.rangeClosed(1, 10)
            .mapToObj(i -> new AlgoliaObject("name" + i, i))
            .collect(Collectors.toList());
    index.addObjects(objects).waitForCompletion();

    index.deleteBy(new Query().setTagFilters(Collections.singletonList("a"))).waitForCompletion();

    assertThat(index.search(new Query("")).getHits()).hasSize(10);
  }
}
