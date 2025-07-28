# ChangeLog

## [3.16.11](https://github.com/algolia/algoliasearch-client-java-2/compare/3.16.10...3.16.11) (2025-07-28)

### Fix

**rules**: allow one-sided timeRange (#795)

## [3.16.10](https://github.com/algolia/algoliasearch-client-java-2/compare/3.16.9...3.16.10) (2024-10-29)

### Fix

**rules**: add `hide` to `FacetValuesOrder` (#794)


## [3.16.9](https://github.com/algolia/algoliasearch-client-java-2/compare/3.16.8...3.16.9) (2023-02-08)

### Fix

**apache**: Treat all I/O exceptions as network errors (#791)


## [3.16.8](https://github.com/algolia/algoliasearch-client-java-2/compare/3.16.7...3.16.8) (2023-12-20)

### Fix

- **rules**: add redirect to RenderingContent (#793)


## [3.16.7](https://github.com/algolia/algoliasearch-client-java-2/compare/3.16.6...3.16.7) (2023-06-02)

### Fix

- **core**: http request timeout overflow (#785)
- **search**: add abTestID to search result (#787)

### Misc

- update jackson to 2.14.3 (#786)


## [3.16.6](https://github.com/algolia/algoliasearch-client-java-2/compare/3.16.5...3.16.6) (2022-08-03)

### Fix

- HttpRequester: use body supplier (#774)
- URL unsafe characters (#779)

### Misc

- Update jackson to `2.13.x` (#777)

## [3.16.5](https://github.com/algolia/algoliasearch-client-java-2/compare/3.16.4...3.16.5) (2022-03-18)

### Feat

- java http client requester with `HttpClient.Builder` (#776) ([ca20347b](https://github.com/algolia/algoliasearch-client-java-2/commit/ca20347b))



## [3.16.4](https://github.com/algolia/algoliasearch-client-java-2/compare/3.16.3...3.16.4) (2022-02-07)

### Feat

- add default utility classes for Java.net client (#772) ([48aeae3](https://github.com/algolia/algoliasearch-client-java-2/commit/48aeae3))

### Fix

- optional filters deserialization (#771) ([0e58180](https://github.com/algolia/algoliasearch-client-java-2/commit/0e58180))

### Misc

- fail on warning (#770) ([20793bf](https://github.com/algolia/algoliasearch-client-java-2/commit/20793bf))



## [3.16.3](https://github.com/algolia/algoliasearch-client-java-2/compare/3.16.2...3.16.3) (2022-01-27)

### Fix

- **rules**: TimeRange: deserialize time as long (#769) ([c541a5e](https://github.com/algolia/algoliasearch-client-java-2/commit/c541a5e6fb51a670fbdbe60731ac387801e866d0))

- **indexing**: copySettings func not calling expected base func (#768) ([f7432af](https://github.com/algolia/algoliasearch-client-java-2/commit/f7432af48e04fe2bc3386dd99fba148111fca15c))

## [3.16.2](https://github.com/algolia/algoliasearch-client-java-2/compare/3.16.1...3.16.2) (2022-01-07)

### Fix

- **search**: serialize `ConsequenceParams` empty query (#766) ([01b0bcd](https://github.com/algolia/algoliasearch-client-java-2/commit/01b0bcd))

### Feat

- **search**: add 'enableReRanking' query parameter (#764) ([f41e062](https://github.com/algolia/algoliasearch-client-java-2/commit/f41e062))



## [3.16.1](https://github.com/algolia/algoliasearch-client-java-2/compare/3.16.0...3.16.1) (2021-10-29)

### Fix

- **recommendation**: `_score` field as member of `RecommendHit` (#761) ([645678f](https://github.com/algolia/algoliasearch-client-java-2/commit/645678f))

### Feat

- **search**: custom requests (#757) ([6e7d842](https://github.com/algolia/algoliasearch-client-java-2/commit/6e7d842))



## [3.16.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.15.1...3.16.0) (2021-10-14)

### Fix

- **rules**: deserialize optional filters (#758) ([89251ff](https://github.com/algolia/algoliasearch-client-java-2/commit/89251ff))

### Feat

- recommend client implementation (#752) ([e362d90](https://github.com/algolia/algoliasearch-client-java-2/commit/e362d90))



## [3.15.1](https://github.com/algolia/algoliasearch-client-java-2/compare/3.15.0...3.15.1) (2021-08-20)

### Fix

- **rules**: automatic facet filter deserialization (#753) ([c3647a9](https://github.com/algolia/algoliasearch-client-java-2/commit/c3647a9))



## [3.15.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.14.3...3.15.0) (2021-07-23)

### Feat

- **dynamic facets**: facet ordering support (#747) ([53dc256](https://github.com/algolia/algoliasearch-client-java-2/commit/53dc256))

### Change

- rename `RecommendationClient` to `PersonalizationClient`  (#750) ([ddbec57](https://github.com/algolia/algoliasearch-client-java-2/commit/ddbec57))



## [3.14.3](https://github.com/algolia/algoliasearch-client-java-2/compare/3.14.2...3.14.3) (2021-06-29)

### Fix

- **query**: empty nested list (#749) ([37e0f44](https://github.com/algolia/algoliasearch-client-java-2/commit/37e0f44))



## [3.14.2](https://github.com/algolia/algoliasearch-client-java-2/compare/3.14.1...3.14.2) (2021-06-02)

### Fix

- **rules**: `TimeRange` serialization (#745) ([6ee40f3](https://github.com/algolia/algoliasearch-client-java-2/commit/6ee40f3))



## [3.14.1](https://github.com/algolia/algoliasearch-client-java-2/compare/3.14.0...3.14.1) (2021-05-03)

### Fix

- **search**: search parameters setters return type (#744) ([b2b0173](https://github.com/algolia/algoliasearch-client-java-2/commit/b2b0173))



## [3.14.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.13.0...3.14.0) (2021-04-29)

### Feat

- **dictionaries**: dictionaries API implementation (#735) ([96897e5](https://github.com/algolia/algoliasearch-client-java-2/commit/96897e5))
- **rules**: add filters param to rules condition (#740) ([760d2f1](https://github.com/algolia/algoliasearch-client-java-2/commit/760d2f1))



## [3.13.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.12.1...3.13.0) (2021-03-31)

### Feat

- **settings**: add attributesToTransliterate and decompoundQuery parameters (#741) ([b730e1a](https://github.com/algolia/algoliasearch-client-java-2/commit/b730e1a))
- **indices**: add missing fields to IndicesResponse (#742) ([ec1f4ac](https://github.com/algolia/algoliasearch-client-java-2/commit/ec1f4ac))
- **virtual indices**: virtual indices related parameters  (#739) ([0bb458d](https://github.com/algolia/algoliasearch-client-java-2/commit/0bb458d))

### Misc

- **search**: `replaceExistingSynonyms` to `clearExistingSynonyms` ([6af339d](https://github.com/algolia/algoliasearch-client-java-2/commit/6af339d))

### Fix

- **osgi**: add missing embedded classes to the exported packages (#738) ([e02b2c2](https://github.com/algolia/algoliasearch-client-java-2/commit/e02b2c2))
- **transport**: apply retry strategy when SSLException occurs using apache client ([29b223e](https://github.com/algolia/algoliasearch-client-java-2/commit/29b223e))



## [3.12.1](https://github.com/algolia/algoliasearch-client-java-2/compare/3.12.0...3.12.1) (2020-11-03)

### Fix

- **ABTest**: add missing fields in Variant (#723)



## [3.12.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.11.0...3.12.0) (2020-07-28)

### Feat

- **indexing**: add 'IncrementFrom' and 'IncrementSet' operations ([da67171](https://github.com/algolia/algoliasearch-client-java-2/commit/da67171))
- **rules**: apply code review sugguestions ([8733bd7](https://github.com/algolia/algoliasearch-client-java-2/commit/8733bd7))
- **rules**: add custom serializer/deserializer ([1147494](https://github.com/algolia/algoliasearch-client-java-2/commit/1147494))
- **rules**: expose rule.consequence.promote.objectIDs string array ([1a7b296](https://github.com/algolia/algoliasearch-client-java-2/commit/1a7b296))

### Fix

- **search**: add missing 'exhaustiveNbHits' ([d5a860f](https://github.com/algolia/algoliasearch-client-java-2/commit/d5a860f))



## [3.11.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.10.0...3.11.0) (2020-07-17)

### Feat

- **rule**: implement multi-condition rules ([df05893](https://github.com/algolia/algoliasearch-client-java-2/commit/df05893))

### Fix

- **build**: make the algoliasearch-apache-uber JAR compliant with OSGi ([6ccd2dd](https://github.com/algolia/algoliasearch-client-java-2/commit/6ccd2dd))
- **explain**: add support of `type` property as a List in `Alternative` ([249cac5](https://github.com/algolia/algoliasearch-client-java-2/commit/249cac5))



## [3.10.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.9.0...3.10.0) (2020-06-12)

### Feat

- **settings**: accept enablePersonalization boolean as a valid setting parameter ([c4b4649](https://github.com/algolia/algoliasearch-client-java-2/commit/c4b4649))
- **logs**: specify properties serialization names ([61a8abc](https://github.com/algolia/algoliasearch-client-java-2/commit/61a8abc))
- **logs**: add inner_queries response field ([4dca712](https://github.com/algolia/algoliasearch-client-java-2/commit/4dca712))
- **build**: make Apache Uber JAR OSGi-compliant ([ee7fab7](https://github.com/algolia/algoliasearch-client-java-2/commit/ee7fab7))
- **search**: expose built-in operations for partial updates with PartialUpdateOperation<T> ([949c83f](https://github.com/algolia/algoliasearch-client-java-2/commit/949c83f))
- add naturalLanguages parameter ([77a039b](https://github.com/algolia/algoliasearch-client-java-2/commit/77a039b))

### Fix

- **search**: add support of quoted booleans in IgnorePlurals ([560d2f2](https://github.com/algolia/algoliasearch-client-java-2/commit/560d2f2))

### Misc

- Merge pull request #700 from algolia/feat/settings ([c3d9cc4](https://github.com/algolia/algoliasearch-client-java-2/commit/c3d9cc4))
- chore(compiler) use `release` argument ([cdbc57b](https://github.com/algolia/algoliasearch-client-java-2/commit/cdbc57b))



## [3.9.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.8.1...3.9.0) (2020-04-21)

### Feat

- **apache**: improve performances by changing maxConnPerRoute from 2 to 100 ([7193a0d](https://github.com/algolia/algoliasearch-client-java-2/commit/7193a0d))
- expose new constructor for the ApacheHttpRequester ([882b4f7](https://github.com/algolia/algoliasearch-client-java-2/commit/882b4f7))
- deprecate ConfigBase Builder.setUseSystemProxy() method ([e49291e](https://github.com/algolia/algoliasearch-client-java-2/commit/e49291e))
- make the ApacheHttpRequester public for easier reconfiguration (#689) ([ae3886b](https://github.com/algolia/algoliasearch-client-java-2/commit/ae3886b))
- **analytics**: introduce the region parameter to instantiate the AnalyticsClient ([97a43f9](https://github.com/algolia/algoliasearch-client-java-2/commit/97a43f9))



## [3.8.1](https://github.com/algolia/algoliasearch-client-java-2/compare/3.8.0...3.8.1) (2020-03-26)

### Misc

- Update readme with new Java net requester ([a60b9b0](https://github.com/algolia/algoliasearch-client-java-2/commit/a60b9b0))

### Refactor

- Memory optimization of ZoneRules usage (#684) ([4d6dae6](https://github.com/algolia/algoliasearch-client-java-2/commit/4d6dae6))



## [3.8.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.7.0...3.8.0) (2020-03-25)

### Feat

- JDK11 java-net requester module ([266fa67](https://github.com/algolia/algoliasearch-client-java-2/commit/266fa67))

### Misc

- update readme [skip ci] ([4301f3b](https://github.com/algolia/algoliasearch-client-java-2/commit/4301f3b))

### Fix

- pom.xml version ([05be38f](https://github.com/algolia/algoliasearch-client-java-2/commit/05be38f))
- run-travis.sh ([7357b27](https://github.com/algolia/algoliasearch-client-java-2/commit/7357b27))
- **serializer**: correctly serialize arrays to coma-separated list ([4a8e356](https://github.com/algolia/algoliasearch-client-java-2/commit/4a8e356))
- **api-key**: use correct string type for restrictSources API key parameter ([66ac9fb](https://github.com/algolia/algoliasearch-client-java-2/commit/66ac9fb))
- **synonym**: correctly serialize SynonymQuery's type attribute ([29d6b87](https://github.com/algolia/algoliasearch-client-java-2/commit/29d6b87))
- **account**: use correct AlgoliaRuntimeException message when copying an index to an already existing destination ([b892ef3](https://github.com/algolia/algoliasearch-client-java-2/commit/b892ef3))

### Refactor

- run-travis.sh ([49f5295](https://github.com/algolia/algoliasearch-client-java-2/commit/49f5295))
- apache test suite ([bface34](https://github.com/algolia/algoliasearch-client-java-2/commit/bface34))
- clean some imports ([f70c9cd](https://github.com/algolia/algoliasearch-client-java-2/commit/f70c9cd))



## [3.7.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.6.2...3.7.0) (2020-02-06)

### Fix

- AroundPrecision serialization in multipleQueries ([42d46ba](https://github.com/algolia/algoliasearch-client-java-2/commit/42d46ba))

    using aroundPrecision in multipleQueries would cause
    java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to java.lang.CharSequence
- add missing log type in getLogs ([2162d81](https://github.com/algolia/algoliasearch-client-java-2/commit/2162d81))

    fix #667

### Feat

- adds RecommendationClient ([7ca071a](https://github.com/algolia/algoliasearch-client-java-2/commit/7ca071a))

    The personalization strategy endpoint is migrating from the Search API
    to the Recommendation API.

    To use the Recommendation API, one must now use RecommendationClient
    instead of SearchClient
- **mcm**: has pending mappings (#660) ([edfc92e](https://github.com/algolia/algoliasearch-client-java-2/commit/edfc92e))

    Get cluster pending (migrating, creating, deleting) mapping state.
    Query cluster pending mapping status, and optionally get cluster mappings.



## [3.6.2](https://github.com/algolia/algoliasearch-client-java-2/compare/3.6.1...3.6.2) (2019-11-26)

### Misc

- serialization/deserialization of ConsequenceQuery (#664) ([1a9e1b4](https://github.com/algolia/algoliasearch-client-java-2/commit/1a9e1b4))

    Fixed: Custom serializer to handle polymorphism of "query" attribute
    in ConsequenceQuery

    Example:

    ```json
    // query string
    "query": "some query string"

    // remove attribute (deprecated)
    "query": {"remove": ["query1", "query2"]}

    // edits attribute
    "query": {
       "edits": [
       { "type": "remove", "delete": "old" },
       { "type": "replace", "delete": "new", "insert": "newer" }
       ]
    }}
    ```



## [3.6.1](https://github.com/algolia/algoliasearch-client-java-2/compare/3.6.0...3.6.1) (2019-11-12)

### Misc

- generateSecuredAPIKey ([c67080a](https://github.com/algolia/algoliasearch-client-java-2/commit/c67080a))

    The securedAPIKey was not written correctly when used with a query
    parameter. `buildRestrictionQueryString` was writing "query" as
    a nested object but it should be at the same level as "restriction".

## [3.6.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.5.0...3.6.0) (2019-11-06)

### Feat

- feat: adds customNormalization in IndexSettings ([059b388](https://github.com/algolia/algoliasearch-client-java-2/tree/059b388cf98d81a2b86285a0087e1474bac5ebbe))
- update: jackson-databind to 2.9.10.1 ([91814c9](https://github.com/algolia/algoliasearch-client-java-2/tree/91814c93dc4cd74492125b9e0d3ffa79d8601040))

## [3.5.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.4.0...3.5.0) (2019-10-31)

### Feat

- feat: decompounding at query time - explain field & parameter ([88046f1](https://github.com/algolia/algoliasearch-client-java-2/commit/88046f1bd666bd47cb5b843cd88d37adf014b1e4))
- feat: adds Explains.params response object ([008ac56](https://github.com/algolia/algoliasearch-client-java-2/commit/a61916b8302c674bae6bda3cd98720fb4ed31719))
- feat: adds missing enabled parameter in RuleQuery ([ee12fee](https://github.com/algolia/algoliasearch-client-java-2/commit/ee12feea0e15f3979b5ce16734902567d6f905ed))

### Fix

- fix: cursor in browseIndex ([e55ab41](https://github.com/algolia/algoliasearch-client-java-2/commit/e55ab416f5d1884e872f0ee5cc1a1e0614cfda92))

## [3.4.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.4.0...3.3.0) (2019-10-24)

### Feat

- feat: adds filterPromotes in QueryRules([b90b21a](https://github.com/algolia/algoliasearch-client-java-2/commit/b90b21a4043cce856edf1bf89ec637d363d94609))

- feat: add enableABTest ([93ced62](https://github.com/algolia/algoliasearch-client-java-2/commit/93ced6277ea2b57ca4821f812ba96ef4f8423175))

## [3.3.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.3.0...3.2.1) (2019-10-21)

### Feat

- feat(MCM): adds assignUserIDS()

- feat: adds index.findObject()

- feat: adds SearchResult.getObjectIDPosition()

### Fix

- fix: deserialization of legacy filters

    ```
    one string legacy filters "color:green,color:yellow" are converted
    to "ORED" nested filters [["color:green","color:yellow"]]

    array legacy filters ["color:green","color:yellow"] are converted
    to "ORED" nested filters [["color:green","color:yellow"]]
    ```
- fix: updated Jackson version to 2.9.10

## [3.2.1](https://github.com/algolia/algoliasearch-client-java-2/compare/3.2.0...3.2.1) (2019-08-19)

### Feat

- useSystemProxy in the config builder ([c87e6f2](https://github.com/algolia/algoliasearch-client-java-2/commit/c87e6f2))

    Adds the possibility for the underlying HTTPClient to use JVM settings
    for proxy such as:

    ```
    System.setProperty("http.proxyHost", getHTTPHost());
    System.setProperty("http.proxyPort", getHTTPPort());
    System.setProperty("https.proxyHost", getHTTPHost());
    System.setProperty("https.proxyPort", getHTTPPort());
    System.setProperty("https.proxyUser", getAuthUser());
    System.setProperty("https.proxyPassword", getAuthPassword());
    ```

## [3.2.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.1.0...3.2.0) (2019-08-14)

### Feat

- GetSecuredApiKeyRemainingValidity ([6e3cf85](https://github.com/algolia/algoliasearch-client-java-2/commit/6e3cf85))

    New SearchClient method to get the remaining validity (seconds)
    of a securedAPIKey
- alternatives in QueryRules conditions ([cb4fb3e](https://github.com/algolia/algoliasearch-client-java-2/commit/cb4fb3e))
- indexLanguages settings properties ([8109045](https://github.com/algolia/algoliasearch-client-java-2/commit/8109045))

### Fix

- wrong size of integers in IndicesResponse ([b73bfe7](https://github.com/algolia/algoliasearch-client-java-2/commit/b73bfe7))

    Fix the size of integers in IndicesResponse. It must be int64
    instead of int32.
- improves ActionEnum documentation ([383c7a0](https://github.com/algolia/algoliasearch-client-java-2/commit/383c7a0))



## [3.1.0](https://github.com/algolia/algoliasearch-client-java-2/compare/3.0.0...3.1.0) (2019-08-07)

### Bug fix

- Missing catch clauses in the httpRequest ([c587d9f](https://github.com/algolia/algoliasearch-client-java-2/commit/c587d9f))

    The requester was not catching correctly all exceptions, making the
    retry strategy to fail instead of retrying for network related issues.

- Query serializer with nested Arrays ([b2f87f0](https://github.com/algolia/algoliasearch-client-java-2/commit/b2f87f0))

### Addition

- GZip compression feature ([e13ad77](https://github.com/algolia/algoliasearch-client-java-2/commit/e13ad77))

    Adds the possibility to compress POST/PUT requests for SearchClient.
    For the moment only GZIP compression is available.

- `indexExists` method ([b79300d](https://github.com/algolia/algoliasearch-client-java-2/commit/b79300d))

    Exists method: Return whether an index exists or not.

-  `userData` in IndexSettings ([1a4c815](https://github.com/algolia/algoliasearch-client-java-2/commit/1a4c815))
- `attributeCriteriaComputedByMinProximity` ([9ebfb28](https://github.com/algolia/algoliasearch-client-java-2/commit/9ebfb28))
- `customSearchParameters` ([08b5a85](https://github.com/algolia/algoliasearch-client-java-2/commit/08b5a85))
- `primary` in IndexSettings ([147705a](https://github.com/algolia/algoliasearch-client-java-2/commit/147705a))
- `similarQuery` in SearchParameters ([6ff1210](https://github.com/algolia/algoliasearch-client-java-2/commit/6ff1210))


### Updates

- jackson-databind to 2.9.9.2 ([42bf61a](https://github.com/algolia/algoliasearch-client-java-2/commit/42bf61a))

# [3.0.0](https://github.com/algolia/algoliasearch-client-java-2/compare/2.22.0...3.0.0) (2019-06-10)

New major version of the Java client! For more information check out:
- [Our upgrade guide](https://www.algolia.com/doc/api-client/getting-started/upgrade-guides/java/)
- [Our discourse post](https://discourse.algolia.com/t/java-api-client-v3-is-released/7898)



## [2.23.0](https://github.com/algolia/algoliasearch-client-java-2/compare/2.22.0...2.23.0) (2019-07-10)

### Added

- similarQuery ([3c54335](https://github.com/algolia/algoliasearch-client-java-2/commit/3c54335))
- sumOrFiltersScores ([b26c259](https://github.com/algolia/algoliasearch-client-java-2/commit/b26c259))
- optional Filters ([b822774](https://github.com/algolia/algoliasearch-client-java-2/commit/b822774))
- Restore API Key ([4e63598](https://github.com/algolia/algoliasearch-client-java-2/commit/4e63598))
- AATesting ([a7192a5](https://github.com/algolia/algoliasearch-client-java-2/commit/a7192a5))

### Fixed

- Legacy type parameters ([e64c69d](https://github.com/algolia/algoliasearch-client-java-2/commit/e64c69d))
- **synonym**: handle deserialization of lowercase variant of synonym types ([37d16de](https://github.com/algolia/algoliasearch-client-java-2/commit/37d16de))
- Use correct type for serializatoin/deserialization of ignorePlurals ([b283600](https://github.com/algolia/algoliasearch-client-java-2/commit/b283600))

### Updated

- jackson 2.9.8 > 2.9.9 ([cadb6eb](https://github.com/algolia/algoliasearch-client-java-2/commit/cadb6eb))

### Tests

- Updated with new parameters ([d661784](https://github.com/algolia/algoliasearch-client-java-2/commit/d661784))
- **jackson**: add test to ensure both oneWaySynonym/onewaysynonym types are deserialized into OneWaySynonym ([4c15d46](https://github.com/algolia/algoliasearch-client-java-2/commit/4c15d46))

### Misc

- [maven-release-plugin] prepare release 2.23.0 ([bed8530](https://github.com/algolia/algoliasearch-client-java-2/commit/bed8530))
- Update README.md ([cdfb59b](https://github.com/algolia/algoliasearch-client-java-2/commit/cdfb59b))
- Update README [skip ci] ([8374457](https://github.com/algolia/algoliasearch-client-java-2/commit/8374457))
- [maven-release-plugin] prepare for next development iteration ([94b4a47](https://github.com/algolia/algoliasearch-client-java-2/commit/94b4a47))




# [2.22.0](https://github.com/algolia/algoliasearch-client-java-2/compare/2.21.1...2.22.0) (2019-01-15)

### Change

- **Added:** advancedSyntaxFeatures ([8075a67](https://github.com/algolia/algoliasearch-client-java-2/c`ommit/8075a6720599736892c14e15cd25b5db6f865310))
- **Fixed:** geoSearch `insideBoundingBox` and `insidePolygon` type from `List<String>` to `List<List<Float>>` ([b4d84a5](https://github.com/algolia/algoliasearch-client-java-2/commit/b4d84a55c3ae39faf0cd2e468da0a4f2dc4eb1d5))
- **Updated:** Jackson from 2.9.5 to 2.9.8 ([571f273](https://github.com/algolia/algoliasearch-client-java-2/commit/571f273d122ed85862ab04501aabc28b6e6813db))

# [2.21.1](https://github.com/algolia/algoliasearch-client`-java-2/compare/2.21.0...2.21.1) (2018-12-18)

### Change

- **Added:** enablePersonalization on searchQuery ([12f63d0](https://github.com/algolia/algoliasearch-client-java-2/commit/12f63d0313aa42b286cdcae5f05555b38e27c4ea))

# [2.21.0](https://github.com/algolia/algoliasearch-client-java-2/compare/2.20.0...2.21.0) (2018-12-17)

### Summary
``
Hello everyone,

Big release today. We have added a lot of helpers methods : `replaceAllRules`, `replaceAllSynonyms`, `replaceAllObjects`. We also added a new class `AccountClient` that allows you to perfom cross appplication operations. For example, with this new class you can copy an index from an application to another one. We added the support of two news API features **Personalization** and **Insights**.

Have a nice day.

### Changes

- **Added:** Insights/Personalization feature ([a9455e7](https://github.com/algolia/algoliasearch-client-java-2/commit/a9455e7dfc3c3ef37ab5f4ac659d2705d60d4644))
- **Fixed:** multipleQueries bug with FacetFilters ([ce98985](https://github.com/algolia/algoliasearch-client-java-2/commit/ce989856983d650955f1523779d9e7008cde4ffc))
- **Added:** Helpers methods ([9c0a1ae](https://github.com/algolia/algoliasearch-client-java-2/commit/9c0a1ae3d30463efb49540ad46832d61fc548d8e))

# [2.20.0](https://github.com/algolia/algoliasearch-client-java-2/compare/2.19.0...2.20.0) (2018-10-18)

### Summary

Hello everyone,

Big release today. A lot of new features and bug fixes coming! As you may see,
we've added new methods to manage assignments of userIDs to clusters for users
of our **Multi Cluster Management feature**. We've also implemented new
features regarding **Query Rules** and fixed a few JSON deserialization issues.

Also note that `facetFilters` couldn't previously been used with list of list
(only simple list were accepted). This prevented users to OR filters.

Overall, this release should bring more feature and stabilize existing ones.
Special thanks to our users who reported several issues and nicely waited for
us to fix and release everything at the same time.

Have a nice day.

### Changes

- **misc:** [maven-release-plugin] prepare release 2.20.0 ([5e58d23](https://github.com/algolia/algoliasearch-client-java-2/commit/5e58d23))
- **Added:** Missing searchUserIDs method related to Multi Cluster Management (MCM) ([8da4bd2](https://github.com/algolia/algoliasearch-client-java-2/commit/8da4bd2))
- **misc:** Updated: Jackson package ([2972bf6](https://github.com/algolia/algoliasearch-client-java-2/commit/2972bf6))
- **misc:** Merge pull request #511 from algolia/fix-deleteobject-objectid ([b4cd0c9](https://github.com/algolia/algoliasearch-client-java-2/commit/b4cd0c9))
- **Fixed:** Replaced algolia exception by IllegalArgumentException ([a684746](https://github.com/algolia/algoliasearch-client-java-2/commit/a684746))
- **Added:** objectID parameter validity check before calling deleteObject method ([5a8ae8d](https://github.com/algolia/algoliasearch-client-java-2/commit/5a8ae8d))
- **Changed:** Fix flaky tests for AB Testing ([f616cf3](https://github.com/algolia/algoliasearch-client-java-2/commit/f616cf3))
- **Changed:** Use ALGOLIA_ prefixed environment variables for consistency ([f46d200](https://github.com/algolia/algoliasearch-client-java-2/commit/f46d200))
- **Changed:** Remove the use of a static RequestOptions.empty which was error-prone with a mutable instance ([a20e216](https://github.com/algolia/algoliasearch-client-java-2/commit/a20e216))
- **Added:** Support for Multi Cluster Management (MCM) ([acbcf89](https://github.com/algolia/algoliasearch-client-java-2/commit/acbcf89))
- **misc:** Update README ([d7d78b5](https://github.com/algolia/algoliasearch-client-java-2/commit/d7d78b5))
- **changed:** default hitsPerPage is 1000 for RulesIteratable ([d203841](https://github.com/algolia/algoliasearch-client-java-2/commit/d203841))
- **Added:** Query Rules V2 ([d6b0e83](https://github.com/algolia/algoliasearch-client-java-2/commit/d6b0e83))
- **fixed:** Change facetFilters type from List<String> to a new FacetFilters type to handle List<List<String>> case ([8013c24](https://github.com/algolia/algoliasearch-client-java-2/commit/8013c24))
- **added:** Add .factorypath to the .gitignore ([233a823](https://github.com/algolia/algoliasearch-client-java-2/commit/233a823))
- **misc:** [maven-release-plugin] prepare for next development iteration ([175805b](https://github.com/algolia/algoliasearch-client-java-2/commit/175805b))

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


