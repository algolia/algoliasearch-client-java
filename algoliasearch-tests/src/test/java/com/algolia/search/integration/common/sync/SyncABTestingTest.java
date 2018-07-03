package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.Analytics;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.analytics.ABTest;
import com.algolia.search.inputs.analytics.Variant;
import com.algolia.search.integration.common.ABTestingHelpersTest;
import com.algolia.search.objects.tasks.sync.TaskABTest;
import com.algolia.search.responses.ABTests;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public abstract class SyncABTestingTest extends SyncAlgoliaIntegrationTest {

  @Test
  public void createModifyAndDeleteABTests() throws AlgoliaException {
    Analytics analytics = createAnalytics();
    int offset = 0;
    int limit = 10;

    ABTests abTests = analytics.getABTests(offset, limit);
    List<Long> idsToRemove = new ArrayList<>();

    while (abTests.getCount() != 0) {
      for (ABTest abtest : abTests.getAbtests()) {
        idsToRemove.add(abtest.getAbTestID());
      }
      offset += limit;
      abTests = analytics.getABTests(offset, limit);
    }

    List<TaskABTest> tasks = new ArrayList<>();
    for (long id : idsToRemove) {
      tasks.add(analytics.deleteABTest(id));
    }
    for (TaskABTest task : tasks) {
      waitForCompletion(task);
    }

    Index<AlgoliaObject> i1 = createIndex(AlgoliaObject.class);
    Index<AlgoliaObject> i2 = createIndex(AlgoliaObject.class);

    waitForCompletion(i1.addObject(new AlgoliaObject("algolia", 1)));
    waitForCompletion(i2.addObject(new AlgoliaObject("algolia", 1)));

    LocalDateTime now = LocalDateTime.now();

    ABTest abtest =
        new ABTest(
            "abtest_name",
            Arrays.asList(
                new Variant(i1.getName(), 60, "a description"),
                new Variant(i2.getName(), 40, null)),
            now.plus(1, ChronoUnit.DAYS));

    waitForCompletion(analytics.addABTest(abtest));

    abTests = analytics.getABTests(0, 10);
    assertThat(abTests.getCount()).isEqualTo(1);
    assertThat(abTests.getTotal()).isEqualTo(1);
    assertThat(abTests.getAbtests()).hasSize(1);

    ABTest inserted = abTests.getAbtests().get(0);
    ABTestingHelpersTest.compareABTests(abtest, inserted);

    waitForCompletion(analytics.stopABTest(inserted.getAbTestID()));
    inserted = analytics.getABTest(inserted.getAbTestID());
    assertThat(inserted.getStatus()).isEqualTo("stopped");

    waitForCompletion(analytics.deleteABTest(inserted.getAbTestID()));
    abTests = analytics.getABTests(0, 10);
    assertThat(abTests.getCount()).isEqualTo(0);
  }
}
