package com.algolia.search;

import com.algolia.search.models.common.CompressionType;
import com.algolia.search.models.indexing.ActionEnum;
import com.algolia.search.models.indexing.BatchOperation;
import com.algolia.search.models.indexing.IndicesResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class IntegrationTestExtension
    implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

  public static SearchClient searchClient;
  public static SearchClient searchClient2;

  public static String ALGOLIA_APPLICATION_ID_1 = System.getenv("ALGOLIA_APPLICATION_ID_1");
  public static String ALGOLIA_API_KEY_1 = System.getenv("ALGOLIA_ADMIN_KEY_1");
  public static String ALGOLIA_SEARCH_KEY_1 = System.getenv("ALGOLIA_SEARCH_KEY_1");
  private static String ALGOLIA_APPLICATION_ID_2 = System.getenv("ALGOLIA_APPLICATION_ID_2");
  private static String ALGOLIA_API_KEY_2 = System.getenv("ALGOLIA_ADMIN_KEY_2");
  public static String ALGOLIA_APPLICATION_ID_MCM = System.getenv("ALGOLIA_APPLICATION_ID_MCM");
  public static String ALGOLIA_ADMIN_KEY_MCM = System.getenv("ALGOLIA_ADMIN_KEY_MCM");

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    checkEnvironmentVariable();
    searchClient = DefaultSearchClient.create(ALGOLIA_APPLICATION_ID_1, ALGOLIA_API_KEY_1);
    // Disabling gzip for client2 because GZip not is not enabled yet on the server
    SearchConfig client2Config =
        new SearchConfig.Builder(ALGOLIA_APPLICATION_ID_2, ALGOLIA_API_KEY_2)
            .setCompressionType(CompressionType.NONE)
            .build();
    searchClient2 = DefaultSearchClient.create(client2Config);
    cleanPreviousIndices();
  }

  @Override
  public void close() {
    try {
      searchClient2.close();
      searchClient.close();
    } catch (IOException ignored) {
    }
  }

  private void cleanPreviousIndices() {
    List<IndicesResponse> indices = searchClient.listIndices();

    if (indices != null && !indices.isEmpty()) {
      OffsetDateTime today =
          OffsetDateTime.now(ZoneOffset.UTC).withHour(0).withMinute(0).withNano(0).withSecond(0);

      List<IndicesResponse> indicesToDelete =
          indices.stream()
              .filter(
                  i ->
                      i.getName().contains("java_jvm")
                          && i.getCreatedAt() != null
                          && i.getCreatedAt().isBefore(today))
              .collect(Collectors.toList());

      if (!indicesToDelete.isEmpty()) {

        List<BatchOperation<Object>> operations =
            indicesToDelete.stream()
                .map(i -> new BatchOperation<>(i.getName(), ActionEnum.DELETE))
                .collect(Collectors.toList());

        searchClient.multipleBatch(operations);
      }
    }
  }

  private static void checkEnvironmentVariable() throws Exception {
    if (ALGOLIA_APPLICATION_ID_1 == null || ALGOLIA_APPLICATION_ID_1.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID is not defined or empty");
    }

    if (ALGOLIA_API_KEY_1 == null || ALGOLIA_API_KEY_1.isEmpty()) {
      throw new Exception("ALGOLIA_API_KEY is not defined or empty");
    }

    if (ALGOLIA_SEARCH_KEY_1 == null || ALGOLIA_SEARCH_KEY_1.isEmpty()) {
      throw new Exception("ALGOLIA_SEARCH_KEY_1 is not defined or empty");
    }

    if (ALGOLIA_APPLICATION_ID_2 == null || ALGOLIA_APPLICATION_ID_2.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID_2 is not defined or empty");
    }

    if (ALGOLIA_API_KEY_2 == null || ALGOLIA_API_KEY_2.isEmpty()) {
      throw new Exception("ALGOLIA_API_KEY_2 is not defined or empty");
    }

    if (ALGOLIA_APPLICATION_ID_MCM == null || ALGOLIA_APPLICATION_ID_MCM.isEmpty()) {
      throw new Exception("ALGOLIA_APPLICATION_ID_MCM is not defined or empty");
    }

    if (ALGOLIA_ADMIN_KEY_MCM == null || ALGOLIA_ADMIN_KEY_MCM.isEmpty()) {
      throw new Exception("ALGOLIA_ADMIN_KEY_MCM is not defined or empty");
    }
  }
}
