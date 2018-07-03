package com.algolia.search.integration.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.inputs.analytics.ABTest;
import com.algolia.search.inputs.analytics.Variant;

public class ABTestingHelpersTest {
  public static void compareABTests(ABTest abtest, ABTest inserted) {
    assertThat(inserted.getEndAt()).isEqualTo(abtest.getEndAt());
    assertThat(inserted.getName()).isEqualTo(abtest.getName());
    assertThat(inserted.getStatus()).isEqualTo("active");
    assertThat(inserted.getVariants()).hasSize(2);

    for (int i = 0; i < 2; i++) {
      Variant v1 = inserted.getVariants().get(i);
      Variant v2 = abtest.getVariants().get(i);
      assertThat(v1.getIndex()).isEqualTo(v2.getIndex());
      assertThat(v1.getTrafficPercentage()).isEqualTo(v2.getTrafficPercentage());
      assertThat(v1.getDescription()).isEqualTo(v2.getDescription());
    }
  }
}
