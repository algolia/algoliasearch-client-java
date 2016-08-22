#!/usr/bin/env bash

mvn -Darguments="-DskipTests" release:prepare
mvn -Darguments="-DskipTests" release:perform

sleep 5

REP_ID=`mvn nexus-staging:rc-list | grep comalgolia | cut -d" " -f1`

if [ -z "$REP_ID" ]; then
	echo "Can not find a REP_ID"
	exit 1
fi

echo "----------------------"
echo "REP_ID found: $REP_ID"
echo "----------------------"

sleep 5

# Do it 2 times because nexus...
mvn nexus-staging:close -DstagingRepositoryId="$REP_ID"
mvn nexus-staging:close -DstagingRepositoryId="$REP_ID"
mvn nexus-staging:release -DstagingRepositoryId="$REP_ID"
