#!/usr/bin/env bash

mvn -Darguments="-DskipTests" release:prepare
mvn -Darguments="-DskipTests" release:perform

sleep 5

MVN_REP=`mvn nexus-staging:rc-list | grep comalgolia`
if [[ $MVN_REP == *"[INFO]"* ]]
then
    REP_ID=`echo $MVN_REP | cut -d" " -f2`
else
    REP_ID=`echo $MVN_REP | cut -d" " -f1`
fi

if [ -z "$REP_ID" ]; then
	echo "Can not find a REP_ID"
	exit 1
fi

echo "----------------------"
echo "REP_ID found: $REP_ID"
echo "----------------------"

sleep 15 #sleep longer

mvn nexus-staging:close -DstagingRepositoryId="$REP_ID"
mvn nexus-staging:release -DstagingRepositoryId="$REP_ID"
