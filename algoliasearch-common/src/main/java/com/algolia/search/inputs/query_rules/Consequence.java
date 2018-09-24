package com.algolia.search.inputs.query_rules;

import com.algolia.search.objects.Hide;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Consequence implements Serializable {
  private ConsequenceParams params;
  private List<ConsequencePromote> promote;
  private Map<String, Object> userData;
  private List<Hide> hide;

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

  public void setHide(List<Hide> hide) {
    this.hide = hide;
  }
}
