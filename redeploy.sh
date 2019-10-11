#!/usr/bin/env bash
echo '> stash'
git stash

echo '> pull'
git pull

echo '> kill old'
kill $(cat ./AHDBPID)

echo '> start'

nohup mvn clean spring-boot:run -Dprofile=test -Dserver.port=9999 >> ./ahdbserver.log &
