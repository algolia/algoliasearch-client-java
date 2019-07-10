package com.algolia.search;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Query;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

public class UtilsTest {

  @Test
  public void secureKeys() throws AlgoliaException {
    assertThat(
            Utils.generateSecuredApiKey(
                "182634d8894831d5dbce3b3185c50881",
                new Query()
                    .setTagFilters(Collections.singletonList((Arrays.asList("public", "user1")))),
                null))
        .isEqualTo(
            "ZDAxNDM4ZmZmN2IyY2YyZTE1ZjYyNmMyZWQ5ZDYzNTEyZmIxM2RjM2UwMTQ5NGEwZTM4N2I3YTk2MmZkZjljYXRhZ0ZpbHRlcnM9JTVCJTVCJTIycHVibGljJTIyJTJDJTIydXNlcjElMjIlNUQlNUQ=");

    assertThat(
            Utils.generateSecuredApiKey(
                "182634d8894831d5dbce3b3185c50881",
                new Query()
                    .setTagFilters(Collections.singletonList((Arrays.asList("public", "user1"))))
                    .setUserToken("42"),
                null))
        .isEqualTo(
            "MzFiN2Q3MmM5MmNiMjA0ZTY4ZTc3Y2FjZGJmMzhiMDBmYjExMzFiZWYyNGI3ZjYzOWRhZTBhZGY4MmRkY2RlNHRhZ0ZpbHRlcnM9JTVCJTVCJTIycHVibGljJTIyJTJDJTIydXNlcjElMjIlNUQlNUQmdXNlclRva2VuPTQy");
  }

  @Test
  public void completeExceptionally() {
    assertThat(Utils.completeExceptionally(new NullPointerException())).isCompletedExceptionally();
  }
}
