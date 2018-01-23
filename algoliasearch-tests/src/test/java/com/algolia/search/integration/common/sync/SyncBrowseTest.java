package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.algolia.search.APIClient;
import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.iterators.IndexIterable;
import com.algolia.search.objects.Query;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

public abstract class SyncBrowseTest extends SyncAlgoliaIntegrationTest {

  @Test
  public void browse() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    List<AlgoliaObject> objects =
        IntStream.rangeClosed(1, 10)
            .mapToObj(i -> new AlgoliaObject("name" + i, i))
            .collect(Collectors.toList());
    waitForCompletion(index.addObjects(objects));

    IndexIterable<AlgoliaObject> iterator = index.browse(new Query("").setHitsPerPage(1));
    ArrayList<AlgoliaObject> array = Lists.newArrayList(iterator);

    assertThat(array).hasSize(10);

    for (AlgoliaObject object : iterator) {
      assertThat(object.getName()).startsWith("name");
      assertThat(object.getAge()).isBetween(1, 10);
    }
  }

  @Test
  public void browseWithQuery() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    List<AlgoliaObject> objects =
        IntStream.rangeClosed(1, 5)
            .mapToObj(i -> new AlgoliaObject("name" + i, i))
            .collect(Collectors.toList());
    objects.addAll(
        IntStream.rangeClosed(1, 5)
            .mapToObj(i -> new AlgoliaObject("other" + i, i))
            .collect(Collectors.toList()));
    waitForCompletion(index.addObjects(objects));

    IndexIterable<AlgoliaObject> iterator = index.browse(new Query("name").setHitsPerPage(5));

    assertThat(iterator).hasSize(5);
    for (AlgoliaObject object : iterator) {
      assertThat(object.getName()).startsWith("name");
      assertThat(object.getAge()).isBetween(1, 10);
    }
  }

  @Test
  public void browseNonExistingIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    IndexIterable<AlgoliaObject> iterator = index.browse(new Query("").setHitsPerPage(1));

    assertThat(iterator).isEmpty();
  }

  @Test
  public void browseEmptyIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    // Add object then clear => index is empty
    waitForCompletion(index.addObject(new AlgoliaObject("name", 1)));
    waitForCompletion(index.clear());

    IndexIterable<AlgoliaObject> iterator = index.browse(new Query("").setHitsPerPage(1));
    assertThat(iterator).isEmpty();
  }

  @Test
  public void browseEmptyWithException() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);

    waitForCompletion(index.addObject(new AlgoliaObject("name", 1)));

    ObjectMapper objectMapper =
        new ObjectMapper()
            .enable(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);

    APIClient clientWithSpecificObjectMapper =
        createInstance(APPLICATION_ID, API_KEY, objectMapper);

    Index<BadClass> indexWithWrongClass =
        clientWithSpecificObjectMapper.initIndex("whatever", BadClass.class);

    Iterator<BadClass> iterator =
        indexWithWrongClass.browse(new Query("").setHitsPerPage(1)).iterator();
    assertThatThrownBy(iterator::next).isInstanceOf(RuntimeException.class);
  }

  static class BadClass {
    private String notName;

    public String getNotName() {
      return notName;
    }

    public BadClass setNotName(String notName) {
      this.notName = notName;
      return this;
    }

    @Override
    public String toString() {
      return "BadClass{" + "notName='" + notName + '\'' + '}';
    }
  }
}
