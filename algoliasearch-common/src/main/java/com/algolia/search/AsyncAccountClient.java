package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.inputs.synonym.AbstractSynonym;
import com.algolia.search.iterators.AsyncIndexIterable;
import com.algolia.search.iterators.AsyncRulesIterable;
import com.algolia.search.iterators.AsyncSynonymsIterable;
import com.algolia.search.objects.IndexSettings;
import com.algolia.search.objects.Query;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.tasks.async.AsyncTask;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;

public class AsyncAccountClient {

  /**
   * Copies the 1st search_index into the second one. As indices could be linked to different
   * applications, the operation cannot simply copy the data with a move operation.
   *
   * @param sourceIndex the source index
   * @param destinationIndex the destination index
   * @throws AlgoliaException if destination already exists
   */
  public static <T> List<Long> copyIndex(
      @Nonnull AsyncIndex<T> sourceIndex, @Nonnull AsyncIndex<T> destinationIndex)
      throws AlgoliaException, ExecutionException, InterruptedException {
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
      @Nonnull AsyncIndex<T> sourceIndex,
      @Nonnull AsyncIndex<T> destinationIndex,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException, ExecutionException, InterruptedException {

    // Test if the destination index exists
    IndexSettings destSettings = destinationIndex.getSettings().get();

    if (destSettings != null) {
      throw new AlgoliaException(
          "Destination index already exists. Please delete it before copying index across applications.");
    }

    List<Long> taskIds = new ArrayList<>();

    // Save settings
    IndexSettings srcSettings = sourceIndex.getSettings().get();
    AsyncTask settingsTask = destinationIndex.setSettings(srcSettings, requestOptions).get();
    taskIds.add(settingsTask.getTaskID());

    // Save synonyms
    Iterable<AbstractSynonym> synonymsIterable = new AsyncSynonymsIterable(sourceIndex);
    List<AbstractSynonym> synonyms = Lists.newArrayList(synonymsIterable);

    AsyncTask synonymsTask = destinationIndex.batchSynonyms(synonyms, requestOptions).get();
    taskIds.add(synonymsTask.getTaskID());

    // Save Rules
    Iterable<Rule> rulesIterable = new AsyncRulesIterable(sourceIndex);
    List<Rule> rules = Lists.newArrayList(rulesIterable);

    AsyncTask rulesTask = destinationIndex.batchRules(rules, requestOptions).get();
    taskIds.add(rulesTask.getTaskID());

    // Save Objects
    ArrayList<T> records = new ArrayList<>();
    AsyncIndexIterable<T> iterator = sourceIndex.browse(new Query(""));

    for (T object : iterator) {

      if (records.size() == 1000) {
        AsyncTask task = destinationIndex.addObjects(records, requestOptions).get();
        taskIds.add(task.getTaskID());
        records.clear();
      }

      records.add(object);
    }

    if (records.size() > 0) {
      AsyncTask task = destinationIndex.addObjects(records, requestOptions).get();
      taskIds.add(task.getTaskID());
    }

    return taskIds;
  }
}
