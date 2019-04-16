package com.algolia.search.models.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

/**
 * Edit parameter. More information:
 *
 * @see <a href="https://www.algolia.com/doc/api-client/methods/query-rules">Algolia.com</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Edit implements Serializable {

  private String type;
  private String delete;
  private String insert;

  public Edit() {}

  public Edit(String type, String delete, String insert) {
    this.type = type;
    this.delete = delete;
    this.insert = insert;
  }

  public static Edit createDelete(String delete) {
    return new Edit(EditType.REMOVE, delete, null);
  }

  public static Edit createReplace(String delete, String insert) {
    return new Edit(EditType.REPLACE, delete, insert);
  }

  public String getDelete() {
    return delete;
  }

  public Edit setDelete(String delete) {
    this.delete = delete;
    return this;
  }

  public String getInsert() {
    return insert;
  }

  public Edit setInsert(String insert) {
    this.insert = insert;
    return this;
  }

  public String getType() {
    return type;
  }

  public Edit setType(String type) {
    this.type = type;
    return this;
  }
}
