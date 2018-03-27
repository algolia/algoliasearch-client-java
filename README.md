# Algolia Search API Client for Java

[Algolia Search](https://www.algolia.com) is a hosted full-text, numerical,
and faceted search engine capable of delivering realtime results from the first keystroke.

The **Algolia Search API Client for Java** lets
you easily use the [Algolia Search REST API](https://www.algolia.com/doc/rest-api/search) from
your Java code.



**WARNING:**
The JVM has an infinite cache on successful DNS resolution. As our hostnames points to multiple IPs, the load could be not evenly spread among our machines, and you might also target a dead machine.

You should change this TTL by setting the property `networkaddress.cache.ttl`. For example to set the cache to 60 seconds:
```java
java.security.Security.setProperty("networkaddress.cache.ttl", "60");
```

For debug purposes you can enable debug logging on the API client. It's using [slf4j](https://www.slf4j.org) so it should be compatible with most java logger.
The logger is named `algoliasearch`.




## API Documentation

You can find the full reference on [Algolia's website](https://www.algolia.com/doc/api-client/java/).


## In this page



1. **[Install](#install)**


1. **[Quick Start](#quick-start)**


1. **[Push data](#push-data)**


1. **[Configure](#configure)**


1. **[Search](#search)**


1. **[Search UI](#search-ui)**


1. **[List of available methods](#list-of-available-methods)**


# Getting Started



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

#### Builder

The `v2` of the api client uses a builder to create the `APIClient` object:
* on `Google App Engine` use the `AppEngineAPIClientBuilder`
  * if you fancy `Future`, use the `AsyncHttpAPIClientBuilder`
* on `Android`, use the [`Android` API Client](https://github.com/algolia/algoliasearch-client-android)
* on a regular `JVM`, use the `ApacheAPIClientBuilder`

#### POJO, JSON & `Jackson2`

The Index (and AsyncIndex) classes are parametrized with a Java class. If you specify one it enables you to have type safe method results.
This parametrized Java class is expected to follow the POJO convention:
  * A constructor without parameters
  * Getters & setters for every field you want to (de)serialize

Example:

```java
public class Contact {

  private String name;
  private int age;

  public Contact() {}

  public String getName() {
    return name;
  }

  public Contact setName(String name) {
    this.name = name;
    return this;
  }

  public int getAge() {
    return age;
  }

  public Contact setAge(int age) {
    this.age = age;
    return this;
  }
}
```

All the serialization/deserialization is done with [`Jackson2`](https://github.com/FasterXML/jackson-core/wiki). You can add your custom `ObjectMapper` with the method `setObjectMapper` of the builder.
Changing it might produce unexpected results. You can find the one used in the interface `com.algolia.search.Defaults.DEFAULT_OBJECT_MAPPER`.

#### Async & Future

All methods of the `AsyncAPIClient` are exactly the same as the `APIClient` but returns `CompletableFuture<?>`. All other classes are prefixes with `Async`. You can also pass an optional `ExecutorService` to the `build` of the `AsyncHttpAPIClientBuilder`.

## Quick Start

In 30 seconds, this quick start tutorial will show you how to index and search objects.

### Initialize the client

To begin, you will need to initialize the client. In order to do this you will need your **Application ID** and **API Key**.
You can find both on [your Algolia account](https://www.algolia.com/api-keys).

```java
APIClient client =
  new ApacheAPIClientBuilder("YourApplicationID", "YourAPIKey").build();

Index<Contact> index = client.initIndex("your_index_name", Contact.class);
```

For the asynchronous version:

```java
AsyncAPIClient client =
  new AsyncHttpAPIClientBuilder("YourApplicationID", "YourAPIKey").build();

AsyncIndex<Contact> index = client.initIndex("your_index_name", Contact.class);
```

For `Google AppEngine`:

```java
APIClient client =
  new AppEngineAPIClientBuilder("YourApplicationID", "YourAPIKey").build();

Index<Contact> index = client.initIndex("your_index_name", Contact.class);
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
[frontend search UI libraries](https://www.algolia.com/doc/guides/search-ui/search-libraries/)

The following example shows how to build a front-end search quickly using
[InstantSearch.js](https://community.algolia.com/instantsearch.js/)

### index.html

```html
<!doctype html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/instantsearch.js@2.3/dist/instantsearch.min.css">
  <!-- Always use `2.x` versions in production rather than `2` to mitigate any side effects on your website,
  Find the latest version on InstantSearch.js website: https://community.algolia.com/instantsearch.js/v2/guides/usage.html -->
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

  <script src="https://cdn.jsdelivr.net/npm/instantsearch.js@2.3/dist/instantsearch.min.js"></script>
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
  urlSync: true,
  searchParameters: {
    hitsPerPage: 10
  }
});

search.addWidget(
  instantsearch.widgets.searchBox({
    container: '#search-input'
  })
);

search.addWidget(
  instantsearch.widgets.hits({
    container: '#hits',
    templates: {
      item: document.getElementById('hit-template').innerHTML,
      empty: "We didn't find any results for the search <em>\"{{query}}\"</em>"
    }
  })
);

search.start();
```




## List of available methods


































## Getting Help

- **Need help**? Ask a question to the [Algolia Community](https://discourse.algolia.com/) or on [Stack Overflow](http://stackoverflow.com/questions/tagged/algolia).
- **Found a bug?** You can open a [GitHub issue](https://github.com/algolia/algoliasearch-client-java-2/issues).

