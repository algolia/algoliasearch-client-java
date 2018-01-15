package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.base.Joiner;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SynonymQuery implements Serializable {

  private String query;
  private String type;
  private Long page;
  private Long hitsPerPage;

  public SynonymQuery(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

  public SynonymQuery setQuery(String query) {
    this.query = query;
    return this;
  }

  /**
   * use {@link #getType()}
   *
   * @return
   */
  @Deprecated
  @JsonIgnore
  public List<String> getTypes() {
    return Collections.singletonList(type);
  }

  /**
   * use {@link #setType(String)}
   *
   * @return
   */
  @Deprecated
  @JsonIgnore
  public SynonymQuery setTypes(List<String> types) {
    this.type = Joiner.on(",").join(types);
    return this;
  }

  public String getType() {
    return type;
  }

  public SynonymQuery setType(String type) {
    this.type = type;
    return this;
  }

  public Long getPage() {
    return page;
  }

  @JsonSetter
  public SynonymQuery setPage(Long page) {
    this.page = page;
    return this;
  }

  public SynonymQuery setPage(Integer page) {
    return this.setPage(page.longValue());
  }

  public Long getHitsPerPage() {
    return hitsPerPage;
  }

  @JsonSetter
  public SynonymQuery setHitsPerPage(Long hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  public SynonymQuery setHitsPerPage(Integer hitsPerPage) {
    return this.setHitsPerPage(hitsPerPage.longValue());
  }

  @Override
  public String toString() {
    return "SynonymQuery{"
        + "query='"
        + query
        + '\''
        + ", type="
        + type
        + ", page="
        + page
        + ", hitsPerPage="
        + hitsPerPage
        + '}';
  }
}
