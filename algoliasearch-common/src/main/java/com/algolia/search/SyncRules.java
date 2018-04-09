package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.query_rules.Rule;
import com.algolia.search.objects.RequestOptions;
import com.algolia.search.objects.RuleQuery;
import com.algolia.search.objects.tasks.sync.Task;
import com.algolia.search.responses.SearchRuleResult;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

public interface SyncRules<T> extends SyncBaseIndex<T> {

  /**
   * Saves/updates a query rule without replacing it and NOT forwarding it to the replicas
   *
   * @param queryRuleID the id of the queryRule
   * @param content the queryRule
   * @return the associated task
   */
  default Task saveRule(@Nonnull String queryRuleID, @Nonnull Rule content)
      throws AlgoliaException {
    return saveRule(queryRuleID, content, false);
  }

  /**
   * Saves/updates a query rule without replacing it and NOT forwarding it to the replicas
   *
   * @param queryRuleID the id of the queryRule
   * @param content the queryRule
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default Task saveRule(
      @Nonnull String queryRuleID, @Nonnull Rule content, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return saveRule(queryRuleID, content, false, requestOptions);
  }

  /**
   * Saves/updates a queryRule without replacing
   *
   * @param queryRuleID the id of the queryRule
   * @param content the queryRule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @return the associated task
   */
  default Task saveRule(
      @Nonnull String queryRuleID, @Nonnull Rule content, boolean forwardToReplicas)
      throws AlgoliaException {
    return saveRule(queryRuleID, content, forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Saves/updates a queryRule without replacing
   *
   * @param queryRuleID the id of the queryRule
   * @param content the queryRule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default Task saveRule(
      @Nonnull String queryRuleID,
      @Nonnull Rule content,
      boolean forwardToReplicas,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    if (queryRuleID.isEmpty()) {
      throw new AlgoliaException("Cannot save rule with empty queryRuleID");
    }
    return getApiClient()
        .saveRule(getName(), queryRuleID, content, forwardToReplicas, requestOptions);
  }

  /**
   * Get a rule by Id
   *
   * @param ruleId the id of the query rule
   * @return the associated task
   */
  default Optional<Rule> getRule(@Nonnull String ruleId) throws AlgoliaException {
    return getRule(ruleId, RequestOptions.empty);
  }

  /**
   * Get a rule by Id
   *
   * @param ruleId the id of the query rule
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default Optional<Rule> getRule(@Nonnull String ruleId, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().getRule(getName(), ruleId, requestOptions);
  }

  /**
   * Deletes a query rule by ID and NOT forwarding it to the replicas
   *
   * @param ruleId the id of the queryRule
   * @return the associated task
   */
  default Task deleteRule(@Nonnull String ruleId) throws AlgoliaException {
    return deleteRule(ruleId, false, RequestOptions.empty);
  }

  /**
   * Deletes a query rule by ID and NOT forwarding it to the replicas
   *
   * @param ruleId the id of the queryRule
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default Task deleteRule(@Nonnull String ruleId, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return deleteRule(ruleId, false, requestOptions);
  }

  /**
   * Deletes a query rule
   *
   * @param ruleId the id of the query rule
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default Task deleteRule(
      @Nonnull String ruleId, boolean forwardToReplicas, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().deleteRule(getName(), ruleId, forwardToReplicas, requestOptions);
  }

  /**
   * Clear all query Rules and NOT forwarding it to the replicas
   *
   * @return the associated task
   */
  default Task clearRules() throws AlgoliaException {
    return clearRules(false);
  }

  /**
   * Clear all query Rules and NOT forwarding it to the replicas
   *
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default Task clearRules(@Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return clearRules(false, requestOptions);
  }

  /**
   * Clears all Rules
   *
   * @return the associated task
   */
  default Task clearRules(boolean forwardToReplicas) throws AlgoliaException {
    return clearRules(forwardToReplicas, RequestOptions.empty);
  }

  /**
   * Clears all Rules
   *
   * @param forwardToReplicas should this request be forwarded to replicas
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default Task clearRules(boolean forwardToReplicas, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient().clearRules(getName(), forwardToReplicas, requestOptions);
  }

  /**
   * Search for Rules
   *
   * @param query the query
   * @return the results of the query
   */
  default SearchRuleResult searchRules(@Nonnull RuleQuery query) throws AlgoliaException {
    return searchRules(query, RequestOptions.empty);
  }

  /**
   * Search for Rules
   *
   * @param query the query
   * @param requestOptions Options to pass to this request
   * @return the results of the query
   */
  default SearchRuleResult searchRules(
      @Nonnull RuleQuery query, @Nonnull RequestOptions requestOptions) throws AlgoliaException {
    return getApiClient().searchRules(getName(), query, requestOptions);
  }

  /**
   * Add or replace a list of query Rules
   *
   * @param rules List of query Rules
   * @param forwardToReplicas Forward the operation to the slave indices
   * @param clearExistingRules Replace the existing query Rules with this batch
   * @return the associated task
   */
  default Task batchRules(
      @Nonnull List<Rule> rules, boolean forwardToReplicas, boolean clearExistingRules)
      throws AlgoliaException {
    return batchRules(rules, forwardToReplicas, clearExistingRules, RequestOptions.empty);
  }

  /**
   * Add or replace a list of query Rules
   *
   * @param rules List of query Rules
   * @param forwardToReplicas Forward the operation to the slave indices
   * @param clearExistingRules Replace the existing query Rules with this batch
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default Task batchRules(
      @Nonnull List<Rule> rules,
      boolean forwardToReplicas,
      boolean clearExistingRules,
      @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return getApiClient()
        .batchRules(getName(), rules, forwardToReplicas, clearExistingRules, requestOptions);
  }

  /**
   * Add or replace a list of Rules, no replacement
   *
   * @param rules List of Rules
   * @param forwardToReplicas Forward the operation to the slave indices
   * @return the associated task
   */
  default Task batchRules(@Nonnull List<Rule> rules, boolean forwardToReplicas)
      throws AlgoliaException {
    return batchRules(rules, forwardToReplicas, false);
  }

  /**
   * Add or replace a list of Rules, no replacement
   *
   * @param rules List of Rules
   * @param forwardToReplicas Forward the operation to the slave indices
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default Task batchRules(
      @Nonnull List<Rule> rules, boolean forwardToReplicas, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return batchRules(rules, forwardToReplicas, false, requestOptions);
  }

  /**
   * Add or replace a list of Rules, no forward to replicas, and no replacement
   *
   * @param rules List of Rules
   * @return the associated task
   */
  default Task batchRules(@Nonnull List<Rule> rules) throws AlgoliaException {
    return batchRules(rules, false, false);
  }

  /**
   * Add or replace a list of Rules, no forward to replicas, and no replacement
   *
   * @param rules List of Rules
   * @param requestOptions Options to pass to this request
   * @return the associated task
   */
  default Task batchRules(@Nonnull List<Rule> rules, @Nonnull RequestOptions requestOptions)
      throws AlgoliaException {
    return batchRules(rules, false, false, requestOptions);
  }
}
