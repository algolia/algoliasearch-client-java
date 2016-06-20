package com.algolia.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.api.client.util.ObjectParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

class JacksonParser implements ObjectParser {
  private final ObjectMapper objectMapper;

  JacksonParser(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public <T> T parseAndClose(InputStream in, Charset charset, Class<T> dataClass) throws IOException {
    return objectMapper.readValue(in, dataClass);
  }

  @Override
  public Object parseAndClose(InputStream in, Charset charset, Type valueType) throws IOException {
    return objectMapper.readValue(in, TypeFactory.defaultInstance().constructType(valueType));
  }

  @Override
  public <T> T parseAndClose(Reader reader, Class<T> dataClass) throws IOException {
    return objectMapper.readValue(reader, dataClass);
  }

  @Override
  public Object parseAndClose(Reader reader, Type valueType) throws IOException {
    return objectMapper.readValue(reader, TypeFactory.defaultInstance().constructType(valueType));
  }
}
