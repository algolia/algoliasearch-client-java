package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.iterators.IndexIterable;
import com.algolia.search.iterators.RulesIterable;
import com.algolia.search.iterators.SynonymsIterable;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.sync.Task;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class SyncAccountClient {

  /**
   * Copies the 1st search_index into the second one. As indices could be linked to different
   * applications, the operation cannot simply copy the data with a move operation.
   *
   * @param sourceIndex the source index
   * @param destinationIndex the destination index
   * @throws AlgoliaException if destination already exists
   */
  public static <T> List<Long> copyIndex(
      @Nonnull Index<T> sourceIndex, @Nonnull Index<T> destinationIndex) throws AlgoliaException {
    return copyIndex(sourceIndex, destinationIndex, new RequestOptions());
  }

  /**
   * Copies the 1st search_index into the second one. As indices could be linked to different
   * applications, the operation cannot simply copy the data with a move operation.
   *
   * @param sourceIndex the source index
   * @param destinationIndex the destination index
   * @param requestOptions requestOptions
   * @throws AlgoliaException if destination already exists
   */
  public static <T> List<Long> copyIndex(
      @Nonnull Index<T> sourceIndex,
      @Nonnull Index<T> destinationIndex,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {

    // Test if the destination index exists
    IndexSettings destSettings = destinationIndex.getSettings();

    if (destSettings != null) {
      throw new AlgoliaException(
          "Destination index already exists. Please delete it before copying index across applications.");
    }

    List<Long> taskIds = new ArrayList<>();

    // Save settings
    IndexSettings srcSettings = sourceIndex.getSettings();
    Task settingsTask = destinationIndex.setSettings(srcSettings, requestOptions);
    taskIds.add(settingsTask.getTaskID());

    // Save synonyms
    Iterable<AbstractSynonym> synonymsIterable = new SynonymsIterable(sourceIndex);
    List<AbstractSynonym> synonyms = Lists.newArrayList(synonymsIterable);

    Task synonymsTask = destinationIndex.batchSynonyms(synonyms, requestOptions);
    taskIds.add(synonymsTask.getTaskID());

    // Save Rules
    Iterable<Rule> rulesIterable = new RulesIterable(sourceIndex);
    List<Rule> rules = Lists.newArrayList(rulesIterable);

    Task rulesTask = destinationIndex.batchRules(rules, requestOptions);
    taskIds.add(rulesTask.getTaskID());

    // Save Objects
    ArrayList<T> records = new ArrayList<>();
    IndexIterable<T> iterator = sourceIndex.browse(new Query(""));

    for (T object : iterator) {

      if (records.size() == 1000) {
        Task task = destinationIndex.addObjects(records, requestOptions);
        taskIds.add(task.getTaskID());
        records.clear();
      }

      records.add(object);
    }

    if (records.size() > 0) {
      Task task = destinationIndex.addObjects(records, requestOptions);
      taskIds.add(task.getTaskID());
    }

    return taskIds;
  }
}
