package com.algolia.search.iterators;

import com.algolia.search.Index;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import java.util.Iterator;
import javax.annotation.Nonnull;

public class SynonymsIterable implements Iterable<AbstractSynonym> {

  private final Index<?> index;
  private final Integer hitsPerPage;

  public SynonymsIterable(@Nonnull Index<?> index) {
    this(index, 1000);
  }

  public SynonymsIterable(@Nonnull Index<?> index, @Nonnull Integer hitsPerPage) {
    this.index = index;
    this.hitsPerPage = hitsPerPage;
  }

  @Override
  public Iterator<AbstractSynonym> iterator() {
    return new SynonymsIterator(index, hitsPerPage);
  }
}
