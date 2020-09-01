#!/usr/bin/env bash
echo "> pwd"
pwd

#echo '> package'
#./mvnw -version
#./mvnw package -Pnative

echo '> deploy'
du -sh ./target/*

echo '> get transfer'
wget https://hub.fastgit.org/Mikubill/transfer/releases/download/v0.4.7/transfer_0.4.7_linux_amd64.tar.gz
tar xzvf transfer_0.4.7_linux_amd64.tar.gz

echo '> upload jar'
./transfer cow ./target/ahdbserver-1.3.2-SNAPSHOT-runner
# todo get link

echo '> login remote'
ssh -p 4422 ./travis_rsa root@$SERVER_HOST pwd

echo '> copy runner'
scp -P4422 ./target/ahdbserver-1.3.2-SNAPSHOT-runner root@"$SERVER_HOST":~/ahdb/server

echo '> copy runner ok'
