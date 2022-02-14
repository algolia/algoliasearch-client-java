<?php

namespace Algolia\AlgoliaSearch\Api;

use Algolia\AlgoliaSearch\Algolia;
use Algolia\AlgoliaSearch\Configuration\QuerySuggestionsConfig;
use Algolia\AlgoliaSearch\ObjectSerializer;
use Algolia\AlgoliaSearch\RetryStrategy\ApiWrapper;
use Algolia\AlgoliaSearch\RetryStrategy\ApiWrapperInterface;
use Algolia\AlgoliaSearch\RetryStrategy\ClusterHosts;

/**
 * QuerySuggestionsApi Class Doc Comment
 *
 * @category Class
 * @package  Algolia\AlgoliaSearch
 */
class QuerySuggestionsApi
{
    /**
     * @var ApiWrapperInterface
     */
    protected $api;

    /**
     * @var QuerySuggestionsConfig
     */
    protected $config;

    /**
     * @param QuerySuggestionsConfig $config
     * @param ApiWrapperInterface $apiWrapper
     */
    public function __construct(ApiWrapperInterface $apiWrapper, QuerySuggestionsConfig $config)
    {
        $this->config = $config;

        $this->api = $apiWrapper;
    }

    /**
     * Instantiate the client with basic credentials and region
     *
     * @param string $appId  Application ID
     * @param string $apiKey Algolia API Key
     * @param string $region Region
     */
    public static function create($appId = null, $apiKey = null, $region = null)
    {
        $allowedRegions = explode('-', 'us-eu');
        $config = QuerySuggestionsConfig::create($appId, $apiKey, $region, $allowedRegions);

        return static::createWithConfig($config);
    }

    /**
     * Instantiate the client with congiguration
     *
     * @param QuerySuggestionsConfig $config Configuration
     */
    public static function createWithConfig(QuerySuggestionsConfig $config)
    {
        $config = clone $config;

        if ($hosts = $config->getHosts()) {
            // If a list of hosts was passed, we ignore the cache
            $clusterHosts = ClusterHosts::create($hosts);
        } else {
            $clusterHosts = ClusterHosts::create('query-suggestions.'.$config->getRegion().'.algolia.com');
        }

        $apiWrapper = new ApiWrapper(
            Algolia::getHttpClient(),
            $config,
            $clusterHosts
        );

        return new static($apiWrapper, $config);
    }

    /**
     * @return QuerySuggestionsConfig
     */
    public function getClientConfig()
    {
        return $this->config;
    }

    /**
     * Operation createConfig
     *
     * Create a configuration of a Query Suggestions index.
     *
     * @param  \Algolia\AlgoliaSearch\Model\QuerySuggestions\QuerySuggestionsIndexWithIndexParam $querySuggestionsIndexWithIndexParam querySuggestionsIndexWithIndexParam (required)
     *
     * @throws \Algolia\AlgoliaSearch\ApiException on non-2xx response
     * @throws \InvalidArgumentException
     *
     * @return \Algolia\AlgoliaSearch\Model\QuerySuggestions\SucessResponse|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase
     */
    public function createConfig($querySuggestionsIndexWithIndexParam)
    {
        // verify the required parameter 'querySuggestionsIndexWithIndexParam' is set
        if ($querySuggestionsIndexWithIndexParam === null || (is_array($querySuggestionsIndexWithIndexParam) && count($querySuggestionsIndexWithIndexParam) === 0)) {
            throw new \InvalidArgumentException(
                'Missing the required parameter $querySuggestionsIndexWithIndexParam when calling createConfig'
            );
        }

        $resourcePath = '/1/configs';
        $queryParams = [];
        $httpBody = [];

        if (isset($querySuggestionsIndexWithIndexParam)) {
            $httpBody = $querySuggestionsIndexWithIndexParam;
        }

        return $this->sendRequest('POST', $resourcePath, $queryParams, $httpBody);
    }
    /**
     * Operation deleteConfig
     *
     * Delete a configuration of a Query Suggestion&#39;s index.
     *
     * @param  string $indexName The index in which to perform the request. (required)
     *
     * @throws \Algolia\AlgoliaSearch\ApiException on non-2xx response
     * @throws \InvalidArgumentException
     *
     * @return \Algolia\AlgoliaSearch\Model\QuerySuggestions\SucessResponse|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase
     */
    public function deleteConfig($indexName)
    {
        // verify the required parameter 'indexName' is set
        if ($indexName === null || (is_array($indexName) && count($indexName) === 0)) {
            throw new \InvalidArgumentException(
                'Missing the required parameter $indexName when calling deleteConfig'
            );
        }

        $resourcePath = '/1/configs/{indexName}';
        $queryParams = [];
        $httpBody = [];
        // path params
        if ($indexName !== null) {
            $resourcePath = str_replace(
                '{' . 'indexName' . '}',
                ObjectSerializer::toPathValue($indexName),
                $resourcePath
            );
        }

        return $this->sendRequest('DELETE', $resourcePath, $queryParams, $httpBody);
    }
    /**
     * Operation getAllConfigs
     *
     * Get all the configurations of Query Suggestions.
     *
     *
     * @throws \Algolia\AlgoliaSearch\ApiException on non-2xx response
     * @throws \InvalidArgumentException
     *
     * @return \Algolia\AlgoliaSearch\Model\QuerySuggestions\QuerySuggestionsIndex[]|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase
     */
    public function getAllConfigs()
    {
        $resourcePath = '/1/configs';
        $queryParams = [];
        $httpBody = [];

        return $this->sendRequest('GET', $resourcePath, $queryParams, $httpBody);
    }
    /**
     * Operation getConfig
     *
     * Get the configuration of a single Query Suggestions index.
     *
     * @param  string $indexName The index in which to perform the request. (required)
     *
     * @throws \Algolia\AlgoliaSearch\ApiException on non-2xx response
     * @throws \InvalidArgumentException
     *
     * @return \Algolia\AlgoliaSearch\Model\QuerySuggestions\QuerySuggestionsIndex|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase
     */
    public function getConfig($indexName)
    {
        // verify the required parameter 'indexName' is set
        if ($indexName === null || (is_array($indexName) && count($indexName) === 0)) {
            throw new \InvalidArgumentException(
                'Missing the required parameter $indexName when calling getConfig'
            );
        }

        $resourcePath = '/1/configs/{indexName}';
        $queryParams = [];
        $httpBody = [];
        // path params
        if ($indexName !== null) {
            $resourcePath = str_replace(
                '{' . 'indexName' . '}',
                ObjectSerializer::toPathValue($indexName),
                $resourcePath
            );
        }

        return $this->sendRequest('GET', $resourcePath, $queryParams, $httpBody);
    }
    /**
     * Operation getConfigStatus
     *
     * Get the status of a Query Suggestion&#39;s index.
     *
     * @param  string $indexName The index in which to perform the request. (required)
     *
     * @throws \Algolia\AlgoliaSearch\ApiException on non-2xx response
     * @throws \InvalidArgumentException
     *
     * @return \Algolia\AlgoliaSearch\Model\QuerySuggestions\Status|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase
     */
    public function getConfigStatus($indexName)
    {
        // verify the required parameter 'indexName' is set
        if ($indexName === null || (is_array($indexName) && count($indexName) === 0)) {
            throw new \InvalidArgumentException(
                'Missing the required parameter $indexName when calling getConfigStatus'
            );
        }

        $resourcePath = '/1/configs/{indexName}/status';
        $queryParams = [];
        $httpBody = [];
        // path params
        if ($indexName !== null) {
            $resourcePath = str_replace(
                '{' . 'indexName' . '}',
                ObjectSerializer::toPathValue($indexName),
                $resourcePath
            );
        }

        return $this->sendRequest('GET', $resourcePath, $queryParams, $httpBody);
    }
    /**
     * Operation getLogFile
     *
     * Get the log file of the last build of a single Query Suggestion index.
     *
     * @param  string $indexName The index in which to perform the request. (required)
     *
     * @throws \Algolia\AlgoliaSearch\ApiException on non-2xx response
     * @throws \InvalidArgumentException
     *
     * @return \Algolia\AlgoliaSearch\Model\QuerySuggestions\LogFile[]|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase
     */
    public function getLogFile($indexName)
    {
        // verify the required parameter 'indexName' is set
        if ($indexName === null || (is_array($indexName) && count($indexName) === 0)) {
            throw new \InvalidArgumentException(
                'Missing the required parameter $indexName when calling getLogFile'
            );
        }

        $resourcePath = '/1/logs/{indexName}';
        $queryParams = [];
        $httpBody = [];
        // path params
        if ($indexName !== null) {
            $resourcePath = str_replace(
                '{' . 'indexName' . '}',
                ObjectSerializer::toPathValue($indexName),
                $resourcePath
            );
        }

        return $this->sendRequest('GET', $resourcePath, $queryParams, $httpBody);
    }
    /**
     * Operation updateConfig
     *
     * Update the configuration of a Query Suggestions index.
     *
     * @param  string $indexName The index in which to perform the request. (required)
     * @param  \Algolia\AlgoliaSearch\Model\QuerySuggestions\QuerySuggestionsIndexParam $querySuggestionsIndexParam querySuggestionsIndexParam (required)
     *
     * @throws \Algolia\AlgoliaSearch\ApiException on non-2xx response
     * @throws \InvalidArgumentException
     *
     * @return \Algolia\AlgoliaSearch\Model\QuerySuggestions\SucessResponse|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase|\Algolia\AlgoliaSearch\Model\QuerySuggestions\ErrorBase
     */
    public function updateConfig($indexName, $querySuggestionsIndexParam)
    {
        // verify the required parameter 'indexName' is set
        if ($indexName === null || (is_array($indexName) && count($indexName) === 0)) {
            throw new \InvalidArgumentException(
                'Missing the required parameter $indexName when calling updateConfig'
            );
        }
        // verify the required parameter 'querySuggestionsIndexParam' is set
        if ($querySuggestionsIndexParam === null || (is_array($querySuggestionsIndexParam) && count($querySuggestionsIndexParam) === 0)) {
            throw new \InvalidArgumentException(
                'Missing the required parameter $querySuggestionsIndexParam when calling updateConfig'
            );
        }

        $resourcePath = '/1/configs/{indexName}';
        $queryParams = [];
        $httpBody = [];
        // path params
        if ($indexName !== null) {
            $resourcePath = str_replace(
                '{' . 'indexName' . '}',
                ObjectSerializer::toPathValue($indexName),
                $resourcePath
            );
        }

        if (isset($querySuggestionsIndexParam)) {
            $httpBody = $querySuggestionsIndexParam;
        }

        return $this->sendRequest('PUT', $resourcePath, $queryParams, $httpBody);
    }

    private function sendRequest($method, $resourcePath, $queryParams, $httpBody)
    {
        $query = \GuzzleHttp\Psr7\Query::build($queryParams);

        if ($method === 'GET') {
            $request = $this->api->read(
                $method,
                $resourcePath . ($query ? "?{$query}" : '')
            );
        } else {
            $request = $this->api->write(
                $method,
                $resourcePath . ($query ? "?{$query}" : ''),
                $httpBody
            );
        }

        return $request;
    }
}