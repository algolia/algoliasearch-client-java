package com.algolia.search.objects.tasks;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.Defaults;
import com.algolia.search.objects.tasks.sync.Task;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.Test;

public class TaskTest {

  private ObjectMapper objectMapper =
      Defaults.DEFAULT_OBJECT_MAPPER.setVisibility(
          PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

  @Test
  public void serialization() throws IOException {
    String serialized =
        "{\"updatedAt\":\"2017-12-07T09:59:49.392Z\",\"taskID\":6483140,\"objectID\":\"99\"}";

    Task task =
        objectMapper.readValue(serialized, objectMapper.getTypeFactory().constructType(Task.class));

    assertThat(task.getTaskID()).isEqualTo(6483140);
  }
}
