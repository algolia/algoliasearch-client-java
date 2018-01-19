package com.algolia.search;

import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.RuleQuery;
import com.algolia.search.objects.tasks.async.AsyncTask;
import com.algolia.search.responses.SearchRuleResult;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface AsyncRules<T> extends AsyncBaseIndex<T> {

  /**
   * Saves/updates a rule without replacing it and NOT forwarding it to the replicas
   *
   * @param ruleID the id of the query rule
   * @param rule the query rule
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveRule(@Nonnull String ruleID, @Nonnull Rule rule) {
    return saveRule(ruleID, rule, false);
  }

  /**
   * Saves/updates a rule without replacing it and NOT forwarding it to the replicas
   *
   * @param ruleID the id of the query rule
   * @param rule the query rule
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveRule(
      @Nonnull String ruleID, @Nonnull Rule rule, @Nonnull RequestOptions requestOptions) {
    return saveRule(ruleID, rule, false, requestOptions);
  }

  /**
   * Saves/updates a rule
   *
   * @param ruleID the id of the query rule
   * @param rule the query rule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveRule(
      @Nonnull String ruleID, @Nonnull Rule rule, boolean forwardToReplicas) {
    return saveRule(ruleID, rule, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Saves/updates a rule
   *
   * @param ruleID the id of the query rule
   * @param rule the query rule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> saveRule(
      @Nonnull String ruleID,
      @Nonnull Rule rule,
      boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {
    return getApiClient().saveRule(getName(), ruleID, rule, forwardToReplicas, requestOptions);
  }

  /**
   * Get a rule by ID
   *
   * @param ruleID the id of the rule
   * @return the associated task
   */
  default CompletableFuture<Optional<Rule>> getRule(@Nonnull String ruleID) {
    return getRule(ruleID, RequestOptions.empty);
  }

  /**
   * Get a rule by ID
   *
   * @param ruleID the id of the rule
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<Optional<Rule>> getRule(
      @Nonnull String ruleID, @Nonnull RequestOptions requestOptions) {
    return getApiClient().getRule(getName(), ruleID, requestOptions);
  }

  /**
   * Deletes a rule by ID and NOT forwarding it to the replicas
   *
   * @param ruleID the id of the query rule
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteRule(@Nonnull String ruleID) {
    return deleteRule(ruleID, false);
  }

  /**
   * Deletes a rule by ID and NOT forwarding it to the replicas
   *
   * @param ruleID the id of the query rule
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteRule(
      @Nonnull String ruleID, @Nonnull RequestOptions requestOptions) {
    return deleteRule(ruleID, false, requestOptions);
  }

  /**
   * Deletes a rule
   *
   * @param ruleID the id of the rule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteRule(
      @Nonnull String ruleID, boolean forwardToReplicas) {
    return deleteRule(ruleID, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Deletes a rule
   *
   * @param ruleID the id of the rule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> deleteRule(
      @Nonnull String ruleID, boolean forwardToReplicas, @Nonnull RequestOptions requestOptions) {
    return getApiClient().deleteRule(getName(), ruleID, forwardToReplicas, requestOptions);
  }

  /**
   * Clear all Rules and NOT forwarding it to the replicas
   *
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearRules() {
    return clearRules(false);
  }

  /**
   * Clear all Rules and NOT forwarding it to the replicas
   *
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearRules(@Nonnull RequestOptions requestOptions) {
    return clearRules(false, requestOptions);
  }

  /**
   * Clears all Rules
   *
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearRules(boolean forwardToReplicas) {
    return clearRules(forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Clears all Rules
   *
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> clearRules(
      boolean forwardToReplicas, @Nonnull RequestOptions requestOptions) {
    return getApiClient().clearRules(getName(), forwardToReplicas, requestOptions);
  }

  /**
   * Search for Rules
   *
   * @param query the query
   * @return the results of the query
   */
  default CompletableFuture<SearchRuleResult> searchRules(@Nonnull RuleQuery query) {
    return searchRules(query, RequestOptions.empty);
  }

  /**
   * Search for Rules
   *
   * @param requestOptions Options to pass to this request
   * @param query the query
   * @return the results of the query
   */
  default CompletableFuture<SearchRuleResult> searchRules(
      @Nonnull RuleQuery query, @Nonnull RequestOptions requestOptions) {
    return getApiClient().searchRules(getName(), query, requestOptions);
  }

  /**
   * Add or replace a list of Rules
   *
   * @param rules List of Rules
   * @param forwardToReplicas Forward the operation to the replicas indices
   * @param clearExistingRules Replace the existing Rules with this batch
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchRules(
      @Nonnull List<Rule> rules, boolean forwardToReplicas, boolean clearExistingRules) {
    return batchRules(rules, forwardToReplicas, clearExistingRules, RequestOptions.empty);
  }

  /**
   * Add or replace a list of Rules
   *
   * @param rules List of Rules
   * @param forwardToReplicas Forward the operation to the replicas indices
   * @param clearExistingRules Replace the existing Rules with this batch
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchRules(
      @Nonnull List<Rule> rules,
      boolean forwardToReplicas,
      boolean clearExistingRules,
      @Nonnull RequestOptions requestOptions) {
    return getApiClient()
        .batchRules(getName(), rules, forwardToReplicas, clearExistingRules, requestOptions);
  }

  /**
   * Add or Replace a list of Rules, no replacement
   *
   * @param rules List of Rules
   * @param forwardToReplicas Forward the operation to the slave indices
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchRules(
      @Nonnull List<Rule> rules, boolean forwardToReplicas) {
    return batchRules(rules, forwardToReplicas, false);
  }

  /**
   * Add or Replace a list of Rules, no replacement
   *
   * @param rules List of Rules
   * @param forwardToReplicas Forward the operation to the slave indices
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchRules(
      @Nonnull List<Rule> rules,
      boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions) {
    return batchRules(rules, forwardToReplicas, false, requestOptions);
  }

  /**
   * Add or Replace a list of Rules, no forward to replicas, and no replacement
   *
   * @param rules List of Rules
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchRules(@Nonnull List<Rule> rules) {
    return batchRules(rules, false, false);
  }

  /**
   * Add or Replace a list of Rules, no forward to replicas, and no replacement
   *
   * @param rules List of Rules
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default CompletableFuture<AsyncTask> batchRules(
      @Nonnull List<Rule> rules, @Nonnull RequestOptions requestOptions) {
    return batchRules(rules, false, false, requestOptions);
  }
}
