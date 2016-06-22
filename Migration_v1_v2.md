# Changelog

* Builder
* listIndexes => listIndices
* index.addObject(obj, objectId) => index.addObject(objectId, obj)
* searchDisjunctiveFaceting removed
* removed getLogs(int offset, int length) throws AlgoliaException => LOG_ALL
* removed getLogs(int offset, int length, boolean onlyErrors) throws AlgoliaException => LOG_ERROR
* client.copyIndex/client.moveIndex => index.copyTo(dst), index.moveTo(dst)
* index.clearIndex() => index.clear();
* task.waitFor(long)
* T getObject => Optional<T> getObject
* partialUpdateObject => specific class
* remove enableRateLimitForward
* userkey => key
* APIClient.IndexQuery => IndexQuery
* Index.IndexBrowser => IndexIterable<T>
* add a .stream() to IndexIterable
* query
** attributes -> attributesToRetrieve
** numerics -> numericFilters
** tags -> tagsFilters
** maxNumberOfFacets -> maxValuesPerFacet
** removed noTypoToleranceOn, similarQuery, referers
** aroundLatLong -> aroundLatLng
** aroundLatLongViaIP -> aroundLatLngViaIP
** add setAroundRadiusAll()
** add setRemoveStopWords(List<String> isoCode)


# TODO

* new params

* custom query param
* custom index settings

* Update apache http client
* gae tests
* async

