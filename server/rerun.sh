#!/usr/bin/env bash
echo '> stash'
#git stash

echo '> pull'
#git pull

#cp ./src/main/resources/application.properties.prod ./src/main/resources/application.properties

echo '> kill old'
kill $(cat ./pid)

echo '> start'
chmod +x ahdbserver-1.3.2-SNAPSHOT-runner
VERSION=$(<version.txt)

nohup ./ahdbserver-1.3.2-SNAPSHOT-runner \
-Xmx256m \
-DVersion="$VERSION" \
-XX:+PrintGC -XX:+PrintGCTimeStamps +XX:+PrintHeapShape \
>> ./ahdbserver.log & echo $! > pid
