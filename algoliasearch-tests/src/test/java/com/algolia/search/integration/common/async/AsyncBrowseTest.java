package com.algolia.search.integration.common.async;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.*;
import com.algolia.search.iterators.AsyncIndexIterable;
import com.algolia.search.objects.Query;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

public abstract class AsyncBrowseTest extends AsyncAlgoliaIntegrationTest {
  @Test
  public void browse() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    List<AlgoliaObject> objects =
        IntStream.rangeClosed(1, 10)
            .mapToObj(i -> new AlgoliaObject("name" + i, i))
            .collect(Collectors.toList());
    waitForCompletion(index.addObjects(objects));

    AsyncIndexIterable<AlgoliaObject> iterator = index.browse(new Query("").setHitsPerPage(1));
    ArrayList<AlgoliaObject> array = Lists.newArrayList(iterator);

    assertThat(array).hasSize(10);

    for (AlgoliaObject object : iterator) {
      assertThat(object.getName()).startsWith("name");
      assertThat(object.getAge()).isBetween(1, 10);
    }
  }

  @Test
  public void browseWithQuery() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    List<AlgoliaObject> objects =
        IntStream.rangeClosed(1, 5)
            .mapToObj(i -> new AlgoliaObject("name" + i, i))
            .collect(Collectors.toList());
    objects.addAll(
        IntStream.rangeClosed(1, 5)
            .mapToObj(i -> new AlgoliaObject("other" + i, i))
            .collect(Collectors.toList()));
    waitForCompletion(index.addObjects(objects));

    AsyncIndexIterable<AlgoliaObject> iterator = index.browse(new Query("name").setHitsPerPage(5));

    assertThat(iterator).hasSize(5);
    for (AlgoliaObject object : iterator) {
      assertThat(object.getName()).startsWith("name");
      assertThat(object.getAge()).isBetween(1, 10);
    }
  }

  @Test
  public void browseNonExistingIndex() {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    AsyncIndexIterable<AlgoliaObject> iterator = index.browse(new Query("").setHitsPerPage(1));

    assertThat(iterator).isEmpty();
  }

  @Test
  public void browseEmptyIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    // Add object then clear => index is empty
    waitForCompletion(index.addObject(new AlgoliaObject("name", 1)));
    waitForCompletion(index.clear());

    AsyncIndexIterable<AlgoliaObject> iterator = index.browse(new Query("").setHitsPerPage(1));
    assertThat(iterator).isEmpty();
  }
}
