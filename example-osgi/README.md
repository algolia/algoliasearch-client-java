# Using the Algolia Apache Uber JAR as an OSGi bundle

## Step 1: Install an OSGi platform to run bundles

Apache Karaf is an runner for OSGi bundles. It is the runner we use to test if
our uber JARs are correctly bundled to run as OSGi bundles.

To install Apache Karaf, go to https://karaf.apache.org/download.html and
download the latest Binary distribution of the Karaf Runtime, as `.tar.gz` if
you are using macOS or Linux or as `.zip` for Windows (not tested).

Once downloaded and unarchived (`tar zxvf` to unarchive a `.tar.gz`), you can
now go within the decompressed directory and run `bin/karaf`.

By doing so, you start the Karaf Runtime which lets you install, start, remove
OSGi bundles. Those bundles actually are plain JARs with some extra entries
within their `MANIFEST.MF` file.

The main commands are:

 - `bundle:list` to list installed bundles with their associated unique IDs
 - `bundle:install mvn:com.algolia/algoliasearch-apache-uber/3.9.0` to install a Maven bundle
 - `bundle:start <ID>` to start an installed bundle
 - `bundle:uninstall <ID>` to uninstall a bundle
 - `log:display` to debug or investigate the most recent logs

## Step 2: Build the Algolia bundle locally

To make the Algolia bundle visible to Apache Karaf when we will want to install
it, we need to create the usual JAR package and install it in our local
M2 repository (under `~/.m2/`). This can be done with the following
`mvn install` command, performed from the root of the Algolia Java library:

```sh
mvn clean install -DskipTests -pl algoliasearch-apache,algoliasearch-apache-uber
```

## Step 3: Build the Algolia OSGi Demo Bundle

Same thing as before. This time, we are building a demo bundle application,
which has an entry point, called a `BundleActivator`, which depends on the
Algolia Apache uber JAR and we install it in our local M2 repository as well.

```sh
cd example-osgi
mvn clean install
```

## Step 4: Install and start the bundles

Go back to your Apache Karaf interactive prompt we have set up in Step 1 and
use the following commands:

```sh
bundle:install mvn:com.algolia/algoliasearch-apache-uber/3.9.0
bundle:install mvn:com.algolia/example-osgi/3.9.0
```

Then perform a `bundle:list` to ensure the two bundles are not listed and are
in `Installed` state, such as:

```sh
karaf@root()> bundle:install mvn:com.algolia/algoliasearch-apache-uber/3.9.0
Bundle ID: 44
karaf@root()> bundle:install mvn:com.algolia/example-osgi/3.9.0
Bundle ID: 45
karaf@root()> bundle:list
START LEVEL 100 , List Threshold: 50
ID │ State     │ Lvl │ Version │ Name
───┼───────────┼─────┼─────────┼────────────────────────────────────────
22 │ Active    │  80 │ 4.2.8   │ Apache Karaf :: OSGi Services :: Event
44 │ Installed │  80 │ 3.9.0   │ algoliasearch-apache-uber
45 │ Installed │  80 │ 3.9.0   │ example-osgi
karaf@root()>
```

You should then be able to start the bundles, first the Algolia Java uber JAR
then the Demo Bundle which performs a `client.listIndices()`:

```sh
karaf@root()> bundle:start 44
karaf@root()> bundle:start 45
Algolia Demo Bundle is starting (listIndices() listed 7993 indices)
```

## References

- https://github.com/algolia/algoliasearch-client-java/pull/697/files
- https://karaf.apache.org/manual/latest/#_directory_structure
- https://michaelrice.com/2015/04/the-simplest-osgi-karaf-hello-world-demo-i-could-come-up-with/
- https://www.baeldung.com/osgi
- https://stackoverflow.com/questions/8352710/osgi-missing-requirement-error
- https://github.com/Dynatrace/Dynatrace-AppMon-Server-REST-Java-SDK/issues/1
- https://www.javaworld.com/article/2077837/java-se-hello-osgi-part-1-bundles-for-beginners.html?page=3
