package com.algolia.search.integration.common.async;

import static com.algolia.search.integration.common.async.AsyncRulesTest.generateRule;
import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.AlgoliaObjectWithID;
import com.algolia.search.AsyncAlgoliaIntegrationTest;
import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.inputs.synonym.Synonym;
import com.algolia.search.objects.*;
import com.algolia.search.responses.SearchResult;
import com.algolia.search.responses.SearchRuleResult;
import com.algolia.search.responses.SearchSynonymResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public abstract class AsyncIndicesTest extends AsyncAlgoliaIntegrationTest {

  @Test
  public void getAllIndices() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    futureAssertThat(client.listIndices()).extracting("name").contains(index.getName());
  }

  @Test
  public void deleteIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    futureAssertThat(client.listIndices()).extracting("name").contains(index.getName());

    waitForCompletion(index.delete());
    futureAssertThat(client.listIndices()).extracting("name").doesNotContain(index.getName());
  }

  @Test
  public void moveIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    futureAssertThat(client.listIndices()).extracting("name").contains(index.getName());

    AsyncIndex<AlgoliaObject> indexMoveTo = createIndex(AlgoliaObject.class);
    waitForCompletion(index.moveTo(indexMoveTo.getName()));
    futureAssertThat(client.listIndices())
        .extracting("name")
        .doesNotContain(index.getName())
        .contains(indexMoveTo.getName());
  }

  @Test
  public void copyIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    futureAssertThat(client.listIndices()).extracting("name").contains(index.getName());

    AsyncIndex<AlgoliaObject> indexCopyTo = createIndex(AlgoliaObject.class);
    waitForCompletion(index.copyTo(indexCopyTo.getName()));
    futureAssertThat(client.listIndices())
        .extracting("name")
        .contains(index.getName(), indexCopyTo.getName());
  }

  @Test
  public void clearIndex() throws Exception {
    AsyncIndex<AlgoliaObject> index = createIndex(AlgoliaObject.class);
    waitForCompletion(index.addObject(new AlgoliaObject("algolia", 4)));

    waitForCompletion(index.clear());

    SearchResult<AlgoliaObject> results = index.search(new Query("")).get();

    assertThat(results.getHits()).isEmpty();
  }
}
