package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Query;
import com.algolia.search.responses.SearchResult;
import java.util.List;
import org.junit.Test;

public abstract class SyncIndicesTest extends SyncAlgoliaIntegrationTest {

  @Test
  public void getAllIndices() throws AlgoliaException {
    assertThat(client.listIndexes()).isNotNull();

    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    index.addObject(new AlgoliaObject("algolia", 4)).waitForCompletion();

    List<Index.Attributes> listIndices = client.listIndexes();
    assertThat(listIndices).extracting("name").contains(index.getName());
    assertThat(listIndices).extracting("numberOfPendingTasks").isNotNull();
  }

  @Test
  public void deleteIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    index.addObject(new AlgoliaObject("algolia", 4)).waitForCompletion();

    assertThat(client.listIndexes()).extracting("name").contains(index.getName());

    index.delete().waitForCompletion();
    assertThat(client.listIndexes()).extracting("name").doesNotContain(index.getName());
  }

  @Test
  public void moveIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    index.addObject(new AlgoliaObject("algolia", 4)).waitForCompletion();

    assertThat(client.listIndexes()).extracting("name").contains(index.getName());

    Index<AlgoliaObject> indexMoveTo = createIndex(AlgoliaObject.class);
    index.moveTo(indexMoveTo.getName()).waitForCompletion();
    assertThat(client.listIndexes())
        .extracting("name")
        .doesNotContain(index.getName())
        .contains(indexMoveTo.getName());
  }

  @Test
  public void copyIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    index.addObject(new AlgoliaObject("algolia", 4)).waitForCompletion();

    assertThat(client.listIndexes()).extracting("name").contains(index.getName());

    Index<AlgoliaObject> indexCopyTo = createIndex(AlgoliaObject.class);
    index.copyTo(indexCopyTo.getName()).waitForCompletion();
    assertThat(client.listIndexes())
        .extracting("name")
        .contains(index.getName(), indexCopyTo.getName());
  }

  @Test
  public void clearIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    index.addObject(new AlgoliaObject("algolia", 4)).waitForCompletion();

    index.clear().waitForCompletion();

    SearchResult<AlgoliaObject> results = index.search(new Query(""));

    assertThat(results.getHits()).isEmpty();
  }
}
