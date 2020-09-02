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
EXE_NAME="myfile.txt"
#./transfer cow ./target/ahdbserver-1.3.2-SNAPSHOT-runner | grep 'Download Link: ' | cut -d' ' -f3 >> link.txt
echo "Some text here." > $EXE_NAME
./transfer cow $EXE_NAME | grep 'Download Link: ' | cut -d' ' -f3 >> link.txt
link=$(cat link.txt)
echo link on travis
echo "$link"

echo '> login remote' "$SERVER_HOST"
cd ..
ssh -t -i ./travis_rsa -p 4422 root@"$SERVER_HOST" <<EOF
pwd; du -sh *
echo link on remote
echo $link
cd ~/ahdb/server
mv $EXE_NAME $EXE_NAME.old
transfer $link
EOF

echo '> copy runner'
#scp -P4422 ./target/ahdbserver-1.3.2-SNAPSHOT-runner root@"$SERVER_HOST":~/ahdb/server

echo '> copy runner ok'
