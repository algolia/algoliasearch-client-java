package com.algolia.search.objects;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.Defaults;
import com.algolia.search.inputs.OperationOnIndex;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import org.junit.Test;

public class OperationOnIndexTest {

  private ObjectMapper objectMapper = Defaults.DEFAULT_OBJECT_MAPPER;

  @Test
  public void operationOnIndexSerializationWithoutCopyScope() throws IOException {
    OperationOnIndex operation = new OperationOnIndex("op", "dest");
    String json = objectMapper.writeValueAsString(operation);
    assertThat(json).isEqualTo("{\"operation\":\"op\",\"destination\":\"dest\"}");
  }

  @Test
  public void operationOnIndexSerializationWithCopyScope() throws IOException {
    OperationOnIndex operation =
        new OperationOnIndex("op", "dest", Collections.singletonList("rules"));
    String json = objectMapper.writeValueAsString(operation);
    assertThat(json)
        .isEqualTo("{\"operation\":\"op\",\"destination\":\"dest\",\"scope\":[\"rules\"]}");
  }
}
