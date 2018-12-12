package com.algolia.search.inputs.insights;

import com.algolia.search.Defaults;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InsightsResult implements Serializable {

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public InsightsResult setMessage(String message) {
    this.message = message;
    return this;
  }

  private int status;
  private String message;

  class InsightsResultDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      ObjectMapper objectMapper = Defaults.DEFAULT_OBJECT_MAPPER;
      ObjectReader reader = objectMapper.readerFor(new TypeReference<InsightsResult>() {});
      return reader.readValue(node);
    }
  }
}
