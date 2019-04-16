package com.algolia.search.models.indexing;

/**
 * Actions that need to be performed.
 *
 * @see <a href="https://www.algolia.com/doc/api-reference/api-methods/batc">Algolia.com</a>
 */
public class ActionEnum {
  public static final String ADD_OBJECT = "addObject";
  public static final String UPDATE_OBJECT = "updateObject";
  public static final String PARTIAL_UPDATE_OBJECT = "partialUpdateObject";
  public static final String PARTIAL_UPDATE_OBJECT_NO_CREATE = "partialUpdateObjectNoCreate";
  public static final String DELETE_OBJECT = "deleteObject";
  public static final String DELETE = "delete";
  public static final String CLEAR = "clear";
}
