package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.iterators.RulesIterable;
import com.algolia.search.iterators.SynonymsIterable;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.RequestOptions;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nonnull;

public class AccountClient {

  /**
   * Copy an index to another
   *
   * @param sourceIndex the source index
   * @param destinationIndex the destination index
   * @throws AlgoliaException if destination already exists
   */
  public static void copyIndex(@Nonnull Index sourceIndex, @Nonnull Index destinationIndex)
      throws AlgoliaException {
    copyIndex(sourceIndex, destinationIndex, new RequestOptions());
  }

  /**
   * Copy an index to another
   *
   * @param sourceIndex the source index
   * @param destinationIndex the destination index
   * @param requestOptions requestOptions
   * @throws AlgoliaException if destination already exists
   */
  public static void copyIndex(
      @Nonnull Index sourceIndex,
      @Nonnull Index destinationIndex,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {

    // Test if the destination index exists
    IndexSettings destSettings = destinationIndex.getSettings();

    if (destSettings != null) {
      throw new AlgoliaException(
          "Destination index already exists. Please delete it before copying index across applications.");
    }

    // Save settings
    IndexSettings srcSettings = sourceIndex.getSettings();
    destinationIndex.setSettings(srcSettings, requestOptions);

    // Save synonyms
    Iterable<AbstractSynonym> synonymsIterable = new SynonymsIterable(sourceIndex);
    List<AbstractSynonym> synonyms = Lists.newArrayList(synonymsIterable);

    destinationIndex.batchSynonyms(synonyms, requestOptions);

    // Save Rules
    Iterable<Rule> rulesIterable = new RulesIterable(sourceIndex);
    List<Rule> rules = Lists.newArrayList(rulesIterable);

    destinationIndex.batchRules(rules, requestOptions);
  }
}
