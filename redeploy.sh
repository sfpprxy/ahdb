#!/usr/bin/env bash
echo '> stash'
git stash

echo '> pull'
git pull

cp ./src/main/resources/application.properties.prod ./src/main/resources/application.properties

echo '> kill old'
kill $(cat ./AHDBPID)

echo '> start'

nohup mvn clean spring-boot:run -Dprofile=test -Dserver.port=9999 >> ./ahdbserver.log &
