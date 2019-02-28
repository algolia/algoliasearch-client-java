package com.algolia.search.models;

import com.algolia.search.custom_serializers.CustomZonedDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetSettingsResponse extends IndexingResponse implements Serializable {

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  public SetSettingsResponse setUpdatedAt(ZonedDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  @JsonDeserialize(using = CustomZonedDateTimeDeserializer.class)
  private ZonedDateTime updatedAt;
}
