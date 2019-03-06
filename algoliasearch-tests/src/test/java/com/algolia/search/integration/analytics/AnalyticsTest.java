package com.algolia.search.integration.analytics;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.algolia.search.clients.SearchIndex;
import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.inputs.analytics.ABTest;
import com.algolia.search.inputs.analytics.ABTestResponse;
import com.algolia.search.inputs.analytics.Variant;
import com.algolia.search.integration.AlgoliaBaseIntegrationTest;
import com.algolia.search.integration.AlgoliaObject;
import com.algolia.search.models.AddABTestResponse;
import com.algolia.search.models.BatchIndexingResponse;
import com.algolia.search.models.DeleteAbTestResponse;
import com.algolia.search.models.StopAbTestResponse;
import com.algolia.search.responses.ABTests;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class AnalyticsTest extends AlgoliaBaseIntegrationTest {

  private SearchIndex<AlgoliaObject> index1;
  private SearchIndex<AlgoliaObject> index2;
  private String index1Name;
  private String index2Name;

  void init() {
    index1Name = getTestIndexName("ab_testing");
    index2Name = getTestIndexName("ab_testing_dev");
    index1 = searchClient.initIndex(index1Name, AlgoliaObject.class);
    index2 = searchClient.initIndex(index2Name, AlgoliaObject.class);
  }

  @Test
  void abTestingTest() {
    init();
    String now =
        ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String testName = String.format("java-%s-%s", now, userName);

    ABTests abTests = analyticsClient.getABTestsAsync().join();

    if (abTests.getAbtests() != null) {
      List<ABTestResponse> abTestsToDelte =
          abTests
              .getAbtests()
              .stream()
              .filter(
                  x ->
                      x.getName().contains("java-")
                          && !x.getName().contains(String.format("java-%s", now)))
              .collect(Collectors.toList());

      for (ABTestResponse abtest : abTestsToDelte) {
        analyticsClient.deleteABTestAsync(abtest.getAbTestID());
      }
    }

    CompletableFuture<BatchIndexingResponse> saveObjectFuture1 =
        index1.saveObjectAsync(new AlgoliaObject("one", "value"));
    CompletableFuture<BatchIndexingResponse> saveObjectFuture2 =
        index2.saveObjectAsync(new AlgoliaObject("one", "value"));

    OffsetDateTime utcNow = OffsetDateTime.now(ZoneOffset.UTC).withNano(0).withSecond(0);

    ABTest abtest =
        new ABTest(
            testName,
            Arrays.asList(
                new Variant(index1Name, 60, "a description"), new Variant(index2Name, 40, null)),
            utcNow.plusDays(1));

    saveObjectFuture1.join().waitTask();
    saveObjectFuture2.join().waitTask();

    AddABTestResponse addAbTest = analyticsClient.addABTestAsync(abtest).join();
    long abTestID = addAbTest.getAbTestID();
    index1.waitTask(addAbTest.getTaskID());

    ABTest abTestToCheck = analyticsClient.getABTestAsync(abTestID).join();

    // Assert that the objects are deeply equal
    assertThat(abTestToCheck.getVariants())
        .usingRecursiveComparison()
        .isEqualTo(abtest.getVariants());
    assertThat(abTestToCheck.getEndAt()).isEqualTo(abtest.getEndAt());
    assertThat(abTestToCheck.getName()).isEqualTo(abtest.getName());

    ABTests listABTests = analyticsClient.getABTestsAsync().join();
    assertThat(listABTests.getAbtests()).isNotNull();

    Optional<ABTestResponse> result =
        listABTests.getAbtests().stream().filter(x -> x.getAbTestID() == abTestID).findFirst();

    // Assert that the objects are deeply equal;
    assertThat(result.get().getVariants())
        .usingRecursiveComparison()
        .isEqualTo(abtest.getVariants());
    assertThat(result.get().getEndAt()).isEqualTo(abtest.getEndAt());
    assertThat(result.get().getName()).isEqualTo(abtest.getName());

    StopAbTestResponse stopAbTestResponse = analyticsClient.stopABTestAsync(abTestID).join();

    // Assert that the ABTest was stopped
    ABTestResponse stoppedAbTest = analyticsClient.getABTestAsync(abTestID).join();
    assertThat(Objects.equals(stoppedAbTest.getStatus(), "stopped"));

    DeleteAbTestResponse deleteAbTest = analyticsClient.deleteABTestAsync(abTestID).join();
    index1.waitTask(deleteAbTest.getTaskID());

    assertThatThrownBy(() -> analyticsClient.getABTestAsync(abTestID).join())
        .hasCauseInstanceOf(AlgoliaApiException.class)
        .hasMessageContaining("ABTestID not found");
  }
}
