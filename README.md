# Algolia Search API Client for Java

[Algolia Search](https://www.algolia.com) is a hosted full-text, numerical, and faceted search engine capable of delivering realtime results from the first keystroke.
The **Algolia Search API Client for Java** lets you easily use the [Algolia Search REST API](https://www.algolia.com/doc/rest-api/search) from your Java code.







## API Documentation

You can find the full reference on [Algolia's website](https://www.algolia.com/doc/api-client/java/).


## Table of Contents


1. **[Supported platforms](#supported-platforms)**


1. **[Install](#install)**


1. **[Philosophy](#philosophy)**

    * [Builder](#builder)
    * [JSON &amp; `Jackson2`](#json--jackson2)
    * [Async &amp; Future](#async--future)

1. **[Quick Start](#quick-start)**

    * [Initialize the client](#initialize-the-client)

1. **[Push data](#push-data)**


1. **[Configure](#configure)**


1. **[Search](#search)**


1. **[Search UI](#search-ui)**

    * [index.html](#indexhtml)





# Getting Started




## Supported platforms

This API client only suports Java 1.8+.
If you need support for an older version, please use this [package](https://github.com/algolia/algoliasearch-client-java).

## Install

If you're using `Maven`, add the following dependency to your `pom.xml` file:

```xml
<dependency>
  <groupId>com.algolia</groupId>
  <artifactId>algoliasearch</artifactId>
  <version>[2,]</version>
</dependency>
```

For the asynchronous version use:

```xml
<dependency>
  <groupId>com.algolia</groupId>
  <artifactId>algoliasearch-async</artifactId>
  <version>[2,]</version>
</dependency>
```

On `Google AppEngine` use this:

```xml
<dependency>
  <groupId>com.algolia</groupId>
  <artifactId>algoliasearch-appengine</artifactId>
  <version>[2,]</version>
</dependency>
```

## Philosophy

### Builder

The `v2` of the api client uses a builder to create the `APIClient` object:
* on `Google App Engine` use the `AppEngineAPIClientBuilder`
  * if you fancy `Future`, use the `AsyncHttpAPIClientBuilder`
* on `Android`, use the [`Android` API Client](https://github.com/algolia/algoliasearch-client-android)
* on a regular `JVM`, use the `ApacheAPIClientBuilder`

### JSON & `Jackson2`

All the serialization/deserialization is done with [`Jackson2`](https://github.com/FasterXML/jackson-core/wiki). You can add your custom `ObjectMapper` with the method `setObjectMapper` of the builder.
Changing it might produce unexpected results. You can find the one used in the interface `com.algolia.search.Defaults.DEFAULT_OBJECT_MAPPER`.

### Async & Future

All methods of the `AsyncAPIClient` are exactly the same as the `APIClient` but returns `CompletableFuture<?>`. All other classes are prefixes with `Async`. You can also pass an optional `ExecutorService` to the `build` of the `AsyncHttpAPIClientBuilder`.

## Quick Start

In 30 seconds, this quick start tutorial will show you how to index and search objects.

### Initialize the client

To begin, you will need to initialize the client. In order to do this you will need your **Application ID** and **API Key**.
You can find both on [your Algolia account](https://www.algolia.com/api-keys).

```java
APIClient client = new ApacheAPIClientBuilder("YourApplicationID", "YourAPIKey").build();
```

For the asynchronous version:

```java
AsyncAPIClient client = new AsyncHttpAPIClientBuilder("YourApplicationID", "YourAPIKey").build();
```

For `Google AppEngine`:

```java
APIClient client = new AppEngineAPIClientBuilder("YourApplicationID", "YourAPIKey").build();
```

## Push data

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

## Configure

Settings can be customized to fine tune the search behavior. For example, you can add a custom sort by number of followers to further enhance the built-in relevance:

```java
//Sync & Async version

index.setSettings(new IndexSettings().setCustomRanking(Collections.singletonList("desc(followers)")));
```

You can also configure the list of attributes you want to index by order of importance (most important first).

**Note:** The Algolia engine is designed to suggest results as you type, which means you'll generally search by prefix.
In this case, the order of attributes is very important to decide which hit is the best:

```java
//Sync & Async version

index.setSettings(new IndexSettings().setSearchableAttributes(
	Arrays.asList("lastname", "firstname", "company")
);
```

## Search

You can now search for contacts using `firstname`, `lastname`, `company`, etc. (even with typos):

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

## Search UI

**Warning:** If you are building a web application, you may be more interested in using one of our
[frontend search UI librairies](https://www.algolia.com/doc/guides/search-ui/search-libraries/)

The following example shows how to build a front-end search quickly using
[InstanSearch.js](https://community.algolia.com/instantsearch.js/)

### index.html

```html
<!doctype html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/instantsearch.js/1/instantsearch.min.css">
</head>
<body>
  <header>
    <div>
       <input id="search-input" placeholder="Search for products">
       <!-- We use a specific placeholder in the input to guides users in their search. -->
    
  </header>
  <main>
      
      
  </main>

  <script type="text/html" id="hit-template">
    
      <p class="hit-name">{{{_highlightResult.firstname.value}}} {{{_highlightResult.lastname.value}}}</p>
    
  </script>

  <script src="https://cdn.jsdelivr.net/instantsearch.js/1/instantsearch.min.js"></script>
  <script src="app.js"></script>
</body>
```

### app.js

```js
var search = instantsearch({
  // Replace with your own values
  appId: 'YourApplicationID',
  apiKey: 'YourSearchOnlyAPIKey', // search only API key, no ADMIN key
  indexName: 'contacts',
  urlSync: true
});

search.addWidget(
  instantsearch.widgets.searchBox({
    container: '#search-input'
  })
);

search.addWidget(
  instantsearch.widgets.hits({
    container: '#hits',
    hitsPerPage: 10,
    templates: {
      item: document.getElementById('hit-template').innerHTML,
      empty: "We didn't find any results for the search <em>\"{{query}}\"</em>"
    }
  })
);

search.start();
```

## Getting Help

- **Need help**? Ask a question to the [Algolia Community](https://discourse.algolia.com/) or on [Stack Overflow](http://stackoverflow.com/questions/tagged/algolia).
- **Found a bug?** You can open a [GitHub issue](https://github.com/algolia/algoliasearch-client-java-2/issues).



