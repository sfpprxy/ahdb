#!/usr/bin/env bash
echo '> stash'
git stash

echo '> pull'
git pull

cp ./src/main/resources/application.properties.prod ./src/main/resources/application.properties

echo '> kill old'
kill $(cat ./pid)

echo '> start'
VERSION=$(<version.txt)
nohup ./ahdbserver-1.3.2-SNAPSHOT-runner -XX:+PrintGC -XX:+PrintGCTimeStamps -XX:+VerboseGC +XX:+PrintHeapShape -Xmx32m -DVersion="$VERSION">> ./ahdbserver.log & echo $! > pid
