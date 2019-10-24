#!/usr/bin/env bash
echo '> stash'
git stash

echo '> pull'
git pull

cp ./src/main/resources/logback.xml.prod ./src/main/resources/logback.xml
cp ./src/main/resources/application.properties.prod ./src/main/resources/application.properties

echo '> kill old'
kill $(cat ./AHDBPID)

echo '> package'
mvn -Dmaven.test.skip=true package

echo '> start'
nohup java -Dprofile=prod -Dserver.port=9999 \
-jar target/ahdbserver-0.0.3.jar  >> ./ahdbserver.log &