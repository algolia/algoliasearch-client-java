#!/usr/bin/env bash
set -e

VERSION=$1

if [ -z "$VERSION" ]; then
  echo "Usage: ./release.sh <VERSION>" >&2
  exit 1
fi

mvn versions:set -DnewVersion="$1" -DgenerateBackupPoms=false
mvn clean
mvn package
mvn deploy -DskipTests

git add pom.xml algoliasearch-core/pom.xml algoliasearch-apache/pom.xml algoliasearch-core-uber/pom.xml algoliasearch-apache-uber/pom.xml
git commit -m "chore: Update version to $VERSION [skip ci]"
git tag "$VERSION"
git push
git push --tags
