#!/usr/bin/env bash
echo "> pwd"
pwd

#echo '> package'
#./mvnw -version
#./mvnw package -Pnative

echo '> deploy'
du -sh ./target/*
echo '> copy jar'
scp -P4422 ./target/ahdbserver-1.3.2-SNAPSHOT.jar root@"$SERVER_HOST":~/ahdb/server
echo '> copy runner'
scp -P4422 ./target/ahdbserver-1.3.2-SNAPSHOT-runner root@"$SERVER_HOST":~/ahdb/server
echo '> copy runner ok'
