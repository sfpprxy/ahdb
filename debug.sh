#!/usr/bin/env bash
echo '> stash'
git stash

echo '> pull'
git pull

cp ./src/main/resources/application.properties.debug ./src/main/resources/application.properties

echo '> kill old'
kill $(cat ./AHDBPID)

echo '> package'
mvn -Dmaven.test.skip=true package

echo '> start'
java -Dprofile=debug -Dserver.port=9999 \
-Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.port=10001 \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false \
-Djava.rmi.server.hostname=123.206.124.78 \
-jar target/ahdb-0.0.2-SNAPSHOT.jar
