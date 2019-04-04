package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaApiException;
import com.algolia.search.exceptions.AlgoliaRetryException;
import com.algolia.search.exceptions.AlgoliaRuntimeException;
import com.algolia.search.exceptions.LaunderThrowable;
import com.algolia.search.models.HttpMethod;
import com.algolia.search.models.RequestOptions;
import com.algolia.search.models.common.CallType;
import com.algolia.search.models.indexing.DeleteResponse;
import com.algolia.search.models.indexing.SearchResult;
import com.algolia.search.models.rules.Rule;
import com.algolia.search.models.rules.RuleQuery;
import com.algolia.search.models.rules.SaveRuleResponse;
import com.algolia.search.util.AlgoliaUtils;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

/**
 * This interface holds all endpoints for Query Rules.
 *
 * @param <T>
 */
public interface SearchIndexRules<T> extends SearchIndexBase<T> {

  /**
   * Get the specified rule by its objectID
   *
   * @param objectID Algolia's objectID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default Rule getRule(@Nonnull String objectID) {
    return LaunderThrowable.await(getRuleAsync(objectID));
  }

  /**
   * Get the specified rule by its objectID
   *
   * @param objectID Algolia's objectID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default Rule getRule(@Nonnull String objectID, RequestOptions requestOptions) {
    return LaunderThrowable.await(getRuleAsync(objectID, requestOptions));
  }

  /**
   * Get the specified rule by its objectID
   *
   * @param objectID Algolia's objectID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<Rule> getRuleAsync(@Nonnull String objectID) {
    return getRuleAsync(objectID, null);
  }

  /**
   * Get the specified rule by its objectID
   *
   * @param objectID Algolia's objectID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<Rule> getRuleAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {
    Objects.requireNonNull(objectID, "The rule ID is required.");

    if (AlgoliaUtils.isEmptyWhiteSpace(objectID)) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return getTransport()
        .executeRequestAsync(
            HttpMethod.GET,
            "/1/indexes/" + getUrlEncodedIndexName() + "/rules/" + objectID,
            CallType.READ,
            Rule.class,
            requestOptions);
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param query The search rule query
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SearchResult<Rule> searchRules(@Nonnull RuleQuery query) {
    return LaunderThrowable.await(searchRulesAsync(query, null));
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param query The search rule query
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SearchResult<Rule> searchRules(@Nonnull RuleQuery query, RequestOptions requestOptions) {
    return LaunderThrowable.await(searchRulesAsync(query, requestOptions));
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param query The search rule query
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SearchResult<Rule>> searchRulesAsync(@Nonnull RuleQuery query) {
    return searchRulesAsync(query, null);
  }

  /**
   * Search for rules matching various criteria.
   *
   * @param query The search rule query
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  @SuppressWarnings("unchecked")
  default CompletableFuture<SearchResult<Rule>> searchRulesAsync(
      @Nonnull RuleQuery query, RequestOptions requestOptions) {

    Objects.requireNonNull(query, "A search query is required.");

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + getUrlEncodedIndexName() + "/rules/search",
            CallType.READ,
            query,
            SearchResult.class,
            Rule.class,
            requestOptions)
        .thenComposeAsync(
            resp -> {
              CompletableFuture<SearchResult<Rule>> r = new CompletableFuture<>();
              r.complete(resp);
              return r;
            },
            getConfig().getExecutor());
  }

  /**
   * Create or update a single rule.
   *
   * @param rule A query rule
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SaveRuleResponse saveRule(@Nonnull Rule rule) {
    return LaunderThrowable.await(saveRuleAsync(rule));
  }

  /**
   * Create or update a single rule.
   *
   * @param rule A query rule
   * @param forwardToReplicas Forward the request to the replicas
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SaveRuleResponse saveRule(@Nonnull Rule rule, @Nonnull Boolean forwardToReplicas) {
    return LaunderThrowable.await(saveRuleAsync(rule, forwardToReplicas));
  }

  /**
   * Create or update a single rule.
   *
   * @param rule A query rule
   * @param forwardToReplicas Forward the request to the replicas
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SaveRuleResponse saveRule(
      @Nonnull Rule rule,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {
    return LaunderThrowable.await(saveRuleAsync(rule, forwardToReplicas, requestOptions));
  }

  /**
   * Create or update a single rule.
   *
   * @param rule A query rule
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SaveRuleResponse saveRule(@Nonnull Rule rule, @Nonnull RequestOptions requestOptions) {
    return LaunderThrowable.await(saveRuleAsync(rule, requestOptions));
  }

  /**
   * Create or update a single rule.
   *
   * @param rule A query rule
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SaveRuleResponse> saveRuleAsync(@Nonnull Rule rule) {
    return saveRuleAsync(rule, new RequestOptions());
  }

  /**
   * Create or update a single rule.
   *
   * @param rule A query rule
   * @param forwardToReplicas Forward the request to the replicas
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SaveRuleResponse> saveRuleAsync(
      @Nonnull Rule rule, @Nonnull Boolean forwardToReplicas) {
    return saveRuleAsync(rule, forwardToReplicas, new RequestOptions());
  }

  /**
   * Create or update a single rule.
   *
   * @param rule A query rule
   * @param forwardToReplicas Forward the request to the replicas
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SaveRuleResponse> saveRuleAsync(
      @Nonnull Rule rule,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(requestOptions, "RequestOptions are required.");
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");

    requestOptions.addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());

    return saveRuleAsync(rule, requestOptions);
  }

  /**
   * Create or update a single rule.
   *
   * @param rule A query rule
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SaveRuleResponse> saveRuleAsync(
      @Nonnull Rule rule, @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(rule, "A rule is required.");
    Objects.requireNonNull(requestOptions, "RequestOptions are required.");

    if (AlgoliaUtils.isNullOrEmptyWhiteSpace(rule.getObjectID())) {
      throw new AlgoliaRuntimeException("objectID must not be null, empty or white spaces.");
    }

    return getTransport()
        .executeRequestAsync(
            HttpMethod.PUT,
            "/1/indexes/" + getUrlEncodedIndexName() + "/rules/" + rule.getObjectID(),
            CallType.WRITE,
            rule,
            SaveRuleResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Create or update a specified set of rules, or all rules.
   *
   * @param rules List of rules
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  default SaveRuleResponse saveRules(@Nonnull List<Rule> rules) {
    return LaunderThrowable.await(saveRulesAsync(rules));
  }

  /**
   * Create or update a specified set of rules, or all rules.
   *
   * @param rules List of rules
   * @param forwardToReplicas Forward to the replicas the request
   * @param clearExistingRules Clear all existing rules
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SaveRuleResponse saveRules(
      @Nonnull List<Rule> rules,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull Boolean clearExistingRules) {
    return LaunderThrowable.await(saveRulesAsync(rules, forwardToReplicas, clearExistingRules));
  }

  /**
   * Create or update a specified set of rules, or all rules.
   *
   * @param rules List of rules
   * @param forwardToReplicas Forward to the replicas the request
   * @param clearExistingRules Clear all existing rules
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SaveRuleResponse saveRules(
      @Nonnull List<Rule> rules,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull Boolean clearExistingRules,
      @Nonnull RequestOptions requestOptions) {
    return LaunderThrowable.await(
        saveRulesAsync(rules, forwardToReplicas, clearExistingRules, requestOptions));
  }

  /**
   * Create or update a specified set of rules, or all rules.
   *
   * @param rules List of rules
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SaveRuleResponse saveRules(@Nonnull List<Rule> rules, RequestOptions requestOptions) {
    return LaunderThrowable.await(saveRulesAsync(rules, requestOptions));
  }

  /**
   * Create or update a specified set of rules, or all rules.
   *
   * @param rules List of rules
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When the class doesn't have an objectID field or a
   *     Jacksonannotation @JsonProperty(\"objectID\"")
   */
  default CompletableFuture<SaveRuleResponse> saveRulesAsync(@Nonnull List<Rule> rules) {
    return saveRulesAsync(rules, new RequestOptions());
  }

  /**
   * Create or update a specified set of rules, or all rules.
   *
   * @param rules List of rules
   * @param forwardToReplicas Forward to the replicas the request
   * @param clearExistingRules Clear all existing rules
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SaveRuleResponse> saveRulesAsync(
      @Nonnull List<Rule> rules,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull Boolean clearExistingRules) {

    return saveRulesAsync(rules, forwardToReplicas, clearExistingRules, new RequestOptions());
  }

  /**
   * Create or update a specified set of rules, or all rules.
   *
   * @param rules List of rules
   * @param forwardToReplicas Forward to the replicas the request
   * @param clearExistingRules Clear all existing rules
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SaveRuleResponse> saveRulesAsync(
      @Nonnull List<Rule> rules,
      @Nonnull Boolean forwardToReplicas,
      @Nonnull Boolean clearExistingRules,
      @Nonnull RequestOptions requestOptions) {

    Objects.requireNonNull(requestOptions, "RequestOptions are required.");
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    Objects.requireNonNull(clearExistingRules, "clearExistingRules is required.");

    requestOptions
        .addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString())
        .addExtraQueryParameters("clearExistingRules", clearExistingRules.toString());

    return saveRulesAsync(rules, requestOptions);
  }

  /**
   * Create or update a specified set of rules, or all rules.
   *
   * @param rules List of rules
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SaveRuleResponse> saveRulesAsync(
      @Nonnull List<Rule> rules, RequestOptions requestOptions) {

    Objects.requireNonNull(rules, "Rules are required.");

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + getUrlEncodedIndexName() + "/rules/batch",
            CallType.WRITE,
            rules,
            SaveRuleResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Delete the rule for the given ruleId
   *
   * @param objectID The rule objectID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default DeleteResponse deleteRule(@Nonnull String objectID) {
    return LaunderThrowable.await(deleteRuleAsync(objectID));
  }

  /**
   * Delete the rule for the given ruleId
   *
   * @param objectID The rule objectID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default DeleteResponse deleteRule(@Nonnull String objectID, RequestOptions requestOptions) {
    return LaunderThrowable.await(deleteRuleAsync(objectID, requestOptions));
  }

  /**
   * Delete the rule for the given ruleId
   *
   * @param objectID The rule objectID
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<DeleteResponse> deleteRuleAsync(@Nonnull String objectID) {
    return deleteRuleAsync(objectID, null);
  }

  /**
   * Delete the rule for the given ruleId
   *
   * @param objectID The rule objectID
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<DeleteResponse> deleteRuleAsync(
      @Nonnull String objectID, RequestOptions requestOptions) {
    Objects.requireNonNull(objectID, "The objectID is required.");

    if (AlgoliaUtils.isNullOrEmptyWhiteSpace(objectID)) {
      throw new AlgoliaRuntimeException("objectID must not be empty.");
    }

    return getTransport()
        .executeRequestAsync(
            HttpMethod.DELETE,
            "/1/indexes/" + getUrlEncodedIndexName() + "/rules/" + objectID,
            CallType.WRITE,
            DeleteResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }

  /**
   * Push a new set of rules and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing rules are deleted and replaced with the new ones, in a
   * single, atomic operation
   *
   * @param rules List of rules
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SaveRuleResponse replaceAllRules(@Nonnull List<Rule> rules) {
    return LaunderThrowable.await(replaceAllRulesAsync(rules));
  }

  /**
   * Push a new set of rules and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing rules are deleted and replaced with the new ones, in a
   * single, atomic operation
   *
   * @param rules List of rules
   * @param forwardToReplicas Forward to the replicas the request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SaveRuleResponse replaceAllRules(
      @Nonnull List<Rule> rules, @Nonnull Boolean forwardToReplicas) {
    return LaunderThrowable.await(replaceAllRulesAsync(rules, forwardToReplicas));
  }

  /**
   * Push a new set of rules and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing rules are deleted and replaced with the new ones, in a
   * single, atomic operation
   *
   * @param rules List of rules
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default SaveRuleResponse replaceAllRules(
      @Nonnull List<Rule> rules, @Nonnull RequestOptions requestOptions) {
    return LaunderThrowable.await(replaceAllRulesAsync(rules, requestOptions));
  }

  /**
   * Push a new set of rules and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing rules are deleted and replaced with the new ones, in a
   * single, atomic operation
   *
   * @param rules List of rules
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SaveRuleResponse> replaceAllRulesAsync(@Nonnull List<Rule> rules) {
    return saveRulesAsync(rules, false, true, new RequestOptions());
  }

  /**
   * Push a new set of rules and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing rules are deleted and replaced with the new ones, in a
   * single, atomic operation
   *
   * @param rules List of rules
   * @param forwardToReplicas Forward to the replicas the request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SaveRuleResponse> replaceAllRulesAsync(
      @Nonnull List<Rule> rules, @Nonnull Boolean forwardToReplicas) {
    return saveRulesAsync(rules, forwardToReplicas, true, new RequestOptions());
  }

  /**
   * Push a new set of rules and erase all previous ones. This method, like replaceAllObjects,
   * guarantees zero downtime. All existing rules are deleted and replaced with the new ones, in a
   * single, atomic operation
   *
   * @param rules List of rules
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<SaveRuleResponse> replaceAllRulesAsync(
      @Nonnull List<Rule> rules, @Nonnull RequestOptions requestOptions) {
    return saveRulesAsync(rules, false, true, requestOptions);
  }

  /**
   * Delete all rules in an index.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default DeleteResponse clearRules() {
    return LaunderThrowable.await(clearRulesAsync());
  }

  /**
   * Delete all rules in an index.
   *
   * @param forwardToReplicas Forward the request to the replicas if so
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default DeleteResponse clearRules(@Nonnull Boolean forwardToReplicas) {
    return LaunderThrowable.await(clearRulesAsync(forwardToReplicas));
  }

  /**
   * Delete all rules in an index.
   *
   * @param forwardToReplicas Forward the request to the replicas if so
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default DeleteResponse clearRules(
      @Nonnull Boolean forwardToReplicas, RequestOptions requestOptions) {
    return LaunderThrowable.await(clearRulesAsync(forwardToReplicas, requestOptions));
  }

  /**
   * Delete all rules in an index.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default DeleteResponse clearRules(RequestOptions requestOptions) {
    return LaunderThrowable.await(clearRulesAsync(requestOptions));
  }

  /**
   * Delete all rules in an index.
   *
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<DeleteResponse> clearRulesAsync() {
    return clearRulesAsync(false);
  }

  /**
   * Delete all rules in an index.
   *
   * @param forwardToReplicas Forward the request to the replicas if so
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<DeleteResponse> clearRulesAsync(@Nonnull Boolean forwardToReplicas) {
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    RequestOptions requestOptions =
        new RequestOptions()
            .addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());
    return clearRulesAsync(requestOptions);
  }

  /**
   * Delete all rules in an index.
   *
   * @param forwardToReplicas Forward the request to the replicas if so
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<DeleteResponse> clearRulesAsync(
      @Nonnull Boolean forwardToReplicas, RequestOptions requestOptions) {
    Objects.requireNonNull(forwardToReplicas, "ForwardToReplicas is required.");
    requestOptions.addExtraQueryParameters("forwardToReplicas", forwardToReplicas.toString());
    return clearRulesAsync(requestOptions);
  }

  /**
   * Delete all rules in an index.
   *
   * @param requestOptions Options to pass to this request
   * @throws AlgoliaRetryException When the retry has failed on all hosts
   * @throws AlgoliaApiException When the API sends an http error code
   * @throws AlgoliaRuntimeException When an error occurred during the serialization
   */
  default CompletableFuture<DeleteResponse> clearRulesAsync(RequestOptions requestOptions) {

    return getTransport()
        .executeRequestAsync(
            HttpMethod.POST,
            "/1/indexes/" + getUrlEncodedIndexName() + "/rules/clear",
            CallType.WRITE,
            DeleteResponse.class,
            requestOptions)
        .thenApplyAsync(
            resp -> {
              resp.setWaitConsumer(this::waitTask);
              return resp;
            },
            getConfig().getExecutor());
  }
}
