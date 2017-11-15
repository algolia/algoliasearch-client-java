package com.algolia.search.objects;

import java.util.Arrays;
import java.util.List;

public enum CopyScope {
  settings,
  synonyms,
  rules;

  public static List<CopyScope> all = Arrays.asList(values());
}
