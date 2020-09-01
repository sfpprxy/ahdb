#!/usr/bin/env bash
pwd

echo '> env keys'

echo "$SERVER_HOST"

echo '> package'
#mvn package -Pnative

echo '> start'
touch rq
cat "mama" > rq
scp rq -P 4422 root@"$SERVER_HOST":~/ahdb/server
