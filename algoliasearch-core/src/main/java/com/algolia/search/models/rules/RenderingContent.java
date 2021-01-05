package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RenderingContent implements Serializable {

  private Redirect redirect;
  private FacetMerchandising facetMerchandising;
  private Map<String, Object> userData;

  // For serialization
  public RenderingContent() {}

  public RenderingContent(Redirect redirect, FacetMerchandising facetMerchandising) {
    this.redirect = redirect;
    this.facetMerchandising = facetMerchandising;
  }

  public Redirect getRedirect() {
    return redirect;
  }

  public RenderingContent setRedirect(Redirect redirect) {
    this.redirect = redirect;
    return this;
  }

  public FacetMerchandising getFacetMerchandising() {
    return facetMerchandising;
  }

  public RenderingContent setFacetMerchandising(FacetMerchandising facetMerchandising) {
    this.facetMerchandising = facetMerchandising;
    return this;
  }

  public Map<String, Object> getUserData() {
    return userData;
  }

  public RenderingContent setUserData(Map<String, Object> userData) {
    this.userData = userData;
    return this;
  }
}
