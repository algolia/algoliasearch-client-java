package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.BatchOperation;
import com.algolia.search.inputs.batch.BatchDeleteIndexOperation;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.inputs.synonym.Synonym;
import com.algolia.search.iterators.RulesIterable;
import com.algolia.search.iterators.SynonymsIterable;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class SyncIteratorTest extends SyncAlgoliaIntegrationTest {

  private static List<String> indicesNames = Arrays.asList("index1", "index2", "index3", "index4");

  @Before
  @After
  public void cleanUp() throws AlgoliaException {
    List<BatchOperation> clean =
        indicesNames.stream().map(BatchDeleteIndexOperation::new).collect(Collectors.toList());
    client.batch(clean).waitForCompletion();
  }

  @Test
  public void synonymIterator() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index1", AlgoliaObject.class);

    List<AbstractSynonym> synonyms =
        IntStream.rangeClosed(1, 10)
            .mapToObj(i -> new Synonym(Arrays.asList("a_" + i, "b_" + i)).setObjectID("id_" + i))
            .collect(Collectors.toList());
    index.batchSynonyms(synonyms).waitForCompletion();

    Iterable<AbstractSynonym> iterable = new SynonymsIterable(index, 1);
    List<AbstractSynonym> array = Lists.newArrayList(iterable);

    assertThat(array).hasSize(10);

    for (AbstractSynonym object : iterable) {
      assertThat(object.getType()).isEqualTo("synonym");
      assertThat(object.getObjectID()).startsWith("id_");
    }
  }

  @Test
  public void synonymIteratorNonExistingIndex() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index2", AlgoliaObject.class);

    Iterable<AbstractSynonym> iterator = new SynonymsIterable(index);
    assertThat(iterator).isEmpty();
  }

  @Test
  public void synonymIteratorEmptySynonyms() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index3", AlgoliaObject.class);

    index.addObject(new AlgoliaObject("name", 1)).waitForCompletion();

    Iterable<AbstractSynonym> iterator = new SynonymsIterable(index);
    assertThat(iterator).isEmpty();
  }

  @Test
  public void ruleIterator() throws AlgoliaException {
    Index<AlgoliaObject> index = client.initIndex("index4", AlgoliaObject.class);

    List<Rule> rules =
        IntStream.rangeClosed(1, 10)
            .mapToObj(i -> generateRule("id_" + i))
            .collect(Collectors.toList());
    index.batchRules(rules).waitForCompletion();

    Iterable<Rule> iterable = new RulesIterable(index, 1);
    List<Rule> array = Lists.newArrayList(iterable);

    assertThat(array).hasSize(10);

    for (Rule object : iterable) {
      assertThat(object.getObjectID()).startsWith("id_");
    }
  }
}
