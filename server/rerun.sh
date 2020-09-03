#!/usr/bin/env bash
echo '> stash'
git stash

echo '> pull'
git pull

cp ./src/main/resources/application.properties.prod ./src/main/resources/application.properties

echo '> kill old'
kill $(cat ./pid)

echo '> start'
nohup ./ahdbserver-1.3.2-SNAPSHOT-runner -XX:+PrintGC -XX:+PrintGCTimeStamps -XX:+VerboseGC +XX:+PrintHeapShape -Xmx32m >> ./ahdbserver.log & echo $! > pid


./mvnw package -Pnative -Dquarkus.native.additional-build-args=-H:IncludeResources=META-INF/resources/.* -Dquarkus.native.enable-http-url-handler=true -Dquarkus.native.auto-service-loader-registration=true
