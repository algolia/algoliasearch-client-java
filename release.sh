#!/bin/sh
mvn -Darguments="-DskipTests" release:prepare
mvn -Darguments="-DskipTests" release:perform

REP_ID=`mvn nexus-staging:rc-list | grep comalgolia | cut -d" " -f2`

mvn nexus-staging:close -DstagingRepositoryId="$REP_ID"
mvn nexus-staging:release -DstagingRepositoryId="$REP_ID"