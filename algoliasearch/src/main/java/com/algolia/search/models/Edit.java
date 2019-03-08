package com.algolia.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Edit {

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
    return new Edit(EditType.Remove, delete, null);
  }

  public static Edit createReplace(String delete, String insert) {
    return new Edit(EditType.Replace, delete, insert);
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
