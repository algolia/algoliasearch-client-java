package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
  public void deleteByQuery() throws AlgoliaException, InterruptedException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    List<AlgoliaObject> objects =
        IntStream.rangeClosed(1, 10)
            .mapToObj(i -> new AlgoliaObject("name" + i, i))
            .collect(Collectors.toList());
    waitForCompletion(index.addObjects(objects));

    index.deleteByQuery(new Query(""));

    for (int i = 0; i < 10; i++) {
      List<AlgoliaObject> hits = index.search(new Query("")).getHits();
      if (hits.isEmpty()) {
        assertThat(hits).isEmpty();
        return;
      }
      Thread.sleep(1000L);
    }
    fail("empty index should be empty");
  }

  @SuppressWarnings("deprecation")
  @Test
  public void deleteByQueryBatchSize2() throws AlgoliaException, InterruptedException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    List<AlgoliaObject> objects =
        IntStream.rangeClosed(1, 10)
            .mapToObj(i -> new AlgoliaObject("name" + i, i))
            .collect(Collectors.toList());
    waitForCompletion(index.addObjects(objects));

    index.deleteByQuery(new Query(""), 2);

    for (int i = 0; i < 10; i++) {
      List<AlgoliaObject> hits = index.search(new Query("")).getHits();
      if (hits.isEmpty()) {
        assertThat(hits).isEmpty();
        return;
      }
      Thread.sleep(1000L);
    }
    fail("empty index should be empty");
  }

  @Test
  public void deleteBy() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    List<AlgoliaObject> objects =
        IntStream.rangeClosed(1, 10)
            .mapToObj(i -> new AlgoliaObject("name" + i, i))
            .collect(Collectors.toList());
    waitForCompletion(index.addObjects(objects));

    waitForCompletion(index.deleteBy(new Query().setTagFilters(Collections.singletonList("a"))));

    assertThat(index.search(new Query("")).getHits()).hasSize(10);
  }
}
