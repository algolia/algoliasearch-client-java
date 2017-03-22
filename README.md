# Algolia Search API Client for Java

[Algolia Search](https://www.algolia.com) is a hosted full-text, numerical, and faceted search engine capable of delivering realtime results from the first keystroke.
The **Algolia Search API Client for Java** lets you easily use the [Algolia Search REST API](https://www.algolia.com/doc/rest-api/search) from your Java code.







## API Documentation

You can find the full reference on [Algolia's website](https://www.algolia.com/doc/api-client/java/).


## Table of Contents


1. **[Install](#install)**


1. **[Quick Start](#quick-start)**

    * [Initialize the client](#initialize-the-client)
    * [Push data](#push-data)
    * [Search](#search)
    * [Configure](#configure)
    * [Frontend search](#frontend-search)

1. **[Philosophy](#philosophy)**

    * [Builder](#builder)
    * [JSON &amp; <a href="http://wiki.fasterxml.com/JacksonRelease20">Jackson2</a>](#json--a-href=http:wikifasterxmlcomjacksonrelease20jackson2a)
    * [Async &amp; Future](#async--future)

1. **[Getting Help](#getting-help)**





# Getting Started



## Install

If you're using Maven, add the following dependency to your `pom.xml` file:

```xml
<dependency>
  <groupId>com.algolia</groupId>
  <artifactId>algoliasearch</artifactId>
  <version>[2,]</version>
</dependency>
```

For the Async version use this:

```xml
<dependency>
  <groupId>com.algolia</groupId>
  <artifactId>algoliasearch-async</artifactId>
  <version>[2,]</version>
</dependency>
```

On Google AppEngine use this:

```xml
<dependency>
  <groupId>com.algolia</groupId>
  <artifactId>algoliasearch-appengine</artifactId>
  <version>[2,]</version>
</dependency>
```

## Quick Start

In 30 seconds, this quick start tutorial will show you how to index and search objects.

### Initialize the client

You first need to initialize the client. For that you need your **Application ID** and **API Key**.
You can find both of them on [your Algolia account](https://www.algolia.com/api-keys).

```java
APIClient client = new ApacheAPIClientBuilder("YourApplicationID", "YourAPIKey").build();
```

For the Async version

```java
AsyncAPIClient client = new AsyncHttpAPIClientBuilder("YourApplicationID", "YourAPIKey").build();
```

For Google AppEngine

```java
APIClient client = new AppEngineAPIClientBuilder("YourApplicationID", "YourAPIKey").build();
```

### Push data

Without any prior configuration, you can start indexing contacts in the ```contacts``` index using the following code:

```java
class Contact {

	private String firstname;
	private String lastname;
	private int followers;
	private String company;

	//Getters/Setters ommitted
}

Index<Contact> index = client.initIndex("contacts", Contact.class);
index.addObject(new Contact()
      .setFirstname("Jimmie")
      .setLastname("Barninger")
      .setFollowers(93)
      .setCompany("California Paint"));
index.addObject(new JSONObject()
      .setFirstname("Warren")
      .setLastname("Speach")
      .setFollowers(42)
      .setCompany("Norwalk Crmc"));
```

If you prefer the async version:

```java
AsyncIndex<Contact> index = client.initIndex("contacts", Contact.class);
index.addObject(new Contact()
      .setFirstname("Jimmie")
      .setLastname("Barninger")
      .setFollowers(93)
      .setCompany("California Paint"));
index.addObject(new JSONObject()
      .setFirstname("Warren")
      .setLastname("Speach")
      .setFollowers(42)
      .setCompany("Norwalk Crmc"));
```

### Search

You can now search for contacts using firstname, lastname, company, etc. (even with typos):

```java
//Sync version

// search by firstname
System.out.println(index.search(new Query("jimmie")));
// search a firstname with typo
System.out.println(index.search(new Query("jimie")));
// search for a company
System.out.println(index.search(new Query("california paint")));
// search for a firstname & company
System.out.println(index.search(new Query("jimmie paint")));
```

```java
//Async version

// search by firstname
System.out.println(index.search(new Query("jimmie")).get());
// search a firstname with typo
System.out.println(index.search(new Query("jimie")).get());
// search for a company
System.out.println(index.search(new Query("california paint")).get());
// search for a firstname & company
System.out.println(index.search(new Query("jimmie paint")).get());
```

### Configure

Settings can be customized to tune the search behavior. For example, you can add a custom sort by number of followers to the already great built-in relevance:

```java
//Sync & Async version

index.setSettings(new IndexSettings().setCustomRanking(Collections.singletonList("desc(followers)")));
```

You can also configure the list of attributes you want to index by order of importance (first = most important):

**Note:** Since the engine is designed to suggest results as you type, you'll generally search by prefix.
In this case the order of attributes is very important to decide which hit is the best:

```java
//Sync & Async version

index.setSettings(new IndexSettings().setSearchableAttributes(
	Arrays.asList("lastname", "firstname", "company")
);
```

### Frontend search

**Note:** If you are building a web application, you may be more interested in using our [JavaScript client](https://github.com/algolia/algoliasearch-client-javascript) to perform queries.

It brings two benefits:
  * Your users get a better response time by not going through your servers
  * It will offload unnecessary tasks from your servers

```html
<script src="https://cdn.jsdelivr.net/algoliasearch/3/algoliasearch.min.js"></script>
<script>
var client = algoliasearch('ApplicationID', 'apiKey');
var index = client.initIndex('indexName');

// perform query "jim"
index.search('jim', searchCallback);

// the last optional argument can be used to add search parameters
index.search(
  'jim', {
    hitsPerPage: 5,
    facets: '*',
    maxValuesPerFacet: 10
  },
  searchCallback
);

function searchCallback(err, content) {
  if (err) {
    console.error(err);
    return;
  }

  console.log(content);
}
</script>
```

## Philosophy

### Builder

The v2 of the api client, uses a builder to create the APIClient object. If you are on a regular JVM (not android, not Google App Engine), use the `ApacheAPIClientBuilder`, if you are on Google App Engine use the `AppEngineAPIClientBuilder`. If you fancy `Future`, use the `AsyncHttpAPIClientBuilder`.
As for Android, please use the [Android API Client](https://github.com/algolia/algoliasearch-client-android)

### JSON & [Jackson2](http://wiki.fasterxml.com/JacksonRelease20)

All the serialization/deserialization is done with [Jackson2](http://wiki.fasterxml.com/JacksonRelease20). You can add your custom ObjectMapper with the method `setObjectMapper` of the builder.
Changing it might result in unexpected result. You can find the one used in the interface `com.algolia.search.Defaults.DEFAULT_OBJECT_MAPPER`.

### Async & Future

All methods of the `AsyncAPIClient` are exactly the same as the `APIClient` but returns `CompletableFuture<?>`. All other classes are prefixes with `Async`. You can also pass a optional `ExecutorService` to the `build` of the `AsyncHttpAPIClientBuilder`.

## Getting Help

- **Need help**? Ask a question to the [Algolia Community](https://discourse.algolia.com/) or on [Stack Overflow](http://stackoverflow.com/questions/tagged/algolia).
- **Found a bug?** You can open a [GitHub issue](https://github.com/algolia/algoliasearch-client-java-2/issues).



