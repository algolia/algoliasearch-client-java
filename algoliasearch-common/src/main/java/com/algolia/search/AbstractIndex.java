package com.algolia.search;

public interface AbstractIndex<T> {

  String getName();

  Class<T> getKlass();
}
