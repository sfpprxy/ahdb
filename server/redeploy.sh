#!/usr/bin/env bash
pwd

echo '> env keys'

echo "$SERVER_HOST"

echo '> package'
#mvn package -Pnative

echo '> start'
touch rq
cat "mama" > rq
scp -P4422 rq root@"$SERVER_HOST":~/ahdb/server
