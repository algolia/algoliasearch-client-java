package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Log implements Serializable {

  private String timestamp;
  private String method;

  @JsonProperty("answer_code")
  private String answerCode;

  @JsonProperty("query_body")
  private String queryBody;

  private String answer;

  private String url;

  private String ip;

  @JsonProperty("query_headers")
  private String queryHeaders;

  private String sha1;

  public String getTimestamp() {
    return timestamp;
  }

  public String getMethod() {
    return method;
  }

  public String getAnswerCode() {
    return answerCode;
  }

  public String getQueryBody() {
    return queryBody;
  }

  public String getAnswer() {
    return answer;
  }

  public String getUrl() {
    return url;
  }

  public String getIp() {
    return ip;
  }

  public String getQueryHeaders() {
    return queryHeaders;
  }

  public String getSha1() {
    return sha1;
  }

  public Log setTimestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public Log setMethod(String method) {
    this.method = method;
    return this;
  }

  public Log setAnswerCode(String answerCode) {
    this.answerCode = answerCode;
    return this;
  }

  public Log setQueryBody(String queryBody) {
    this.queryBody = queryBody;
    return this;
  }

  public Log setAnswer(String answer) {
    this.answer = answer;
    return this;
  }

  public Log setUrl(String url) {
    this.url = url;
    return this;
  }

  public Log setIp(String ip) {
    this.ip = ip;
    return this;
  }

  public Log setQueryHeaders(String queryHeaders) {
    this.queryHeaders = queryHeaders;
    return this;
  }

  public Log setSha1(String sha1) {
    this.sha1 = sha1;
    return this;
  }

  @Override
  public String toString() {
    return "Log{"
        + "timestamp='"
        + timestamp
        + '\''
        + ", method='"
        + method
        + '\''
        + ", answerCode='"
        + answerCode
        + '\''
        + ", queryBody='"
        + queryBody
        + '\''
        + ", answer='"
        + answer
        + '\''
        + ", url='"
        + url
        + '\''
        + ", ip='"
        + ip
        + '\''
        + ", queryHeaders='"
        + queryHeaders
        + '\''
        + ", sha1='"
        + sha1
        + '\''
        + '}';
  }
}
