package com.algolia.search.models.indexing;

/**
 * The “scope” parameter is an array of strings that refer to the following items:
 *
 * @see <a href="https://www.algolia.com/doc/api-reference/api-methods/copy-index>Algolia.com</a>
 */
public class CopyScope {
  public static final String SETTINGS = "settings";
  public static final String SYNONYMS = "synonyms";
  public static final String RULES = "rules";
}
