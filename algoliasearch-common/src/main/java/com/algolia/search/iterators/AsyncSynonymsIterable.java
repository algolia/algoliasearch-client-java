package com.algolia.search.iterators;

import com.algolia.search.AsyncIndex;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import java.util.Iterator;
import javax.annotation.Nonnull;

public class AsyncSynonymsIterable implements Iterable<AbstractSynonym> {

  private final AsyncIndex<?> index;
  private final Integer hitsPerPage;

  public AsyncSynonymsIterable(@Nonnull AsyncIndex<?> index) {
    this(index, 1000);
  }

  public AsyncSynonymsIterable(@Nonnull AsyncIndex<?> index, @Nonnull Integer hitsPerPage) {
    this.index = index;
    this.hitsPerPage = hitsPerPage;
  }

  @Override
  public Iterator<AbstractSynonym> iterator() {
    return new AsyncSynonymsIterator(index, hitsPerPage);
  }
}
