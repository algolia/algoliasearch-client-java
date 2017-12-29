package com.algolia.search;

import java.io.Serializable;

public interface AbstractIndex<T> extends Serializable {

  String getName();

  Class<T> getKlass();
}
