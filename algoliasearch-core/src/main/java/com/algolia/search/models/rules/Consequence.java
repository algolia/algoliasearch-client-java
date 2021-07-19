package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Consequence parameter. More information:
 *
 * @see <a href="https://www.algolia.com/doc/api-client/methods/query-rules">Algolia.com</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Consequence implements Serializable {

  public Consequence() {}

  public ConsequenceParams getParams() {
    return params;
  }

  public Consequence setParams(ConsequenceParams params) {
    this.params = params;
    return this;
  }

  public List<ConsequencePromote> getPromote() {
    return promote;
  }

  public Consequence setPromote(List<ConsequencePromote> promote) {
    this.promote = promote;
    return this;
  }

  public Map<String, Object> getUserData() {
    return userData;
  }

  public Consequence setUserData(Map<String, Object> userData) {
    this.userData = userData;
    return this;
  }

  public List<Hide> getHide() {
    return hide;
  }

  public Consequence setHide(List<Hide> hide) {
    this.hide = hide;
    return this;
  }

  public Boolean getFilterPromotes() {
    return filterPromotes;
  }

  public Consequence setFilterPromotes(Boolean filterPromotes) {
    this.filterPromotes = filterPromotes;
    return this;
  }

  private ConsequenceParams params;
  private List<ConsequencePromote> promote;
  private Map<String, Object> userData;
  private List<Hide> hide;
  private Boolean filterPromotes;
}
