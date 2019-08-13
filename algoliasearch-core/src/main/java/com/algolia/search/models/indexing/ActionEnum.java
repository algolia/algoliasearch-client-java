package com.algolia.search.models.indexing;

/**
 * Actions that need to be performed.
 *
 * @see <a href="https://www.algolia.com/doc/api-reference/api-methods/batc">Algolia.com</a>
 */
public class ActionEnum {

  /** Add an object. Equivalent to Add an object without ID. */
  public static final String ADD_OBJECT = "addObject";

  /**
   * Add or replace an existing object. You must set the <code>objectID</code> attribute to indicate
   * the object to update. Equivalent to Add/update an object by ID.
   */
  public static final String UPDATE_OBJECT = "updateObject";

  /**
   * Partially update an object. You must set the <code>objectID</code> attribute to indicate the
   * object to update. Equivalent to Partially update an object.
   */
  public static final String PARTIAL_UPDATE_OBJECT = "partialUpdateObject";

  /**
   * Same as partialUpdateObject, except that the object is not created if the object designated by
   * <code>objectID</code> does not exist.
   */
  public static final String PARTIAL_UPDATE_OBJECT_NO_CREATE = "partialUpdateObjectNoCreate";

  /**
   * Delete an object. You must set the <code>objectID</code> attribute to indicate the object to
   * delete. Equivalent to Delete an object.
   */
  public static final String DELETE_OBJECT = "deleteObject";

  /** Delete the index. Equivalent to Delete an index. */
  public static final String DELETE = "delete";

  /**
   * Remove the index’s content, but keep settings and index-specific API keys untouched. Equivalent
   * to Clear objects.
   */
  public static final String CLEAR = "clear";
}
