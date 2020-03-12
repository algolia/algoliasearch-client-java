#!/bin/bash

set -e

if [[ "${JAVA_VERSION}" = "8" ]]; then
  if [ "$TRAVIS_PULL_REQUEST" != "false" ] && [[ ! "$TRAVIS_PULL_REQUEST_SLUG" =~ ^algolia\/ ]]; then
    # test only JDK8 comptaible requester
    eval $(./algolia-keys export) && mvn clean compile -pl algoliasearch-apache,algoliasearch-core &&  mvn clean test -pl algoliasearch-apache,algoliasearch-core;
    exit $?
  else
    mvn clean compile -pl algoliasearch-apache,algoliasearch-core && mvn clean test -pl algoliasearch-apache,algoliasearch-core;
    exit $?
  fi
fi

if [[ "${JAVA_VERSION}" = "11" ]]; then
  if [ "$TRAVIS_PULL_REQUEST" != "false" ] && [[ ! "$TRAVIS_PULL_REQUEST_SLUG" =~ ^algolia\/ ]]; then
    eval $(./algolia-keys export) && mvn clean compile && mvn clean test;
    exit $?
  else
    mvn clean compile && mvn clean test;
    exit $?
  fi
fi
