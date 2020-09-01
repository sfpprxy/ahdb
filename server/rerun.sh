#!/usr/bin/env bash
echo '> stash'
git stash

echo '> pull'
git pull

cp ./src/main/resources/application.properties.prod ./src/main/resources/application.properties

echo '> kill old'
kill $(cat ./AHDBPID)

echo '> start'
nohup ./ahdbserver-1.3.2-SNAPSHOT-runner >> ./ahdbserver.log &
