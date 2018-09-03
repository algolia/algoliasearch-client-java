# ChangeLog

# [2.19.0](https://github.com/algolia/algoliasearch-client-java-2/compare/2.18.1...2.19.0) (2018-09-03)

### Summary

Hello everyone,

Coming back from vacations, let's start this month of September with a few
minor but welcome changes: support for new settings and query parameters. The
list of changes speaks for itself this time. Feel free to consult the details
of each parameter in [the official Algolia documentation](https://www.algolia.com/doc/api-reference/api-parameters/).

### Changes

- **chore(md):** Update contribution-related files ([65c6dab](https://github.com/algolia/algoliasearch-client-java-2/commit/65c6dab))
- **feat:** Expose decompoundedAttributes as a setting parameter ([3285561](https://github.com/algolia/algoliasearch-client-java-2/commit/3285561))
- **feat:** Expose camelCaseAttributes as a setting parameter ([e2e3712](https://github.com/algolia/algoliasearch-client-java-2/commit/e2e3712))
- **feat:** Expose queryLanguages as a setting and search parameter ([04d160f](https://github.com/algolia/algoliasearch-client-java-2/commit/04d160f))
- **feat:** Implement keepDiacriticsOnCharacters as a setting parameter ([5fd418a](https://github.com/algolia/algoliasearch-client-java-2/commit/5fd418a))
- **misc:** [maven-release-plugin] prepare for next development iteration ([5602826](https://github.com/algolia/algoliasearch-client-java-2/commit/5602826))

# [2.18.1](https://github.com/algolia/algoliasearch-client-java-2/compare/2.18.0...2.18.1) (2018-08-07)

### Summary

Hello everyone,

Quick patch release for our Java client because of a bug that was introduced in
the previous version that would make calls to Algolia fail when debug logging
was enabled.

The rest of the changes are internal only, aiming to make the testing suite run
for both Algolia maintainers and external contributors. More on that later. ;)

### Changes

- **fix:** Prevent double-close of response's body when DEBUG logging is enabled ([2ffd96b](https://github.com/algolia/algoliasearch-client-java-2/commit/2ffd96b))
- **test:** Prevent NullPointerException in waitForKeyNotPresent ([4050c19](https://github.com/algolia/algoliasearch-client-java-2/commit/4050c19))
- **test:** Make shouldHandleSNI test work with both community/non-community PRs ([bef94ef](https://github.com/algolia/algoliasearch-client-java-2/commit/bef94ef))
- **chore(travis):** Bypass key management tests for community PRs ([7adbb1c](https://github.com/algolia/algoliasearch-client-java-2/commit/7adbb1c))
- **chore(travis):** Get Algolia credentials from API Key Dealer ([820b0df](https://github.com/algolia/algoliasearch-client-java-2/commit/820b0df))
- **chore(travis):** Rename env variables ([735048f](https://github.com/algolia/algoliasearch-client-java-2/commit/735048f))
- **chore(md):** Update contribution-related files ([ddb4607](https://github.com/algolia/algoliasearch-client-java-2/commit/ddb4607))
- **misc:** Update README ([9fef4ea](https://github.com/algolia/algoliasearch-client-java-2/commit/9fef4ea))
- **doc:** Rename ChangeLog into ChangeLog.md to enable Markdown rendering in Github ([83404cd](https://github.com/algolia/algoliasearch-client-java-2/commit/83404cd))
- **misc:** [maven-release-plugin] prepare for next development iteration ([8175ac8](https://github.com/algolia/algoliasearch-client-java-2/commit/8175ac8))

# [2.18.0](https://github.com/algolia/algoliasearch-client-java-2/compare/2.17.3...2.18.0) (2018-07-26)

### Summary

Hello everyone,

Nearly two months after our last minor version, here is a new one. It includes
two main changes, and not the smallest ones:

 - Bug fix related to the Query Rules serialization of the Consequence part
 - Implementation of a long awaited new Algolia feature: [AB Testing](https://www.algolia.com/products/ab-testing)

Thanks to AB tests, you are now able to compare two different index
configurations in term of analytics performances (number of search queries,
CTR, etc.). You can set up AB tests via the Algolia dashboard or, since this
version of our Java client, list, create, change or delete AB tests
programatically.

### Changes

- **style:** Autoformat ConsequenceQuery.java file ([656d11e](https://github.com/algolia/algoliasearch-client-java-2/commit/656d11e))
- **test:** Add integration tests for AB testing ([78e14df](https://github.com/algolia/algoliasearch-client-java-2/commit/78e14df))
- **feat:** Implement AB Testing ([71832d2](https://github.com/algolia/algoliasearch-client-java-2/commit/71832d2))
- **feat:** Handle analytics.algolia.com endpoint as part of the HTTP clients ([22b06b5](https://github.com/algolia/algoliasearch-client-java-2/commit/22b06b5))
- **refactor:** Introduce AlgoliaRequestKind to different search/write/analytics calls ([6c2ad2c](https://github.com/algolia/algoliasearch-client-java-2/commit/6c2ad2c))
- **style:** Reformat some files ([1965cb0](https://github.com/algolia/algoliasearch-client-java-2/commit/1965cb0))
- **fix:** Avoid infinite serialization recursion of `ConsequenceQuery` ([f38cb5f](https://github.com/algolia/algoliasearch-client-java-2/commit/f38cb5f))
- **misc:** [maven-release-plugin] prepare for next development iteration ([6535f98](https://github.com/algolia/algoliasearch-client-java-2/commit/6535f98))

2.17.3 (2018-06-15)
-------------------
- Fix: Expose missing `userData`/`appliedRules` fields in search query responses when a query rule is triggered

2.17.2 (2018-06-12)
-------------------
- Nothing (wrongly released)

2.17.1 (2018-06-07)
-------------------
- Nothing (wrongly released)

2.17.0 (2018-06-07)
-------------------
- **Deprecation**: Keys should not be managed at the Index level but at the Client level
- Fix: Adding a rule with an empty ID failed silently, it will now raise an exception
- Feat: Expose QueryID in SearchResults

2.16.8 (2018-04-03)
-------------------
- Add maxConnPerRoute

2.16.7 (2018-04-03)
-------------------
- Fix AroundRadius serialization (#476)

2.16.6 (2018-03-08)
-------------------
- Nothing

2.16.5 (2018-03-07)
-------------------
- Fix facet_stats type (#469)

2.16.4 (2018-03-06)
-------------------
- Nothing

2.16.3 (2018-03-05)
-------------------
- Add facet stats (#467)

2.16.2 (2018-02-28)
-------------------
- Nothing

2.16.1 (2018-02-27)
-------------------
- Add CredentialsProvider parameter (#463)

2.16.0 (2018-02-26)
-------------------
- Add a way to put a proxy for the underlying http client

2.15.7 (2018-02-20)
-------------------
- Add multipleQueries(List<IndexQuery> queries, String strategy)
- Add asyncClient.waitTask(task) (#457)
- Add deleteIndex method (without RequestOptions version)
- Fix scope in copy index (#460)

2.15.6 (2018-01-24)
-------------------
- Add waitTask with taskID (#417)
- Rename listIndices to listIndexes (#440)
- Rename types to type (#438)
- Rename `client.updateKey` => `client.updateApiKey` (#442)
- Remove `replaceExistingSynonyms` from `saveSynonym` (#441)
- Add copy/move Index (#444)
- Add waitTask method for taskID with requestOptions (#437)
- Fix visibility for compatibility with Clojure (#453)
- Rename `builder.setExtraHeader` => `builder.addExtraHeader` (#445)
- `partialUpdateObjects` with `createIfNotExists` (#447)
- Fix browse from (#448)

2.15.5 (2017-12-29)
-------------------
- Add Serializable to multiple objects

2.15.5 (2017-12-29)
-------------------
- Add Serializable to multiple objects

2.15.4 (2017-12-11)
-------------------
- Add partialUpdateObjects with createIfNotExists

2.15.3 (2017-12-07)
-------------------
- Fix deserialization with specific object mapper

2.15.2 (2017-11-20)
-------------------
- Fix numberOfPendingTasks typo

2.15.1 (2017-11-15)
-------------------
- Add scope copy

2.15.0 (2017-11-14)
-------------------
- Remove old TypoTolerance enum
- Add java9 support
- Add wait for task with requests options

2.14.0 (2017-10-19)
-------------------
- Add sortFacetValuesBy
- Fix deserialization of AlgoliaException
- Extract rules & synonyms
- Add custom settings

2.13.3 (2017-10-09)
-------------------
- Fix retry strategy when all hosts are down

2.13.2 (2017-09-27)
-------------------
- Fix TypoTolerance serialization of IndexSettings

2.13.1 (2017-09-25)
-------------------
- Add debug logger
- Fix closing connections in ApacheHttpClient
- Fix force ConnectionRequestTimeout

2.13.0 (2017-09-14)
-------------------
- Add deleteBy
- Deprecate deleteByQuery
- Add query rules settings & query parameters

2.12.0 (2017-08-22)
-------------------
- Add request options to search/read methods
- Add query rules
- Add meaningful toString methods
- Add maxConnTotal configuration

2.11.3 (2017-08-01)
-------------------
- Fix query and empty lists (attributesToHighlight)

2.11.2 (2017-07-21)
-------------------
- Fix query toParam for distinct, removeStopWords, ignorePlurals

2.11.1 (2017-07-07)
-------------------
- Fix browse

2.11.0 (2017-07-03)
-------------------
- Fix path to version.properties

2.10.0 (2017-05-30)
-------------------
- Add percentileComputation
- Add setQueryHosts & setBuildHosts on builders

2.9.0 (2017-04-12)
------------------
- Rename searchInFacetValues in searchForFacetValues
- Rename Key to ApiKey
- Add missing index settings & query parameters, see: #288 #290 #292 #293 #295 #294 #296 #297 #318 #298 #317 #316 #315 #314 #313 #312 #311 #310 #309 #308 #307 #306 #305 #304 #303 #302 #301 #300 #299 #291

2.8.0 (2017-02-06)
-----------------
- Add facetingAfterDistinct
- Add maxFacetHits

2.7.0 (2016-12-20)
-----------------
- Add responseFields
- Deprecate IndexSettings.(get|set)Slaves

2.6.1 (2016-12-09)
------------------
- Fix hosts for batches

2.6.0 (2016-12-09)
------------------
- Rename searchFacet in searchInFacetValues
- Add new retry strategy

2.5.0 (2016-11-08)
------------------
- Add searchFacet
- Enable RemoveStopWords for boolean & List<String>
- Enable Distinct for boolean and integer

2.4.1 (2016-10-04)
------------------
- Fix search on non existing index

2.4.0 (2016-10-03)
------------------
- Rename master/slaves to primary/replicas
- Deprecated attributesToIndex
- Add searchableAttributes to replace attributesToIndex
- Add restrictSources to ApiKeys
- Allow integers in the Distinct field

2.3.0 (2016-08-22)
------------------
- Better support for custom object mappers

2.2.1 (2016-08-03)
------------------
- Update user agent to new format

2.2.0 (2016-07-27)
------------------
- Fix browse query
- Fix browse with empty index

2.1.0 (2016-07-12)
------------------
- Fix Query#toParam
- Fix secure key generation
- Randomize hosts
- Add support for Google AppEngine

2.0.0 (2016-07-01)
------------------
- See migration guide: https://github.com/algolia/algoliasearch-client-java-2/wiki/Migration-guide-from-1.x-to-2.x
- Add support for Google AppEngine
- Add type safety

1.7.0 (2016-05-24)
------------------
- Add minimumAroundRadius parameter
- Reduce all timeouts to 2 seconds
- Drop support for Java 1.6

1.6.6 (2016-02-01)
------------------
- Add validUntil as a query parameter
- Add restrictIndices as a query parameter

1.6.5 (2016-01-28)
------------------
- Add snippetEllipsisText as a search parameter

1.6.4 (2015-12-29)
------------------
- Fix DNS records used by the deleteObject method

1.6.3
------------------
- Skipped version

1.6.2
------------------
- Skipped version

1.6.1 (2015-12-02)
------------------
- Improve the performance of the deleteByQuery method

1.6.0 (2015-10-29)
------------------
- New Secured API keys format

1.5.0 (2015-10-12)
------------------
- Added remove stop words query parameter
- Added support of similar queries

1.4.0 (2015-10-02)
------------------
- Upgrade to httpclient 4.5.1

1.3.17 (2015-09-30)
-------------------
- Added support of multiple bounding box for geo-search
- Added support of polygon for geo-search
- Added support of automatic radius computation for geo-search
- Added support of disableTypoToleranceOnAttributes

1.3.16 (2015-09-21)
-------------------
- Ensure the JSON answer is read before trying to tokenize to throw IOException instead of JSONException

1.3.15 (2015-09-15)
-------------------
- Expose the IndexBrowser fixing its class visibility

1.3.14 (2015-09-11)
-------------------
- The underlying HttpClient now uses the system-wide defined properties
- Fixed a typo in the browseFrom method
- Ensure the deleteByQuery calls are disabling distinct

1.3.13 (2015-08-20)
-------------------
- The search request is now doing a POST request instead of a GET to bypass the URL limit

1.3.12 (2015-08-19)
-------------------
- Add ability to customize the user-agent

1.3.11 (2015-08-17)
-------------------
- fixed the IndexBrowser iterator

1.3.10 (2015-08-04)
-------------------
- fixed the way the query parameters were overriding the default one if not set

1.3.9 (2015-07-17)
-------------------
- fixed a bug avoiding the hitsPerPage parameter to be sent to the API if equal to 20

1.3.8 (2015-07-14)
-------------------
- Added support of grouping (distinct=3 for example keep the 3 best hits for a distinct key)

1.3.7 (2015-06-05)
-------------------
- add new parameter on the Query: setMinProximity & setHighlightingTags
- new cursor-based browse implementation

1.3.6 (2015-05-09)
-------------------
- add new partialUpdateObjectNoCreate

1.3.5 (2015-04-09)
------------------
- removed the enabled hosts (new retry strategy)

1.3.4 (2015-04-09)
------------------------
- Better retry strategy using two different provider (Improve high-availability of the solution, retry is done on algolianet.com)
- Read operations are performed to APPID-dsn.algolia.net domain first to leverage Distributed Search Network (select the closest location)
- Improved timeout strategy: increasse timeout after 2 trials & have a different read timeout for search operations


1.3.3 (2015-03-03)
------------------------
- Move to Maven Central

1.3.2 (2015-02-18)
------------------------
- Ability to configure the underlying timeout

- Added new "allOptional" value for the maxValuesPerFacet option

1.3.1 (2014-12-04)
------------------------
- Add searchDisjunctiveFaceting

- Add more debug during an host unreachable error. [Xavier Grand]

1.3.0 (2014-11-30)
------------------

- Release 1.3.0. [Xavier Grand]

- [maven-release-plugin] prepare release 1.3.0. [Xavier Grand]

- Increase sleep. [Xavier Grand]

- Switch to .net. [Xavier Grand]

- Updated maven's repository url in the pom.xml. [Alexandre C]

- Release 1.2.3. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.2.3 (2014-10-31)
------------------

- Add ChangeLog. [Sylvain UTARD]

- Set ConnectionRequestTimeout as well. [Sylvain UTARD]

- Updated to last major version. [Sylvain UTARD]

- Release 1.2.3. [Xavier Grand]

- [maven-release-plugin] prepare release 1.2.3. [Xavier Grand]

- Add more information when hosts are unreachable. [Xavier Grand]

- Add setExtraHeader. [Xavier Grand]

- Release 1.2.2. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.2.2 (2014-10-11)
------------------

- [maven-release-plugin] prepare release 1.2.2. [Sylvain UTARD]

- Embed the API client version in the User-Agent. [Sylvain UTARD]

- Improved documentation. [Sylvain UTARD]

- Cosmetics. [Sylvain UTARD]

- Version 1.2.1. [Julien Lemoine]

- [maven-release-plugin] prepare for next development iteration. [Julien
  Lemoine]

1.2.1 (2014-09-14)
------------------

- [maven-release-plugin] prepare release 1.2.1. [Julien Lemoine]

- New version with ignorePlural query parameter. [Julien Lemoine]

- Add the documentation about the update of an APIKey. [Xavier Grand]

- Add update acl. [Xavier Grand]

- Updated default typoTolerance setting & updated removedWordsIfNoResult
  documentation. [Julien Lemoine]

- Reduce the sleep of waitTask from 1s to 100ms. [Xavier Grand]

- Release 1.2.0. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.2.0 (2014-08-20)
------------------

- [maven-release-plugin] prepare release 1.2.0. [Sylvain UTARD]

- GetObject: return null instead of Exception if missing. [Sylvain
  UTARD]

- Store the HTTP status code if relevant. [Sylvain UTARD]

- Add documentation about removeWordsIfNoResult. [Xavier Grand]

- Version 1.1.23 new prototype for removeWordsIfNoResult. [Julien
  Lemoine]

- [maven-release-plugin] prepare for next development iteration. [Julien
  Lemoine]

1.1.23 (2014-08-13)
-------------------

- [maven-release-plugin] prepare release 1.1.23. [Julien Lemoine]

- New prototype for removeWordsIfNoResult. [Julien Lemoine]

- [maven-release-plugin] prepare for next development iteration. [Julien
  Lemoine]

1.1.22 (2014-08-13)
-------------------

- [maven-release-plugin] prepare release 1.1.22. [Julien Lemoine]

- Added support of removeLastWordsIfNoResult &
  removeFirstWordsIfNoResult. [Julien Lemoine]

- Fixed typos. [Julien Lemoine]

- Fixed links. [Julien Lemoine]

- Added aroundLatLngViaIP documentation. [Julien Lemoine]

- Release 1.1.21. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.21 (2014-08-04)
-------------------

- [maven-release-plugin] prepare release 1.1.21. [Sylvain UTARD]

- Merge branch 'master' of https://github.com/algolia/algoliasearch-
  client-java. [Julien Lemoine]

- Added aroundLatitudeLongitudeViaIP. [Julien Lemoine]

- Added documentation of suffix/prefix index name matching in API key.
  [Julien Lemoine]

- Change the cluster. [Xavier Grand]

- Added restrictSearchableAttributes. [Julien Lemoine]

- Release 1.1.20. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.20 (2014-07-24)
-------------------

- [maven-release-plugin] prepare release 1.1.20. [Sylvain UTARD]

- Added restrictSearchableAttributes. [Julien Lemoine]

- Fix synonyms and replace synonyms. [Xavier Grand]

- Documentation: Added deleteByQuery and multipleQueries. [Xavier Grand]

- Fix analytics flag. [Xavier Grand]

- Release 1.1.19. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.19 (2014-07-17)
-------------------

- [maven-release-plugin] prepare release 1.1.19. [Sylvain UTARD]

- Added notes on attributesToIndex. [Julien Lemoine]

- Update README.md (getObjects + fix typo flot => float) [Xavier Grand]

- Add deleteByQuery and getObjects. [Xavier Grand]

- Added disableTypoToleranceOn & altCorrections index settings. [Julien
  Lemoine]

- Fixed waitTask snippet. [Sylvain UTARD]

- Add typoTolerance & allowsTyposOnNumericTokens query parameters.
  [Sylvain UTARD]

- Release 1.1.18. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.18 (2014-06-09)
-------------------

- [maven-release-plugin] prepare release 1.1.18. [Sylvain UTARD]

- Add typoTolerance + allowTyposOnNumericTokens query parameters
  handling. [Sylvain UTARD]

- Documentation: Added words ranking parameter. [Julien Lemoine]

- Added asc(attributeName) & desc(attributeName) documentation in index
  settings. [Julien Lemoine]

- Java client version 1.1.17 released. [Julien Lemoine]

- [maven-release-plugin] prepare for next development iteration. [Julien
  Lemoine]

1.1.17 (2014-05-21)
-------------------

- [maven-release-plugin] prepare release 1.1.17. [Julien Lemoine]

- AddUserKey now support an array of string for target indexes. [Julien
  Lemoine]

- Updated synonyms examples. [Xavier Grand]

- Fix typo. [Xavier Grand]

- Add a note about distinct and the empty queries. [Xavier Grand]

- 1.1.16 release. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.16 (2014-05-13)
-------------------

- [maven-release-plugin] prepare release 1.1.16. [Sylvain UTARD]

- Disable tests when releasing. [Sylvain UTARD]

- Maven is going to make me crazy. [Sylvain UTARD]

- More test fixes. [Sylvain UTARD]

- Adding user keys is an asynchronous process too but doesn't return any
  taskID -> sleep. [Sylvain UTARD]

- 1.1.15 release. [Sylvain UTARD]

1.1.15 (2014-05-02)
-------------------

- Added support of enableAnalytics, enableSynonyms &
  enableReplaceSynonymsInHighlight in Query class. [Julien Lemoine]

- Added analytics,synonyms,enableSynonymsInHighlight query parameters.
  [Julien Lemoine]

- New numericFilters documentation. [Julien Lemoine]

- 1.1.14 release. [Sylvain UTARD]

1.1.14 (2014-04-01)
-------------------

- Unused import. [Sylvain UTARD]

- Improved API errors handling. [Sylvain UTARD]

- S/setAvancedSyntax/enableAdvancedSyntax/ [Sylvain UTARD]

- Add advancedSyntax query parameter. [Sylvain UTARD]

- Add or for facetFilter. [Xavier Grand]

- Add onlyErrors parametter to getLogs. [Xavier Grand]

- Change sha256 to hmac with sha256. [Xavier Grand]

- Add test on multipleQueries. [Xavier Grand]

- Add multipleQueries. [Xavier Grand]

- 1.1.13 release. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.13 (2014-02-26)
-------------------

- [maven-release-plugin] prepare release 1.1.13. [Sylvain UTARD]

- Ability to generate secured API keys + specify list of indexes
  targeted by user keys. [Sylvain UTARD]

- Added deleteObjects. [Xavier Grand]

- Fixed regex for travis build. [Xavier Grand]

- Delete each index used by the test suite at the end of the execution.
  [Xavier Grand]

- Add maxNumberOfFacets query parameter. [Sylvain UTARD]

- Updated README. [Sylvain UTARD]

- S/setNbHitsPerPage/setHitsPerPage/ [Sylvain UTARD]

- Eclipse: ignore jacoco-maven-plugin's prepare-agent execution.
  [Sylvain UTARD]

- Added comments to ignore unreachable lines for coveralls. [Xavier
  Grand]

- Removed unused imports. [Sylvain UTARD]

- Improved test suite. [Xavier Grand]

- Set project.build.sourceEncoding to UTF-8. [Sylvain UTARD]

- Trying to fix coveralls. [Xavier Grand]

- README: add coveralls badge. [Sylvain UTARD]

- README: add Github version. [Sylvain UTARD]

- Set tagNameFormat to @{project.version} [Sylvain UTARD]

- Updated README. [Sylvain UTARD]

- Release the connection. [Sylvain UTARD]

- Trying to add coveralls. [Xavier Grand]

- Trying to add travis. [Xavier Grand]

- Improved test suite. [Xavier Grand]

- 1.1.12 release. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.12 (2014-02-01)
-------------------

- [maven-release-plugin] prepare release algoliasearch-1.1.12. [Sylvain
  UTARD]

- Added batch function. [Xavier Grand]

- Fixed error in doc generation. [Julien Lemoine]

- Fixed typos (result of doc generator) Added slaves parameter
  documentation in Index settings. [Julien Lemoine]

- Algoliasearch 1.1.11. [Julien Lemoine]

- [maven-release-plugin] prepare for next development iteration. [Julien
  Lemoine]

1.1.11 (2014-01-02)
-------------------

- [maven-release-plugin] prepare release algoliasearch-1.1.11. [Julien
  Lemoine]

- Fix maven name. [Julien Lemoine]

- Added distinct parameters Added support of Rate Limit forward Updated
  documentation. [Julien Lemoine]

- Add version 1.1.10 to repository. [Sylvain UTARD]

- Add version 1.1.10 to repository. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.10 (2013-12-26)
-------------------

- [maven-release-plugin] prepare release algoliasearch-1.1.10. [Sylvain
  UTARD]

- Fixed missing equals. [Sylvain UTARD]

- Improved readability of search & settings parameters. [Julien Lemoine]

- Fixed code comment. [Julien Lemoine]

- 1.1.9 released. [Julien Lemoine]

- [maven-release-plugin] prepare for next development iteration. [Julien
  Lemoine]

1.1.9 (2013-12-06)
------------------

- [maven-release-plugin] prepare release algoliasearch-1.1.9. [Julien
  Lemoine]

- Added missing method. [Julien Lemoine]

- Removed $ directory. [Julien Lemoine]

- Release 1.1.8. [Julien Lemoine]

- [maven-release-plugin] prepare for next development iteration. [Julien
  Lemoine]

1.1.8 (2013-12-06)
------------------

- [maven-release-plugin] prepare release algoliasearch-1.1.8. [Julien
  Lemoine]

- Added partialUpdateObjects method Added browse method & ACL. [Julien
  Lemoine]

- Merge branch 'master' of https://github.com/algolia/algoliasearch-
  client-java. [Julien Lemoine]

- Fixed typo. [Julien Lemoine]

- Release 1.1.7. [Julien Lemoine]

- [maven-release-plugin] prepare for next development iteration. [Julien
  Lemoine]

- [maven-release-plugin] prepare release algoliasearch-$ [Julien
  Lemoine]

- Fixed typo in facetFilters, numericFilters & tagFilters Query methods.
  [Julien Lemoine]

- Renaming. [Sylvain UTARD]

- Release 1.1.6. [Julien Lemoine]

- [maven-release-plugin] prepare for next development iteration. [Julien
  Lemoine]

1.1.6 (2013-11-07)
------------------

- [maven-release-plugin] prepare release algoliasearch-1.1.6. [Julien
  Lemoine]

- [maven-release-plugin] prepare release algoliasearch-1.1.6. [Julien
  Lemoine]

- [maven-release-plugin] prepare release algoliasearch-1.1.6. [Julien
  Lemoine]

- Fixed compilation issue version 1.1.6. [Julien Lemoine]

-  added maven script. [Julien Lemoine]

- Documented new features. [Julien Lemoine]

- Add empty query string tests. Cosmetics (tabs -> spaces + DRY)
  [Sylvain UTARD]

- Release 1.1.5. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.5 (2013-10-31)
------------------

- [maven-release-plugin] prepare release algoliasearch-1.1.5. [Sylvain
  UTARD]

- Set HTTP timeout to 30sec. [Sylvain UTARD]

- Release 1.1.4. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.4 (2013-10-30)
------------------

- [maven-release-plugin] prepare release algoliasearch-1.1.4. [Sylvain
  UTARD]

- Refactoring + amend d93922b. [Sylvain UTARD]

- Add unit tests. [Sylvain UTARD]

- Bump version. [Sylvain UTARD]

- Release 1.1.3. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.3 (2013-10-30)
------------------

- [maven-release-plugin] prepare release algoliasearch-1.1.3. [Sylvain
  UTARD]

- Consume response when an error occurs. [Sylvain UTARD]

- Release 1.1.2. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.2 (2013-10-21)
------------------

- [maven-release-plugin] prepare release algoliasearch-1.1.2. [Sylvain
  UTARD]

- Bump to 1.1.2. [Sylvain UTARD]

- Improve move & copy doc. [Nicolas Dessaigne]

- New method that expose geoPrecision in aroundLatLng. [Julien Lemoine]

- Remoted Android async call from documentation. [Julien Lemoine]

- Release algoliasearch 1.1.0. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.1.0 (2013-10-13)
------------------

- [maven-release-plugin] prepare release algoliasearch-1.1.0. [Sylvain
  UTARD]

- Use JSONObject instead of String. [Sylvain UTARD]

- Updated README to use JSONObjects instead of bulk strings. [Sylvain
  UTARD]

- Updated README with Maven instructions. [Sylvain UTARD]

- Release algoliasearch 1.0.0. [Sylvain UTARD]

- [maven-release-plugin] prepare for next development iteration.
  [Sylvain UTARD]

1.0.0 (2013-10-13)
------------------

- [maven-release-plugin] prepare release algoliasearch-1.0.0. [Sylvain
  UTARD]

- Mavenification. [Sylvain UTARD]

- Renamed parameters of typo tolerance configuration. [Julien Lemoine]

- QueryType=prefixLast is now the default behavior Documented
  unordered(attributeName) [Julien Lemoine]

- Added missing newline. [Julien Lemoine]

- Added helper updateObjects(JSONArray) [Julien Lemoine]

- Added a helper addObjects(JSONArray) [Julien Lemoine]

- Moved numerics close to tags in doc other minor corrections. [Nicolas
  Dessaigne]

- Fixed numerics doc. [Julien Lemoine]

- Document new features: numerics/copy/move/logs. [Julien Lemoine]

- Added support of copy/move index + logs + numerics in Java client.
  [Julien Lemoine]

- Check that objectID is not empty. [Julien Lemoine]

- Added "your account" link. [Julien Lemoine]

- Add a link to download in doc. [Julien Lemoine]

- Correction. [Nicolas Dessaigne]

- Replaced cities by contacts. [Julien Lemoine]

- Added a new constructor that automatically set hostnames. [Julien
  Lemoine]

- Updated ranking doc. [Julien Lemoine]

- Fixed error in method name. [Julien Lemoine]

- Added List indexes / delete index / ACLS documentation. Added per
  Index ACLS. [Julien Lemoine]

- Updates list of hostnames. [Julien Lemoine]

- Added support of attributesToSnippet & queryType. [Julien Lemoine]

- Removed obsolete comment. [Julien Lemoine]

- Fixed typo in comment. [Julien Lemoine]

- Remote unfinished sentence. [Julien Lemoine]

- Removed obsolete comment. [Julien Lemoine]

- Refactor to avoid package clash with offline SDK. [Julien Lemoine]

- Added missing URLEncode on deleteIndex method. [Julien Lemoine]

- Fixed indentation. [Julien Lemoine]

- Removed AlgoliaException at Initialization and removed debug message.
  [Julien Lemoine]

- Improved a little json formatting and replaced QueryParameters by
  Query. [Julien Lemoine]

- Fixed typo. [Julien Lemoine]

- Initial import of Java client. [Julien Lemoine]


