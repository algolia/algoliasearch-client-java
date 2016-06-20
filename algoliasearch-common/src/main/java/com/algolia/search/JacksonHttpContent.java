package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.json.Json;

import java.io.IOException;
import java.io.OutputStream;

class JacksonHttpContent extends AbstractHttpContent {

  private final ObjectMapper objectMapper;
  private final Object data;

  JacksonHttpContent(ObjectMapper objectMapper, Object data) {
    super(Json.MEDIA_TYPE);
    this.objectMapper = objectMapper;
    this.data = data;
  }

  @Override
  public void writeTo(OutputStream out) throws IOException {
    objectMapper.writeValue(out, data);
  }
}
