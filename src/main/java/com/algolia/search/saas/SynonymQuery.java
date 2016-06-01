package com.algolia.search.saas;

/*
 * Copyright (c) 2016 Algolia
 * http://www.algolia.com/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import java.util.List;

public class SynonymQuery {

  public enum SynonymType {

    SYNONYM("synonym"),
    SYNONYM_ONEWAY("oneWaySynonym"),
    PLACEHOLDER("placeholder"),
    ALTCORRECTION_1("altCorrection1"),
    ALTCORRECTION_2("altCorrection2");

    public final String name;

    SynonymType(String name) {
      this.name = name;
    }

  }

  private String query;
  private List<SynonymType> types = null;
  private Integer page = null;
  private Integer hitsPerPage = null;

  public SynonymQuery(String query) {
    this.query = query;
  }

  public SynonymQuery() {
    this(null);
  }

  /**
   * Set the full text query
   */
  public SynonymQuery setQueryString(String query) {
    this.query = query;
    return this;
  }

  /**
   * Set the types of Synonym to search
   */
  public SynonymQuery setTypes(List<SynonymType> types) {
    this.types = types;
    return this;
  }

  /**
   * Set the page to retrieve (zero base). Defaults to 0.
   */
  public SynonymQuery setPage(Integer page) {
    this.page = page;
    return this;
  }

  /**
   * Set the number of hits per page. Defaults to 10.
   */
  public SynonymQuery setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }

  public String getQueryString() {
    return query;
  }

  public List<SynonymType> getTypes() {
    return types;
  }

  public Integer getPage() {
    return page;
  }

  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public boolean hasTypes() {
    return getTypes() != null && !getTypes().isEmpty();
  }

}
